package reserve.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import reserve.model.User;

public class UserController {
	
    // TODO TEST THIS
	public static final String USER_NAME_FORMAT = "[a-zA-Z]+"; // TODO change regexes to match specs
	public static final String PASSWORD_FORMAT = "[a-zA-Z]+";
	
    private final List<User> users = new ArrayList<>();

    // ------------------------------------------------------------------------------------------- //

    /**
     * Adds a new user to the list.
     */
    public void addUser(User user) {
        Objects.requireNonNull(user);
        // TODO check for dupplicates
        users.add(user);
    }

    /**
     * Gets the user with the desired ID.
     * @param id : String
     * @return User if the id is found, null otherwise.
     */
    public User getById(String id) {
        return users.stream()
                    .filter(u -> u.getId().equals(id))
                    .findAny()
                    .orElse(null);
    }
    

    public boolean removeUser(User toRemove) {

        Objects.requireNonNull(toRemove); // Make sure the user is coherent

        if (users.remove(toRemove)) {

            // Log successful
            return true;

        } else {

            // Log

            return false;

        }

    }

    // How do I test this
    
    public User authentifyUser(String userName, String password) {
    	if("password".equals(password))
    		return new User(false, "name", "phone", "id", "email"); // TODO mock better
    	return null;
    }
    
}
