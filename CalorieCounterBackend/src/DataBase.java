import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public void createFood(String name, int calories) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Food (name, calories) VALUES (?, ?)");
            ps.setString(1, name);
            ps.setInt(2, calories);
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new food item was inserted successfully!");
            }
        } catch (SQLException sqle) {
            error(sqle);
        }
    }

    private void insertBMRIntoDatabase(double BMR) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO User (BMR) VALUES (?)");
            ps.setDouble(1, BMR);
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user's BMR was inserted successfully!");
            }
        } catch (SQLException sqle) {
            error(sqle);
        }
    }

    /*	 The BMR for men (655.1 + (9.563 x Weight in kg) + (1.850 x Height in cm) -
         (4.676 x Age in years)*/
    public void menBMR(int weightInKg, int heightInCm, int ageInYears) {
        double BMR = 655.1 + (9.563 * weightInKg) + (1.850 * heightInCm) - (4.676 * ageInYears);
        BMR = Math.ceil(BMR);
        insertBMRIntoDatabase(BMR);
    }

    // The BMR for women (66.47 + (13.75 x Weight in kg) + (5.003 x Height in cm) -
    // (6.755 x Age in years)
    public void womenBMR(int weightInKg, int heightInCm, int ageInYears) {
        double BMR = 66.47 + (13.75 * weightInKg) + (5.003 * heightInCm) - (6.755 * ageInYears);
        BMR = Math.ceil(BMR);
        insertBMRIntoDatabase(BMR);
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

    private void updateAMR(String activityLevel, double multiplier) throws SQLException {
        int maxID = getMaxUserId();
        String getBMRQuery = "SELECT BMR FROM User WHERE UserID = " + maxID;
        ResultSet BMRResultSet = connection.createStatement().executeQuery(getBMRQuery);
        if (BMRResultSet.next()) {
            double BMR = BMRResultSet.getDouble("BMR");
            double AMRcalc = Math.ceil(BMR * multiplier);
            PreparedStatement updateQuery = connection.prepareStatement("UPDATE User Set AMR = ? WHERE UserID = ?");
            updateQuery.setDouble(1, AMRcalc);
            updateQuery.setInt(2, maxID);
            int rowsUpadted = updateQuery.executeUpdate();
            if (rowsUpadted > 0) {
                System.out.println("AMR for " + activityLevel + " activity level: " + AMRcalc);
            }
        }
    }

    //Sedentary (little to no exercise)
    public String sedentary() {
        try {
            updateAMR("sedentary", 1.2);
        } catch (SQLException sqle) {
            error(sqle);
        }
        return "Unable to calculate AMR for sedentary activity level";
    }

    // Lightly active (exercise 1-3 days/week)
    public String lightlyActive() {
        try {
            updateAMR("lightly active", 1.375);
        } catch (SQLException sqle) {
            error(sqle);
        }
        return "Unable to calculate AMR for lightly active activity level";
    }

    // Moderately active (exercise 3-5 days/week)
    public String moderatelyActive() {
        try {
            updateAMR("moderately active", 1.55);
        } catch (SQLException sqle) {
            error(sqle);
        }
        return "Unable to calculate AMR for moderately active activity level";
    }

    // Active (exercise 6-7 days/week)
    public String active() {
        try {
            updateAMR("active", 1.725);
        } catch (SQLException sqle) {
            error(sqle);
        }
        return "Unable to calculate AMR for active activity level";
    }

    // Very active (hard exercise 6-7 days/week)
    public String veryActive() {
        try {
            updateAMR("very active", 1.9);
        } catch (SQLException sqle) {
            error(sqle);
        }
        return "Unable to calculate AMR for very active activity level";
    }

    // To lose weight - 500 calories to the maintenance(AMR)
    public String loseWeight() {
        try {
            int maxID = getMaxUserId();
            String updateQuery = "UPDATE User SET CalorieIntake = AMR - 500 WHERE UserID = " + maxID;
            Statement s = connection.createStatement();
            s.executeUpdate(updateQuery);
        } catch (SQLException sqle) {
            error(sqle);
        }
        return " - 500 calories.";
    }

    // To gain weight + 500 calories to the mainenance(AMR)
    public String gainWeight() {
        try {
            int maxID = getMaxUserId();
            String updateQuery = "UPDATE User SET CalorieIntake = AMR + 500 WHERE UserID = " + maxID;
            Statement s = connection.createStatement();
            s.executeUpdate(updateQuery);
        } catch (SQLException sqle) {
            error(sqle);
        }
        return " + 500 calories.";
    }

    // Get current calorie intake
    public double getCalorieIntake() {
        double CalorieIntake = 0;
        try {
            int maxID = getMaxUserId();
            String results = "SELECT CalorieIntake FROM User WHERE UserID = " + maxID;
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
    public String foodCalories() {
        try {
            Statement s = connection.createStatement();
            String getMaxIDQueryFood = "SELECT MAX(FoodID) AS foodMaxID FROM Food";
            ResultSet maxIDResultSet = s.executeQuery(getMaxIDQueryFood);
            if (maxIDResultSet.next()) {
                int foodMaxID = maxIDResultSet.getInt("foodMaxID");
                String selectQuery = "SELECT Calories AS foodCalorie FROM Food WHERE FoodID = " + foodMaxID;
                ResultSet CalorieSet = s.executeQuery(selectQuery);
                if (CalorieSet.next()) {
                    int foodCalorie = CalorieSet.getInt("foodCalorie");
                    int maxUserID = getMaxUserId();
                    String updateQuery = "UPDATE User SET CalorieIntake = CalorieIntake - " + foodCalorie
                            + " WHERE UserID = " + maxUserID;
                    Statement l = connection.createStatement();
                    l.executeUpdate(updateQuery);
                }
            }
        } catch (SQLException sqle) {
            error(sqle);
        }
        return "Food calories subtracted from CalorieIntake";
    }

    /**
     * Prints out the details of the SQL error that has occurred, and exits the
     * programme
     *
     * @param sqle Exception representing the error that occurred
     */
    private void error(SQLException sqle) {
        System.err.println("Problem Opening Database! " + sqle.getClass().getName());
        sqle.printStackTrace();
        System.exit(1);
    }

    /**
     * Closes the connection to the database, required by AutoCloseable interface.
     */
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