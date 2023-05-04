import spark.Filter;
import spark.Request;
import spark.Response;
import static spark.Spark.*;

import static spark.Spark.*;
public class CalorieCounterWebService {

	public static void main(String[] args) {

		port(8008);
		enableCORS("*", "*", "*");

		before("/secured/*", AuthMiddleware.isAuthenticated);


		post("/secured/maleUser", UserRoutes.createMaleUser);
		post("/secured/femaleUser", UserRoutes.createFemaleUser);
		post("/secured/sedentary",  UserRoutes.sedentaryRoute);
		post("/secured/lightlyActive", UserRoutes.lightlyActiveRoute);
		post("/secured/moderatelyActive", UserRoutes.moderatelyActiveRoute);
		post("/secured/active", UserRoutes.activeRoute);
		post("/secured/veryActive", UserRoutes.veryActiveRoute);
		post("/secured/loseWeight", UserRoutes.loseWeightRoute);
		post("/secured/gainWeight", UserRoutes.gainWeightRoute);
		get("/secured/getCalorieIntake", UserRoutes.getCalorieIntakeRoute);
		get("/secured/foodCalories", FoodRoutes.getFoodCalories);
		post("/secured/food", FoodRoutes.insertFoodRoute);
		get("/secured/food/search", FoodRoutes.foodSearchRoute);
		get("/secured/food/searchParser", FoodRoutes.foodSearchParser);
		post("/secured/food/searchRequest",FoodRoutes.foodSearchRequest);
        post("/signup",UserRoutes.signup);
		post("/login",UserRoutes.login);
		post("/secured/logout", UserRoutes.logout);


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