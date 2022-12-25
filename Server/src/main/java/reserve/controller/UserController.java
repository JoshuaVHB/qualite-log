package reserve.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import reserve.Main;
import reserve.model.User;
import reserve.util.Logger;

public class UserController {
	
    // TODO TEST THIS
	public static final String USER_NAME_FORMAT = "[a-zA-Z]+"; // TODO change regexes to match specs
	public static final String PASSWORD_FORMAT = "[a-zA-Z]+";
	
	private static Logger logger = Main.LOGGER_FACTORY.getLogger("users", Logger.LEVEL_DEBUG);
	
    private final List<User> users = new ArrayList<>();

    // ------------------------------------------------------------------------------------------- //

    /**
     * Adds a new user to the list.
     */
    public void addUser(User user) {
        Objects.requireNonNull(user);
        if(getById(user.getId()) != null)
        	throw new IllegalArgumentException("Duplicate user id found");
        users.add(user);
        logger.debug("Created user " + user);
    }

    /**
     * Gets the user with the desired ID
     * @return User if the id is found, null otherwise.
     */
    public User getById(String id) {
        return users.stream()
                    .filter(u -> u.getId().equals(id))
                    .findAny()
                    .orElse(null);
    }

	public List<User> getUsers() {
		return Collections.unmodifiableList(users);
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
    		return new User(false, "name", "phone", "id", "email", "password"); // TODO mock better
    	return null;
    }
    
}
