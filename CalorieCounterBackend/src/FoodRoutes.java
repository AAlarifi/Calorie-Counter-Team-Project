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
    //private static final String API_ENDPOINT = "https://api.edamam.com/api/food-database/v2/parser";
    private static final String API_ENDPOINT = "https://api.edamam.com/auto-complete";
    private static final String API_APPLICATION_ID = "b588e64f";

    public static Route foodSearchRoute = (Request request, Response response) -> {
        String query = request.queryParams("query");
        HttpClient client = HttpClient.newHttpClient();

        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_ENDPOINT + "?app_id=" + API_APPLICATION_ID + "&app_key=" + API_KEY + "&q=" + encodedQuery))
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseBody = httpResponse.body();
            //JSONArray parsedFoods = responseJson.getJSONArray("parsed");
//        for (int i = 0; i < parsedFoods.length(); i++) {
//            JSONObject food = parsedFoods.getJSONObject(i).getJSONObject("food");
//            String name = food.getString("label");
//            int calories = food.getJSONObject("nutrients").getInt("ENERC_KCAL");
//
//            JSONObject foodItem = new JSONObject();
//            foodItem.put("name", name);
//            foodItem.put("calories", calories);
//
//            foodItems.put(foodItem);
//        }
            //JSONObject responseJson = new JSONObject(responseBody);
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

}
