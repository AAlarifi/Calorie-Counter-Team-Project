import static spark.Spark.get;
import static spark.Spark.port;

import spark.Request;
import spark.Response;
import spark.Route;

public class CalorieCounterWebService {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		port(8088);

		// Simple route so you can check things are working...
		// Accessible via http://localhost:8088/test in your browser
		get("/test", new Route() {
			@Override
			public Object handle(Request request, Response response) throws Exception {
				try (DataBase db = new DataBase()) {
					return "Number of Entries: " + db.getNumberOfEntries();
				}
			}
		});

	}

}
