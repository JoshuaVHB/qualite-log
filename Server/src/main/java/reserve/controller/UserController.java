package reserve.controller;

import reserve.model.User;

import java.util.List;
import java.util.Objects;


public class UserController {

    private static List<User> users;

    public static void createUser(String name, String phone, String id, String email){

        users.add(new User(false, name, phone, id, email));

    }

    static void removeUser(User toRemove) {

        Objects.requireNonNull(toRemove); // Make sur the user is coherent

        if (users.remove(toRemove)) {

            // Log sucessful

        } else {

            // Log

        }

    }


}
