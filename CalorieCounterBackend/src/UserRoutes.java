//import static spark.Spark.*;
import spark.Route;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.*;
public class UserRoutes {

    public static Route signup = (Request request, Response response) -> {
        String firstName = (request.queryParams("firstName"));
        String lastName = (request.queryParams("lastName"));
        String email = (request.queryParams("email"));
        String password = (request.queryParams("password"));
        try (DataBase db = new DataBase()) {
            db.addNewUser(firstName, lastName, email, password);
            return "A user has signed up";
        }catch (Exception e) {
            response.status(400);
            return e.getMessage();
        }
    };

    public static Route login = (Request request, Response response) -> {
        String email = (request.queryParams("email"));
        String password = (request.queryParams("password"));
        // Check if email and password are not null or empty
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            response.status(400);
            return "Email and password are required";
        }
        try (DataBase db = new DataBase()) {
            Integer  id = db.authenticateUser(email, password);
            if (id != null) {
                // Check if token exists for the user
                String token = db.getToken(id);
                if (token != null) {
                    response.status(200);
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("user_id", id);
                    resultMap.put("session_token", token);
                    return resultMap;
                } else {
                    String newToken = db.setToken(id);
                    response.status(200);
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("user_id", id);
                    resultMap.put("session_token", newToken);
                    return resultMap;
                }
            } else {
                response.status(400);
                return "Invalid email/password supplied!";
            }
        } catch (Exception e) {
            response.status(500);
            return "Internal Server Error";
        }
    };

    public static Route logout = (Request request, Response response) -> {
        String token = request.headers("authToken");
        if (token == null) {
            response.status(401);
            return "User isn't logged in!";
        }

        try (DataBase db = new DataBase()) {
            Integer userId = db.getIdFromToken(token);
            if (userId == null) {
                response.status(401);
                return "User isn't logged in!";
            }

            db.removeToken(token);
            response.status(200);
            return "";
        } catch (SQLException e) {
            response.status(500);
            return "Internal Server Error";
        }
    };


    public static Route createMaleUser = (Request request, Response response) -> {
        int weightInKg = Integer.parseInt(request.queryParams("weightInKg"));
        int heightInCm = Integer.parseInt(request.queryParams("heightInCm"));
        int ageInYears = Integer.parseInt(request.queryParams("ageInYears"));
        try (DataBase db = new DataBase()) {
            db.menBMR(weightInKg, heightInCm, ageInYears);
            return "A male user has been created";
        }
    };

    public static Route createFemaleUser = (Request request, Response response) -> {
        int weightInKg = Integer.parseInt(request.queryParams("weightInKg"));
        int heightInCm = Integer.parseInt(request.queryParams("heightInCm"));
        int ageInYears = Integer.parseInt(request.queryParams("ageInYears"));
        try (DataBase db = new DataBase()) {
            db.womenBMR(weightInKg, heightInCm, ageInYears);
            return "A female user has been created";
        }
    };

    public static Route sedentaryRoute = (Request request, Response response) -> {
        try (DataBase db = new DataBase()) {
            String result = db.sedentary();
            return result;
        }
    };

    public static Route lightlyActiveRoute = (Request request, Response response) -> {
        try (DataBase db = new DataBase()) {
            String result = db.lightlyActive();
            return result;
        }
    };

    public static Route moderatelyActiveRoute = (Request request, Response response) -> {
        try (DataBase db = new DataBase()) {
            String result = db.moderatelyActive();
            return result;
        }
    };

    public static Route activeRoute = (Request request, Response response) -> {
        try (DataBase db = new DataBase()) {
            String result = db.active();
            return result;
        }
    };

    public static Route veryActiveRoute = (Request request, Response response) -> {
        try (DataBase db = new DataBase()) {
            String result = db.veryActive();
            return result;
        }
    };

    public static Route loseWeightRoute = (Request request, Response response) -> {
        try (DataBase db = new DataBase()) {
            String result = db.loseWeight();
            return result;
        }
    };

    public static Route gainWeightRoute = (Request request, Response response) -> {
        try (DataBase db = new DataBase()) {
            String result = db.gainWeight();
            return result;
        }
    };

    public static Route getCalorieIntakeRoute = (Request request, Response response) -> {
        try (DataBase db = new DataBase()) {
            return db.getCalorieIntake();
        }
    };

}

