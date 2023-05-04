import spark.Filter;
import spark.Request;
import spark.Response;

import static spark.Spark.halt;

public class AuthMiddleware {
    public static Filter isAuthenticated = (Request request, Response response) -> {
        String token = request.headers("X-Authorization");
        try (DataBase db = new DataBase()) {
            Integer id = db.getIdFromToken(token);
            if (id == null) {
                response.status(401);
                response.body("Unauthorized");
                halt(401, "Unauthorized");
            }
            request.attribute("user_id", id);
        } catch (Exception e) {
            response.status(500);
            response.body("Internal Server Error");
        }
    };
}
