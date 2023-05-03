//import static spark.Spark.*;
import spark.Route;
import spark.Request;
import spark.Response;
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
        try (DataBase db = new DataBase()) {
            Long result = db.authenticateUser(email, password);
            response.status(200);
            return result;
        }catch (Exception e) {
            response.status(401);
            return e.getMessage();
        }
    };



    public static Route createMaleUser = (Request request, Response response) -> {
        int weightInKg = Integer.parseInt(request.queryParams("weightInKg"));
        int heightInCm = Integer.parseInt(request.queryParams("heightInCm"));
        int ageInYears = Integer.parseInt(request.queryParams("ageInYears"));
        try (DataBase db = new DataBase()) {
            db.menBMR(weightInKg, heightInCm, ageInYears);
        }
        return "A male user has been created";
    };

    public static Route createFemaleUser = (Request request, Response response) -> {
        int weightInKg = Integer.parseInt(request.queryParams("weightInKg"));
        int heightInCm = Integer.parseInt(request.queryParams("heightInCm"));
        int ageInYears = Integer.parseInt(request.queryParams("ageInYears"));
        try (DataBase db = new DataBase()) {
            db.womenBMR(weightInKg, heightInCm, ageInYears);
        }
        return "A female user has been created";
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

