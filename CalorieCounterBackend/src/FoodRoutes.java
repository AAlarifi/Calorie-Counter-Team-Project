import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import spark.Request;
import spark.Response;
import spark.Route;
import org.json.JSONArray;
import org.json.JSONObject;

public class FoodRoutes {

    public static Route insertFoodRoute = (Request request, Response response) -> {
        String name = (request.queryParams("name"));
        int calories = Integer.parseInt(request.queryParams("calories"));
        int addedByUserId = Integer.parseInt(request.queryParams("addedByUserId"));
        try (DataBase db = new DataBase()) {
            db.createFood(name, calories, addedByUserId);
        }
        return "Food has been added to the database";
    };

    public static Route getFoodCalories = (Request request, Response response) -> {
        try (DataBase db = new DataBase()) {
            return db.foodCalories();
        }
    };

    private static final String API_KEY = "406bd66f1aa712d1f2d764df821325ff";
    //private static final String API_HOST = "edamam-recipe-search.p.rapidapi.com";

    private static final String API_ENDPOINT_AUTOCOMPLETE = "https://api.edamam.com/auto-complete";
    private static final String API_ENDPOINT_PARSER = "https://api.edamam.com/api/food-database/v2/parser";
    private static final String API_ENDPOINT_REQUEST = "https://api.edamam.com/api/food-database/v2/nutrients";
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
                .uri(URI.create(API_ENDPOINT_PARSER + "?app_id=" + API_APPLICATION_ID + "&app_key=" + API_KEY + "&ingr=" + encodedQuery + "&nutrition-type=logging"))
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            String responseBody = httpResponse.body();
            JSONObject responseJson = new JSONObject(responseBody);
            JSONArray parsedFood = responseJson.getJSONArray("parsed");
            JSONObject foodObj = parsedFood.getJSONObject(0).getJSONObject("food");
            String foodId = foodObj.getString("foodId");

            //creating a json object to hold the foodId


            response.header("Content-Type", "application/json");
            return foodId; // food id json should be here?

        }catch (Exception e){
            e.printStackTrace();
            response.status(500);
            return "Error: " + e.getMessage();
        }
    };
// x-www-form-urlencoded must be used for testing in Postman
    public static Route foodSearchRequest = (Request request, Response response) -> {
        HttpClient client = HttpClient.newHttpClient();
        int quantity = Integer.parseInt(request.queryParams("quantity"));
        String foodId = request.queryParams("foodId");
        String measureURI = request.queryParams("measureURI");

    // get the foodId from the foodSearchParser
    //String foodId = foodSearchParser(request, response);


    JSONObject requestBodyJson = new JSONObject();
        JSONArray ingredientsArray = new JSONArray();
        JSONObject ingredientObject = new JSONObject();
        ingredientObject.put("quantity", quantity);
        ingredientObject.put("measureURI", measureURI);
        ingredientObject.put("foodId", foodId);
        ingredientsArray.put(ingredientObject);
        requestBodyJson.put("ingredients", ingredientsArray);
        String requestBody = requestBodyJson.toString();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_ENDPOINT_REQUEST + "?app_id=" + API_APPLICATION_ID + "&app_key=" + API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseBody = httpResponse.body();
            JSONObject responseJson = new JSONObject(responseBody);
            response.header("Content-Type", "application/json");

            JSONObject totalNutrients = responseJson.getJSONObject("totalNutrients");
            JSONObject caloriesObject = totalNutrients.getJSONObject("ENERC_KCAL");
            JSONObject proteinObject = totalNutrients.getJSONObject("PROCNT");
            JSONObject carbsObject = totalNutrients.getJSONObject("CHOCDF");
            JSONObject fatObject = totalNutrients.getJSONObject("FAT");

            JSONObject nutritionObject = new JSONObject();
            nutritionObject.put("protein", Math.ceil(proteinObject.getDouble("quantity")* 100)/ 100);
            nutritionObject.put("fat", Math.ceil(fatObject.getDouble("quantity")* 100)/ 100);
            nutritionObject.put("carbs", Math.ceil(carbsObject.getDouble("quantity") * 100)/ 100);
            nutritionObject.put("calories", Math.ceil(caloriesObject.getDouble("quantity")));

            return nutritionObject;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            response.status(500);
            return "Error: " + e.getMessage();
        }
    };
}
