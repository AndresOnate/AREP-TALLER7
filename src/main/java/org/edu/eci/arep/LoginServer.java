package org.edu.eci.arep;

import org.edu.eci.arep.repository.UserService;

import java.net.URLEncoder;

import static spark.Spark.*;

public class LoginServer {

    public static void main(String[] args) {

        port(getPort());

        UserService userService = new UserService();

        staticFiles.location("/public");
        secure("certificados/ecikeystore.p12", "123456", null, null);
        get("/loginservice", (req, res) -> {

            Boolean successLogin =  userService.login(req.queryParams("user"), req.queryParams("passwd"));
            if(successLogin){
                return SecureURLReader.invokeService("hello");
            }else {
                return "Usuario o contraseña incorrecta";
            }
        }
        );
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }

}