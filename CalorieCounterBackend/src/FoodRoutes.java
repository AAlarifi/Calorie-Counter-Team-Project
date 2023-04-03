import spark.Request;
import spark.Response;
import spark.Route;

public class FoodRoutes {

    public static Route insertFoodRoute = (Request request, Response response) -> {
        String name = (request.queryParams("name"));
        int calories = Integer.parseInt(request.queryParams("calories"));
        try (DataBase db = new DataBase()) {
            db.createFood(name, calories);
        }
        return "Food has been added to the database";
    };

    public static Route getFoodCalories = (Request request, Response response) -> {
        try (DataBase db = new DataBase()) {
            return db.foodCalories();
        }
    };


}
