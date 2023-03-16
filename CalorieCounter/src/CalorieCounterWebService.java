import static spark.Spark.*;

import spark.Request;
import spark.Response;
import spark.Route;

public class CalorieCounterWebService {

	public static void main(String[] args) {

		port(8008);
		enableCORS("*", "*", "*");

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

		post("/loseWeight", new Route() {
			@Override
			public Object handle(Request request, Response response) throws Exception {
				try (DataBase db = new DataBase()) {
					String result = db.loseWeight();
					return result;
				}
			}
		});

		post("/gainWeight", new Route() {
			@Override
			public Object handle(Request request, Response response) throws Exception {
				try (DataBase db = new DataBase()) {
					String result = db.gainWeight();
					return result;
				}
			}
		});

		get("/getCalorieIntake", new Route() {
			@Override
			public Object handle(Request request, Response response) throws Exception {
				try (DataBase db = new DataBase()) {
					return db.getCalorieIntake();
				}
			}
		});
		
		
	}
	
	
	
	private static void enableCORS(final String origin, final String methods, final String headers) {

		options("/*", (request, response) -> {

			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
			if (accessControlRequestHeaders != null) {
				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}

			String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
			if (accessControlRequestMethod != null) {
				response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
			}

			return "OK";
		});

		before((request, response) -> {
			response.header("Access-Control-Allow-Origin", origin);
			response.header("Access-Control-Request-Method", methods);
			response.header("Access-Control-Allow-Headers", headers);
		});
	}

}