package org.edu.eci.arep;

import org.eclipse.jetty.http.HttpStatus;
import org.edu.eci.arep.repository.UserService;

import java.net.URLEncoder;

import static spark.Spark.*;

public class LoginServer {

    public static void main(String[] args) {

        port(getPort());

        UserService userService = new UserService();

        staticFiles.location("/public");
        secure("certificados/ec2/loginkeypair.p12", getKeyPassword(), null, null);

        get("/loginservice", (req, res) -> {
            Boolean successLogin =  userService.login(req.queryParams("user"), req.queryParams("passwd"));
            if(successLogin){
                return SecureURLReader.invokeService("hello");
            }else {
                res.status(HttpStatus.UNAUTHORIZED_401);
                return false;
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

    static String getKeyPassword() {
        if (System.getenv("KEY_PASSWORD") != null) {
            return System.getenv("KEY_PASSWORD");
        }
        return null;
    }

}