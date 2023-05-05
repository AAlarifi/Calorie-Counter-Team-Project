//import static spark.Spark.*;

import org.json.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserRoutes {

    public static Route signup = (Request request, Response response) -> {
        String firstName = (request.queryParams("firstName"));
        String lastName = (request.queryParams("lastName"));
        String email = (request.queryParams("email"));
        String password = (request.queryParams("password"));
        try (DataBase db = new DataBase()) {
            db.addNewUser(firstName, lastName, email, password);
            return "A user has signed up";
        } catch (Exception e) {
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
            Integer id = db.authenticateUser(email, password);
            if (id != null) {
                // Check if token exists for the user
                String token = db.getToken(id);
                if (token != null) {
                    response.status(200);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("user_id", id);
                    jsonObject.put("session_token", token);
                    return jsonObject.toString();
                } else {
                    String newToken = db.setToken(id);
                    response.status(200);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("user_id", id);
                    jsonObject.put("session_token", newToken);
                    return jsonObject.toString();
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
        String token = request.headers("X-Authorization");
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
        String token = request.headers("X-Authorization");
        try (DataBase db = new DataBase()) {
            Integer userId = db.getIdFromToken(token);
            if (userId == null) {
                return "Invalid session token";
            }
            db.menBMR(weightInKg, heightInCm, ageInYears, userId);
            return "A male user has been created";
        } catch (SQLException e) {
            response.status(500);
            return "Internal Server Error";
        }
    };

    public static Route createFemaleUser = (Request request, Response response) -> {
        int weightInKg = Integer.parseInt(request.queryParams("weightInKg"));
        int heightInCm = Integer.parseInt(request.queryParams("heightInCm"));
        int ageInYears = Integer.parseInt(request.queryParams("ageInYears"));
        String token = request.headers("X-Authorization");
        try (DataBase db = new DataBase()) {
            Integer userId = db.getIdFromToken(token);
            if (userId == null) {
                return "Invalid session token";
            }
            db.womenBMR(weightInKg, heightInCm, ageInYears, userId);
            return "A female user has been created";
        } catch (SQLException e) {
            response.status(500);
            return "Internal Server Error";
        }
    };

    public static Route sedentaryRoute = (Request request, Response response) -> {
        String token = request.headers("X-Authorization");
        try (DataBase db = new DataBase()) {
            Integer userId = db.getIdFromToken(token);
            if (userId == null) {
                return "Invalid session token";
            }
            String result = db.sedentary(userId);
            return result;
        } catch (SQLException e) {
            response.status(500);
            return "Internal Server Error";
        }
    };

    public static Route lightlyActiveRoute = (Request request, Response response) -> {
        String token = request.headers("X-Authorization");
        try (DataBase db = new DataBase()) {
            Integer userId = db.getIdFromToken(token);
            if (userId == null) {
                return "Invalid session token";
            }
            String result = db.lightlyActive(userId);
            return result;
        } catch (SQLException e) {
            response.status(500);
            return "Internal Server Error";
        }
    };

    public static Route moderatelyActiveRoute = (Request request, Response response) -> {
        String token = request.headers("X-Authorization");
        try (DataBase db = new DataBase()) {
            Integer userId = db.getIdFromToken(token);
            if (userId == null) {
                return "Invalid session token";
            }
            String result = db.moderatelyActive(userId);
            return result;
        } catch (SQLException e) {
            response.status(500);
            return "Internal Server Error";
        }
    };

    public static Route activeRoute = (Request request, Response response) -> {
        String token = request.headers("X-Authorization");
        try (DataBase db = new DataBase()) {
            Integer userId = db.getIdFromToken(token);
            if (userId == null) {
                return "Invalid session token";
            }
            String result = db.active(userId);
            return result;
        } catch (SQLException e) {
            response.status(500);
            return "Internal Server Error";
        }
    };

    public static Route veryActiveRoute = (Request request, Response response) -> {
        String token = request.headers("X-Authorization");
        try (DataBase db = new DataBase()) {
            Integer userId = db.getIdFromToken(token);
            if (userId == null) {
                return "Invalid session token";
            }
            String result = db.veryActive(userId);
            return result;
        } catch (SQLException e) {
            response.status(500);
            return "Internal Server Error";
        }
    };

    public static Route loseWeightRoute = (Request request, Response response) -> {
        String token = request.headers("X-Authorization");
        try (DataBase db = new DataBase()) {
            Integer userId = db.getIdFromToken(token);
            if (userId == null) {
                response.status(401);
                return "Invalid session token";
            }
            String result = db.loseWeight(userId);
            return result;
        } catch (SQLException e) {
            response.status(500);
            return "Internal Server Error";
        }
    };

    public static Route gainWeightRoute = (Request request, Response response) -> {
        String token = request.headers("X-Authorization");
        try (DataBase db = new DataBase()) {
            Integer userId = db.getIdFromToken(token);
            if (userId == null) {
                response.status(401);
                return "Invalid session token";
            }
            String result = db.gainWeight(userId);
            return result;
        } catch (SQLException e) {
            response.status(500);
            return "Internal Server Error";
        }
    };

    public static Route getCalorieIntakeRoute = (Request request, Response response) -> {
        String token = request.headers("X-Authorization");
        try (DataBase db = new DataBase()) {
            Integer userId = db.getIdFromToken(token);
            if (userId == null) {
                response.status(401);
                return "Invalid session token";
            }
            double calorieIntake = db.getCalorieIntake(userId);
            return String.valueOf(calorieIntake);
        } catch (SQLException e) {
            response.status(500);
            return "Internal Server Error";
        }
    };

}

