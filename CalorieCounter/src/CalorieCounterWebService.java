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

		// Has been successfully tested in postman
		// Accessible via http://localhost:8088/food in your browser
		post("/food", (request, response) -> {
			String name = request.queryParams("name");
			int calories = Integer.parseInt(request.queryParams("calories"));
			try (DataBase db = new DataBase()) {
				db.createFood(name, calories);
			}
//			response.redirect("/test");
			return "Food has been added to the database";
		});

		// Accessible via http://localhost:8088/maleUser in your browser
		post("/maleUser", (request, response) -> {
			int weightInKg = Integer.parseInt(request.queryParams("weightInKg"));
			int heightInCm = Integer.parseInt(request.queryParams("heightInCm"));
			int ageInYears = Integer.parseInt(request.queryParams("ageInYears"));
			try (DataBase db = new DataBase()) {
				db.menBMR(weightInKg, heightInCm, ageInYears);
			}
			return "A male user has been created";
		});

		post("/femaleUser", (request, response) -> {
			int weightInKg = Integer.parseInt(request.queryParams("weightInKg"));
			int heightInCm = Integer.parseInt(request.queryParams("heightInCm"));
			int ageInYears = Integer.parseInt(request.queryParams("ageInYears"));
			try (DataBase db = new DataBase()) {
				db.womenBMR(weightInKg, heightInCm, ageInYears);
			}
			return "A female user has been created";
		});

		// Accessible via http://localhost:8088/sedentary in your browser
		// could be user/sedentary ????
		post("/sedentary", new Route() {
			@Override
			public Object handle(Request request, Response response) throws Exception {
				try (DataBase db = new DataBase()) {
					String result = db.sedentary();
					return result;
				}
			}
		});

		post("/lightlyActive", new Route() {
			@Override
			public Object handle(Request request, Response response) throws Exception {
				try (DataBase db = new DataBase()) {
					String result = db.lightlyActive();
					return result;
				}
			}
		});

		post("/moderatelyActive", new Route() {
			@Override
			public Object handle(Request request, Response response) throws Exception {
				try (DataBase db = new DataBase()) {
					String result = db.moderatelyActive();
					return result;
				}
			}
		});

		post("/active", new Route() {
			@Override
			public Object handle(Request request, Response response) throws Exception {
				try (DataBase db = new DataBase()) {
					String result = db.active();
					return result;
				}
			}
		});

		post("/veryActive", new Route() {
			@Override
			public Object handle(Request request, Response response) throws Exception {
				try (DataBase db = new DataBase()) {
					String result = db.veryActive();
					return result;
				}
			}
		});

	}

}