package org.edu.eci.arep;

import org.edu.eci.arep.repository.UserService;

import static spark.Spark.*;

public class HelloServer {

    public static void main(String[] args) {

        port(getPort());

        UserService userService = new UserService();

        staticFiles.location("/public");
        secure("certificados/ecikeystore.p12", "123456", null, null);
        get("/hello", (req, res) -> "Hello World");
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }

}