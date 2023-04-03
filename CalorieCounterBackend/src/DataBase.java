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

	public int getNumberOfEntries() {
		int result = -1;
		try {
			Statement s = connection.createStatement();
			ResultSet results = s.executeQuery("SELECT COUNT(*) AS count FROM Food");
			while (results.next()) { // will only execute once, because SELECT COUNT(*) returns just 1 number
				result = results.getInt(results.findColumn("count"));
			}
		} catch (SQLException sqle) {
			error(sqle);

		}
		return result;
	}

	// has been tested.
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

/*	 The BMR for men (655.1 + (9.563 x Weight in kg) + (1.850 x Height in cm) -
	 (4.676 x Age in years)*/
	public void menBMR(int weightInKg, int heightInCm, int ageInYears) {
		try {
			double BMR = 655.1 + (9.563 * weightInKg) + (1.850 * heightInCm) - (4.676 * ageInYears);
            BMR = Math.ceil(BMR);
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

	// The BMR for women (66.47 + (13.75 x Weight in kg) + (5.003 x Height in cm) -
	// (6.755 x Age in years)
	public void womenBMR(int weightInKg, int heightInCm, int ageInYears) {
		try {
			double BMR = 66.47 + (13.75 * weightInKg) + (5.003 * heightInCm) - (6.755 * ageInYears);
            BMR = Math.ceil(BMR);
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

	// Sedentary (little to no exercise)
	public String sedentary() {
		try {
			String getMaxIDQuery = "SELECT MAX(UserID) AS MaxID FROM User";
			ResultSet maxIDResultSet = connection.createStatement().executeQuery(getMaxIDQuery);
			if (maxIDResultSet.next()) {
				int maxID = maxIDResultSet.getInt("MaxID");
				String getBMRQuery = "SELECT BMR FROM User WHERE UserID = " + maxID;
				ResultSet BMRResultSet = connection.createStatement().executeQuery(getBMRQuery);
				if (BMRResultSet.next()) {
					double BMR = BMRResultSet.getDouble("BMR");
					double AMRcalc = Math.ceil(BMR * 1.2);
					PreparedStatement updateQuery = connection.prepareStatement("UPDATE User Set AMR = ? WHERE UserID = ?");
					updateQuery.setDouble(1, AMRcalc);
					updateQuery.setInt(2, maxID);
					int rowsUpadted = updateQuery.executeUpdate();
					if (rowsUpadted > 0) {
						return "AMR for sedentary activity level: " + AMRcalc;
					}
				}
			}
		} catch (SQLException sqle) {
			error(sqle);
		}
		return "Unable to calculate AMR for sedentary activity level";
	}

	// Lightly active (exercise 1-3 days/week)
	public String lightlyActive() {
		try {
			String getMaxIDQuery = "SELECT MAX(UserID) AS MaxID FROM User";
			ResultSet maxIDResultSet = connection.createStatement().executeQuery(getMaxIDQuery);
			if (maxIDResultSet.next()) {
				int maxID = maxIDResultSet.getInt("MaxID");
				String getBMRQuery = "SELECT BMR FROM User WHERE UserID = " + maxID;
				ResultSet BMRResultSet = connection.createStatement().executeQuery(getBMRQuery);
				if (BMRResultSet.next()) {
					double BMR = BMRResultSet.getDouble("BMR");
					double AMRcalc = Math.ceil(BMR * 1.375);
					PreparedStatement updateQuery = connection.prepareStatement("UPDATE User Set AMR = ? WHERE UserID = ?");
					updateQuery.setDouble(1, AMRcalc);
					updateQuery.setInt(2, maxID);
					int rowsUpadted = updateQuery.executeUpdate();
					if (rowsUpadted > 0) {
						return "AMR for lightly active activity level: " + AMRcalc;
					}
				}
			}
		} catch (SQLException sqle) {
			error(sqle);
		}
		return "Unable to calculate AMR for lightly active activity level";
	}

	// Moderately active (exercise 3-5 days/week)
	public String moderatelyActive() {
		try {
			String getMaxIDQuery = "SELECT MAX(UserID) AS MaxID FROM User";
			ResultSet maxIDResultSet = connection.createStatement().executeQuery(getMaxIDQuery);
			if (maxIDResultSet.next()) {
				int maxID = maxIDResultSet.getInt("MaxID");
				String getBMRQuery = "SELECT BMR FROM User WHERE UserID = " + maxID;
				ResultSet BMRResultSet = connection.createStatement().executeQuery(getBMRQuery);
				if (BMRResultSet.next()) {
					double BMR = BMRResultSet.getDouble("BMR");
					double AMRcalc = Math.ceil(BMR * 1.55);
					PreparedStatement updateQuery = connection.prepareStatement("UPDATE User Set AMR = ? WHERE UserID = ?");
					updateQuery.setDouble(1, AMRcalc);
					updateQuery.setInt(2, maxID);
					int rowsUpadted = updateQuery.executeUpdate();
					if (rowsUpadted > 0) {
						return "AMR for moderately active activity level: " + AMRcalc;
					}
				}
			}
		} catch (SQLException sqle) {
			error(sqle);
		}
		return "Unable to calculate AMR for moderately active activity level";
	}

	// Active (exercise 6-7 days/week)
	public String active() {
		try {
			String getMaxIDQuery = "SELECT MAX(UserID) AS MaxID FROM User";
			ResultSet maxIDResultSet = connection.createStatement().executeQuery(getMaxIDQuery);
			if (maxIDResultSet.next()) {
				int maxID = maxIDResultSet.getInt("MaxID");
				String getBMRQuery = "SELECT BMR FROM User WHERE UserID = " + maxID;
				ResultSet BMRResultSet = connection.createStatement().executeQuery(getBMRQuery);
				if (BMRResultSet.next()) {
					double BMR = BMRResultSet.getDouble("BMR");
					double AMRcalc = Math.ceil(BMR * 1.725);
					PreparedStatement updateQuery = connection.prepareStatement("UPDATE User Set AMR = ? WHERE UserID = ?");
					updateQuery.setDouble(1, AMRcalc);
					updateQuery.setInt(2, maxID);
					int rowsUpadted = updateQuery.executeUpdate();
					if (rowsUpadted > 0) {
						return "AMR for active activity level: " + AMRcalc;
					}
				}
			}
		} catch (SQLException sqle) {
			error(sqle);
		}
		return "Unable to calculate AMR for active activity level";
	}

	// Very active (hard exercise 6-7 days/week)
	public String veryActive() {
		try {
			String getMaxIDQuery = "SELECT MAX(UserID) AS MaxID FROM User";
			ResultSet maxIDResultSet = connection.createStatement().executeQuery(getMaxIDQuery);
			if (maxIDResultSet.next()) {
				int maxID = maxIDResultSet.getInt("MaxID");
				String getBMRQuery = "SELECT BMR FROM User WHERE UserID = " + maxID;
				ResultSet BMRResultSet = connection.createStatement().executeQuery(getBMRQuery);
				if (BMRResultSet.next()) {
					double BMR = BMRResultSet.getDouble("BMR");
					double AMRcalc = Math.ceil(BMR * 1.9);
					PreparedStatement updateQuery = connection.prepareStatement("UPDATE User Set AMR = ? WHERE UserID = ?");
					updateQuery.setDouble(1, AMRcalc);
					updateQuery.setInt(2, maxID);
					int rowsUpadted = updateQuery.executeUpdate();
					if (rowsUpadted > 0) {
						return "AMR for very active activity level: " + AMRcalc;
					}
				}
			}
		} catch (SQLException sqle) {
			error(sqle);
		}
		return "Unable to calculate AMR for very active activity level";
	}

	// To lose weight - 500 calories to the maintenance(AMR)
	public String loseWeight() {
		try {
			Statement s = connection.createStatement();
			String getMaxIDQuery = "SELECT MAX(UserID) AS MaxID FROM User";
			ResultSet maxIDResultSet = s.executeQuery(getMaxIDQuery);
			if (maxIDResultSet.next()) {
				int maxID = maxIDResultSet.getInt("MaxID");
				String updateQuery = "UPDATE User SET CalorieIntake = AMR - 500 WHERE UserID = " + maxID;
				s.executeUpdate(updateQuery);
			}
		} catch (SQLException sqle) {
			error(sqle);
		}
		return " - 500 calories.";
	}

	// To gain weight + 500 calories to the mainenance(AMR)
	public String gainWeight() {
		try {
			Statement s = connection.createStatement();
			String getMaxIDQuery = "SELECT MAX(UserID) AS MaxID FROM User";
			ResultSet maxIDResultSet = s.executeQuery(getMaxIDQuery);
			if (maxIDResultSet.next()) {
				int maxID = maxIDResultSet.getInt("MaxID");
				String updateQuery = "UPDATE User SET CalorieIntake = AMR + 500 WHERE UserID = " + maxID;
				s.executeUpdate(updateQuery);
			}
		} catch (SQLException sqle) {
			error(sqle);
		}
		return " + 500 calories.";
	}

	// Get current calorie intake
	public double getCalorieIntake() {
		double CalorieIntake = 0;
		try {
			Statement s = connection.createStatement();
			String getMaxIDQuery = "SELECT MAX(UserID) AS MaxID FROM User";
			ResultSet maxIDResultSet = s.executeQuery(getMaxIDQuery);
			if (maxIDResultSet.next()) {
				int maxID = maxIDResultSet.getInt("MaxID");
				String results = "SELECT CalorieIntake FROM User WHERE UserID = " + maxID;
				ResultSet CalorieSet = s.executeQuery(results);
				if (CalorieSet.next()) {
					CalorieIntake = CalorieSet.getDouble("CalorieIntake");
				}
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
			String getMaxIDQueryFood = "SELECT MAX(FoodID) AS MaxID FROM Food";
			ResultSet maxIDResultSet = s.executeQuery(getMaxIDQueryFood);
			if (maxIDResultSet.next()) {
				int maxID = maxIDResultSet.getInt("MaxID");
				String selectQuery = "SELECT Calories AS foodCalorie FROM Food WHERE FoodID = " + maxID;
				ResultSet CalorieSet = s.executeQuery(selectQuery);
				if (CalorieSet.next()) {
					int foodCalorie = CalorieSet.getInt("foodCalorie");
					String getMaxIDQuery = "SELECT MAX(UserID) AS MaxUserID FROM User";
					ResultSet maxUserIDResult = s.executeQuery(getMaxIDQuery);
					if (maxUserIDResult.next()) {
						int maxUserID = maxIDResultSet.getInt("MaxUserID");
						String updateQuery = "UPDATE User SET CalorieIntake = CalorieIntake - " + foodCalorie
								+ " WHERE UserID = " + maxUserID;
						s.executeUpdate(updateQuery);
					}
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