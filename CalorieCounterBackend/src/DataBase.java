import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.*;
import java.sql.*;


public class DataBase implements AutoCloseable {

    // allows us to easily change the database used
    private static final String JDBC_CONNECTION_STRING = "jdbc:sqlite:data/CalorieDB.db";

    // allows us to re-use the connection between queries if desired
    private Connection connection = null;

    /**
     * Creates an instance of the DataBase object and connects to the database
     */
    public DataBase() {
        try {
            connection = DriverManager.getConnection(JDBC_CONNECTION_STRING);
        } catch (SQLException sqle) {
            error(sqle);
        }
    }

    // Add new user to database validating input before adding to database (email & password format & if email exists in database)
    public void addNewUser(String firstName, String lastName, String email, String password) throws Exception {

        List<String> errorMessages = new ArrayList<>();

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errorMessages.add("Invalid email format. Please enter a valid email address in the format example@example.com.");
        }
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,30}$")) {
            errorMessages.add("Invalid password format. Your password must be between 8 and 30 characters long and contain at least one lowercase letter, one uppercase letter, one digit, and one special character (@$!%*?&).");
        }

        try (PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE email = ?")) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                errorMessages.add("Email already exists");
            }
        } catch (SQLException sqle) {
            error(sqle);
            throw new Exception("Error checking email exists in database");
        }
        if (!errorMessages.isEmpty()) {
            throw new Exception(String.join(", ", errorMessages));
        }

        // A salt is generated for the new user.
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[64];
        random.nextBytes(salt);
        // The hash of the new user's password is created.
        String hash = getHash(password, salt);

        String sql = "INSERT INTO users (first_name, last_name, email, password, salt) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, hash);
            ps.setString(5, bytesToHex(salt));

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }
        } catch (SQLException sqle) {
            error(sqle);
        }
    }

    // This function returns the hash of a password and its salt.
    public static String getHash(String password, byte[] salt) throws Exception {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 100000, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return bytesToHex(hash);
    }

    // Converts bytes to hexadecimal string.
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // Converts a hexadecimal string to a byte array.
    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return bytes;
    }

    public Integer authenticateUser(String email, String password) {
        String sql = "SELECT user_id, password, salt FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String saltString = resultSet.getString("salt");
                byte[] salt = hexToBytes(saltString);
                String hash = getHash(password, salt);
                String storedHash = resultSet.getString("password");
//                System.out.println("Salt: " + saltString);
//                System.out.println("Generated hash: " + hash);
//                System.out.println("Stored hash: " + storedHash);
                if (hash.equals(storedHash)) {
                    return resultSet.getInt("user_id");
                } else {
                    throw new Exception("Wrong password!");
                }
            } else {
                throw new Exception("User not found!");
            }
        } catch (SQLException sqle) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private String generateRandomToken() {
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[16];
        random.nextBytes(tokenBytes);
        return bytesToHex(tokenBytes);
    }

    public String setToken(int id) {
        String token = generateRandomToken(); // Implement the generateRandomToken method to generate a random token
        String sql = "UPDATE users SET session_token=? WHERE user_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, token);
            ps.setInt(2, id);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 1) {
                return token;
            } else {
                throw new SQLException("Unable to set session token");
            }
        } catch (SQLException sqle) {
            error(sqle);
            return null;
        }
    }

    public String getToken(int id) {
        String sql = "SELECT session_token FROM users WHERE user_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String sessionToken = resultSet.getString("session_token");
                if (sessionToken != null) {
                    return sessionToken;
                }
            }
        } catch (SQLException sqle) {
            error(sqle);
        }
        return null;
    }

    public void removeToken(String token) throws SQLException {
        String sql = "UPDATE users SET session_token=null WHERE session_token=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, token);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            error(sqle);
        }
    }

    public Integer getIdFromToken(String token) throws SQLException {
        if (token == null) {
            return null;
        }
        String sql = "SELECT user_id FROM users WHERE session_token=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, token);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("user_id");
            } else {
                return null;
            }
        } catch (SQLException sqle) {
            error(sqle);
            return null;
        }
    }

    public void createFood(String foodName, int calories, int addedByUserId) {
        try {
            // Get the current local time as a timestamp
            long dateAdded = System.currentTimeMillis();

            // Prepare the SQL statement with placeholders for the values
            String sql = "INSERT INTO foods (food_name, calories, date_added, added_by) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            // Set the values for the placeholders in the SQL statement
            ps.setString(1, foodName);
            ps.setInt(2, calories);
            ps.setLong(3, dateAdded);
            ps.setInt(4, addedByUserId);

            // Execute the SQL statement and check if any rows were affected
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new food item was inserted successfully!");
            }
        } catch (SQLException sqle) {
            error(sqle);
        }
    }

//    private void insertBMRIntoDatabase(double BMR, int userId) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("UPDATE users SET BMR = ? WHERE user_id = ?");
//            ps.setDouble(1, BMR);
//            ps.setInt(2, userId);
//            int rowsInserted = ps.executeUpdate();
//            if (rowsInserted > 0) {
//                System.out.println("BMR for user \" + userId + \" was updated successfully!");
//            }
//        } catch (SQLException sqle) {
//            error(sqle);
//        }
//    }

    /*	 The BMR for men (655.1 + (9.563 x Weight in kg) + (1.850 x Height in cm) -
         (4.676 x Age in years)*/
    public void menBMR(int weightInKg, int heightInCm, int ageInYears, int userId) {
        double BMR = 655.1 + (9.563 * weightInKg) + (1.850 * heightInCm) - (4.676 * ageInYears);
        BMR = Math.ceil(BMR);
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE users SET BMR = ? WHERE user_id = ?");
            ps.setDouble(1, BMR);
            ps.setInt(2, userId);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User's BMR was updated successfully!");
            }
        } catch (SQLException sqle) {
            error(sqle);
        }
    }

    // The BMR for women (66.47 + (13.75 x Weight in kg) + (5.003 x Height in cm) - (6.755 x Age in years)
    public void womenBMR(int weightInKg, int heightInCm, int ageInYears, int userId) {
        double BMR = 66.47 + (13.75 * weightInKg) + (5.003 * heightInCm) - (6.755 * ageInYears);
        BMR = Math.ceil(BMR);
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE users SET BMR = ? WHERE user_id = ?");
            ps.setDouble(1, BMR);
            ps.setInt(2, userId);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User's BMR was updated successfully!");
            }
        } catch (SQLException sqle) {
            error(sqle);
        }
    }


    private int getMaxUserId() throws SQLException {
        Statement statement = connection.createStatement();
        String getMaxIDQuery = "SELECT MAX(UserID) AS MaxID FROM User";
        ResultSet resultSet = statement.executeQuery(getMaxIDQuery);
        int maxID = 0;
        if (resultSet.next()) {
            maxID = resultSet.getInt("MaxID");
        }
        return maxID;
    }

    private void updateAMR(int userId, String activityLevel, double multiplier) throws SQLException {
        String getBMRQuery = "SELECT BMR FROM users WHERE user_id = " + userId;
        ResultSet BMRResultSet = connection.createStatement().executeQuery(getBMRQuery);
        if (BMRResultSet.next()) {
            double BMR = BMRResultSet.getDouble("BMR");
            double AMRcalc = Math.ceil(BMR * multiplier);
            PreparedStatement updateQuery = connection.prepareStatement("UPDATE users SET AMR = ? WHERE user_id = ?");
            updateQuery.setDouble(1, AMRcalc);
            updateQuery.setInt(2, userId);
            int rowsUpadted = updateQuery.executeUpdate();
            if (rowsUpadted > 0) {
                System.out.println("AMR for " + activityLevel + " activity level: " + AMRcalc);
            }
        }
    }

    //Sedentary (little to no exercise)
    public String sedentary(int userId) {
        try {
            updateAMR(userId, "sedentary", 1.2);
        } catch (SQLException sqle) {
            error(sqle);
        }
        return "Unable to calculate AMR for sedentary activity level";
    }

    // Lightly active (exercise 1-3 days/week)
    public String lightlyActive(int userId) {
        try {
            updateAMR(userId, "lightly active", 1.375);
        } catch (SQLException sqle) {
            error(sqle);
        }
        return "Unable to calculate AMR for lightly active activity level";
    }

    // Moderately active (exercise 3-5 days/week)
    public String moderatelyActive(int userId) {
        try {
            updateAMR(userId, "moderately active", 1.55);
        } catch (SQLException sqle) {
            error(sqle);
        }
        return "Unable to calculate AMR for moderately active activity level";
    }

    // Active (exercise 6-7 days/week)
    public String active(int userId) {
        try {
            updateAMR(userId, "active", 1.725);
        } catch (SQLException sqle) {
            error(sqle);
        }
        return "Unable to calculate AMR for active activity level";
    }

    // Very active (hard exercise 6-7 days/week)
    public String veryActive(int userId) {
        try {
            updateAMR(userId, "very active", 1.9);
        } catch (SQLException sqle) {
            error(sqle);
        }
        return "Unable to calculate AMR for very active activity level";
    }

    // To lose weight - 500 calories to the maintenance(AMR)
    public String loseWeight(int userId) {
        try {
            String updateQuery = "UPDATE users SET CalorieIntake = AMR - 500 WHERE user_id = " + userId;
            Statement s = connection.createStatement();
            s.executeUpdate(updateQuery);
        } catch (SQLException sqle) {
            error(sqle);
        }
        return " - 500 calories.";
    }

    // To gain weight + 500 calories to the mainenance(AMR)
    public String gainWeight(int userId) {
        try {
            String updateQuery = "UPDATE users SET CalorieIntake = AMR + 500 WHERE user_id = " + userId;
            Statement s = connection.createStatement();
            s.executeUpdate(updateQuery);
        } catch (SQLException sqle) {
            error(sqle);
        }
        return " + 500 calories.";
    }

    // Get current calorie intake
    public double getCalorieIntake(int userId) {
        double CalorieIntake = 0;
        try {
            String results = "SELECT CalorieIntake FROM users WHERE user_id = " + userId;
            Statement s = connection.createStatement();
            ResultSet CalorieSet = s.executeQuery(results);
            if (CalorieSet.next()) {
                CalorieIntake = CalorieSet.getDouble("CalorieIntake");
            }
        } catch (SQLException sqle) {
            error(sqle);
        }
        return CalorieIntake;
    }

    /*
     * Subtract Food calories from the user's Calorie intake
     */
    public String foodCalories(int userId) {
        try {
            Statement s = connection.createStatement();
            String getMaxIDQueryFood = "SELECT MAX(food_id) AS foodMaxID FROM foods WHERE added_by = " + userId;
            ResultSet maxIDResultSet = s.executeQuery(getMaxIDQueryFood);
            if (maxIDResultSet.next()) {
                int foodMaxID = maxIDResultSet.getInt("foodMaxID");
                String selectQuery = "SELECT calories AS foodCalorie FROM foods WHERE food_id = " + foodMaxID;
                ResultSet CalorieSet = s.executeQuery(selectQuery);
                if (CalorieSet.next()) {
                    int foodCalorie = CalorieSet.getInt("foodCalorie");
                    String updateQuery = "UPDATE users SET CalorieIntake = CalorieIntake - " + foodCalorie
                            + " WHERE user_id = " + userId;
                    Statement l = connection.createStatement();
                    l.executeUpdate(updateQuery);
                    return "Food calories subtracted from CalorieIntake";
                }
            }
            return "Unable to retrieve food calories";
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return "Unable to retrieve food calories";
        }
    }

    private void error(SQLException sqle) {
        System.err.println("Problem Opening Database! " + sqle.getClass().getName());
        sqle.printStackTrace();
        System.exit(1);
    }

    @Override
    public void close() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException sqle) {
            error(sqle);
        }
    }
}