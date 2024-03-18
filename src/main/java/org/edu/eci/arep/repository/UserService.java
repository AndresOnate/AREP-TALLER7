package org.edu.eci.arep.repository;

import java.nio.charset.StandardCharsets;
import java.util.Hashtable;

import java.security.MessageDigest;

public class UserService {

    private static Hashtable<String, String> users = new Hashtable<>();
    public UserService() {
        addUser("Andres", "123456");
    }

    public void addUser(String username, String passwd) {
        String encodePasswd = encryptText(passwd);
        users.put(username, encodePasswd);
    }

    public static String encryptText(String text){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            return  bytesToHex(encodedhash);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Boolean login(String username, String password){
        Boolean success = false;
        String encondedPassword = encryptText(password);
        if(users.containsKey(username)){
            System.out.println("Existe el usuario");
            return encondedPassword.equals(users.get(username));
        }
        return false;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
