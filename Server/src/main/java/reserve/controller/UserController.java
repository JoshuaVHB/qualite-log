package reserve.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import reserve.model.User;

public class UserController {
	
	// FIX make controllers non-static, use a singleton instead
	// that way UTs and dev tests can use mocks

	public static final String USER_NAME_FORMAT = "[a-zA-Z]+"; // TODO change regexes to match specs
	public static final String PASSWORD_FORMAT = "[a-zA-Z]+";
	
    private static List<User> users = new ArrayList<>();

    // ------------------------------------------------------------------------------------------- //

    /**
     * @brief Adds a new user to the list.
     * @param name
     * @param phone
     * @param id
     * @param email
     * @throws NullPointerException {@code name, phone, id or email} is null
     */
    public static void createUser(String name, String phone, String id, String email){

        Objects.requireNonNull(name);
        Objects.requireNonNull(phone);
        Objects.requireNonNull(id);
        Objects.requireNonNull(email);

        users.add(new User(false, name, phone, id, email));
        // Serialize

    }
    public static User getById(String id) {
    	for (User u:users) {
    		if (u.getId() == id) return u;
    	}
    	return null;
    }
    

    static void removeUser(User toRemove) {

        Objects.requireNonNull(toRemove); // Make sur the user is coherent

        if (users.remove(toRemove)) {

            // Log sucessful

        } else {

            // Log

        }

    }
    
    public static User authentifyUser(String userName, String password) {
    	if("password".equals(password))
    		return new User(false, "name", "phone", "id", "email"); // TODO mock better
    	return null;
    }
    
    public static User getUserById(String id) throws NoSuchElementException {
		return new User(false, "name", "phone", "id", "email"); // TODO mock better
//    	return users.stream().filter(u -> u.getId().equals(id)).findFirst().get();
    }


}
