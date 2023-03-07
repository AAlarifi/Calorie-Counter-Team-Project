import java.sql.Connection;
import java.sql.DriverManager;
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
