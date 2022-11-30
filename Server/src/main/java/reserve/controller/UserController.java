package reserve.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import reserve.model.User;

public class UserController {
	
	// FIX make controllers non-static, use a singleton instead
	// that way UTs and dev tests can use mocks

    // TODO TEST THIS
	public static final String USER_NAME_FORMAT = "[a-zA-Z]+"; // TODO change regexes to match specs
	public static final String PASSWORD_FORMAT = "[a-zA-Z]+";
	
    private static List<User> users = new ArrayList<User>();

    // ------------------------------------------------------------------------------------------- //

    /**
     * @brief Adds a new user to the list.
     * @param name
     * @param phone
     * @param id
     * @param email
     * @return The user that was added
     * @throws NullPointerException {@code name, phone, id or email} is null
     */
    public static User createUser(String name, String phone, String id, String email){

        Objects.requireNonNull(name);
        Objects.requireNonNull(phone);
        Objects.requireNonNull(id);
        Objects.requireNonNull(email);

        User added = new User(false, name, phone, id, email);
        users.add(added);
        // Serialize

        return added;

    }

    /**
     * Gets the user with the desired ID.
     * @param id : String
     * @return User if the id is found, null otherwise.
     */
    public static User getById(String id) {
        User result = users.stream()
                        .filter(u -> u.getId() == id)
                        .findAny()
                        .orElse(null);
    	return result;
    }
    

    public static boolean removeUser(User toRemove) {

        Objects.requireNonNull(toRemove); // Make sure the user is coherent

        if (users.remove(toRemove)) {

            // Log sucessful
            return true;

        } else {

            // Log

            return false;

        }

    }

    // How do i test this
    
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
