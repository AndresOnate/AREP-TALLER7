package org.edu.eci.arep;

import org.edu.eci.arep.repository.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static spark.Spark.*;

public class HelloServer {

    private static final Map<String, String> serviceUsers = new HashMap<>();

    public static void main(String[] args) {

        serviceUsers.put("loginUser", "kQtTObGHYxgf4SbS");

        port(getPort());

        UserService userService = new UserService();

        secure("certificados/localhost/ecikeystore.p12", getKeyPassword(), null, null);
        get("/hello", (req, res) -> {
            String usuario = req.queryParams("user");
            String clave = req.queryParams("passwd");
            if (validarCredenciales(usuario, clave)) {
                String randomString = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
                return "Tu código secreto es: " + randomString;
            } else {
                res.status(401);
                return "Credenciales inválidas";
            }
        });
    }

    static boolean validarCredenciales(String usuario, String clave) {
        return serviceUsers.containsKey(usuario) && serviceUsers.get(usuario).equals(clave);
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }

    static String getKeyPassword() {
        if (System.getenv("KEY_PASSWORD") != null) {
            return System.getenv("KEY_PASSWORD");
        }
        return null;
    }

}