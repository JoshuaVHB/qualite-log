package reserve.controller;

import reserve.model.User;


import java.util.List;
import java.util.Objects;

// TODO : Serialize everything
public class UserController {

    private static List<User> users;


    // ------------------------------------------------------------------------------------------- //

    /**
     * @brief Adds a new user to the list.
     * @param name
     * @param phone
     * @param id
     * @param email
     */
    public static void createUser(String name, String phone, String id, String email){

        users.add(new User(false, name, phone, id, email));
        // Serialize

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
