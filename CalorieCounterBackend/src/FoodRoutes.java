import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import spark.Request;
import spark.Response;
import spark.Route;
import org.json.JSONArray;
import org.json.JSONObject;

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

    private static final String API_KEY = "406bd66f1aa712d1f2d764df821325ff";
    private static final String API_HOST = "edamam-recipe-search.p.rapidapi.com";

    private static final String API_ENDPOINT_AUTOCOMPLETE = "https://api.edamam.com/auto-complete";
    private static final String API_ENPOINT_PARSER = "https://api.edamam.com/api/food-database/v2/parser";
    private static final String API_ENPOINT_REQUEST = "https://api.edamam.com/api/food-database/v2/nutrients";
    private static final String API_APPLICATION_ID = "b588e64f";

    // Search bar auto-complete
    public static Route foodSearchRoute = (Request request, Response response) -> {
        String query = request.queryParams("query");
        HttpClient client = HttpClient.newHttpClient();

        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_ENDPOINT_AUTOCOMPLETE + "?app_id=" + API_APPLICATION_ID + "&app_key=" + API_KEY + "&q=" + encodedQuery))
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseBody = httpResponse.body();
            JSONArray foodNames = new JSONArray(responseBody);

            JSONArray foodList = new JSONArray();
            for (int i = 0; i < foodNames.length(); i++) {
                String foodName = foodNames.getString(i);
                JSONObject foodObj = new JSONObject();
                foodObj.put("name", foodName);
                foodList.put(foodObj);
            }


        response.header("Content-Type", "application/json");
        return foodList;
    }catch (Exception e){
        e.printStackTrace();
        response.status(500);
        return "Error: " + e.getMessage();
    }
    };

    // Gets the foodId from parsed food.
    public static Route foodSearchParser = (Request request, Response response) -> {
        String query = request.queryParams("query");
        HttpClient client = HttpClient.newHttpClient();

        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_ENPOINT_PARSER + "?app_id=" + API_APPLICATION_ID + "&app_key=" + API_KEY + "&ingr=" + encodedQuery + "&nutrition-type=logging"))
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            String responseBody = httpResponse.body();
            JSONObject responseJson = new JSONObject(responseBody);
            JSONArray parsedFood = responseJson.getJSONArray("parsed");
            JSONObject foodObj = parsedFood.getJSONObject(0).getJSONObject("food");
            String foodId = foodObj.getString("foodId");
            JSONObject foodOject = new JSONObject();
            foodOject.put("foodId", foodId);


            response.header("Content-Type", "application/json");
            return foodOject;

        }catch (Exception e){
            e.printStackTrace();
            response.status(500);
            return "Error: " + e.getMessage();
        }
    };

}
