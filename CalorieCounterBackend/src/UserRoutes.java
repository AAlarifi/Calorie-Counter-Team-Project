//import static spark.Spark.*;
import spark.Route;
import spark.Request;
import spark.Response;
public class UserRoutes {

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

