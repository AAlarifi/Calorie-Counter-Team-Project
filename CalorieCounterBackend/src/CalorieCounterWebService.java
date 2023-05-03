import static spark.Spark.*;
public class CalorieCounterWebService {

	public static void main(String[] args) {

		port(8008);
		enableCORS("*", "*", "*");

		post("/maleUser", UserRoutes.createMaleUser);
		post("/femaleUser", UserRoutes.createFemaleUser);
		post("/sedentary", UserRoutes.sedentaryRoute);
		post("/lightlyActive", UserRoutes.lightlyActiveRoute);
		post("/moderatelyActive", UserRoutes.moderatelyActiveRoute);
		post("/active", UserRoutes.activeRoute);
		post("/veryActive", UserRoutes.veryActiveRoute);
		post("/loseWeight", UserRoutes.loseWeightRoute);
		post("/gainWeight", UserRoutes.gainWeightRoute);
		get("/getCalorieIntake", UserRoutes.getCalorieIntakeRoute);
		get("/foodCalories", FoodRoutes.getFoodCalories);
		post("/food", FoodRoutes.insertFoodRoute);
		get("/food/search", FoodRoutes.foodSearchRoute);
		get("/food/searchParser", FoodRoutes.foodSearchParser);
		post("/food/searchRequest",FoodRoutes.foodSearchRequest);
        post("/signup",UserRoutes.signup);
		post("/login",UserRoutes.login);


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