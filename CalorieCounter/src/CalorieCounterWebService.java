import static spark.Spark.*;
import static spark.Spark.port;

import spark.Request;
import spark.Response;
import spark.Route;

public class CalorieCounterWebService {

	public static void main(String[] args) {
		
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
		
		// Need a front-end to work correctly
		// Accessible via http://localhost:8088/food in your browser
		post("/food", (request, response) -> {
		    String name = request.queryParams("name");
		    int calories = Integer.parseInt(request.queryParams("calories"));
		    try (DataBase db = new DataBase()) {
		        db.createFood(name, calories);
		    }
		    response.redirect("/test");
		    return null;
		});

		
	}

}
