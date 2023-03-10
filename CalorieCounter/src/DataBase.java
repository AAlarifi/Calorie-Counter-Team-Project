import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase implements AutoCloseable {

	// allows us to easily change the database used
	private static final String JDBC_CONNECTION_STRING = "jdbc:sqlite:./data/CalorieDB.db";

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

	// Hasn't been tested yet.
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

	// The BMR for men (655.1 + (9.563 x Weight in kg) + (1.850 x Height in cm) -
	// (4.676 x Age in years)
	public void menBMR(int weightInKg, int heightInCm, int ageInYears) {
		try {
			double BMR = 655.1 + (9.563 * weightInKg) + (1.850 * heightInCm) - (4.676 * ageInYears);

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

	// The AMR (activity level) AMR = BMR x 1.2 for sedentary (little to no
	// exercise).
	public double Sedentary() {
		double result = 0;
		try {
			Statement s = connection.createStatement();
			ResultSet results = s.executeQuery("SELECT BMR, BMR * 1.2 AS AMR FROM User");
			while (results.next()) {
				result = results.getInt(results.findColumn("count"));
				System.out.println("User AMR: " + result);
			}
		} catch (SQLException sqle) {
			error(sqle);

		}
		return result;
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