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
	public static final String USER_ID_FORMAT = "[0-9]+"; // TODO change regexes to match specs
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
    

    public void removeUser(User toRemove) {
        if (!users.remove(toRemove))
        	throw new IllegalArgumentException("Unknown user");
        logger.debug("Removed user " + toRemove);
    }
    
    public User authentifyUser(String userId, String password) {
    	return users.stream()
    		.filter(u->u.getId().equals(userId))
    		.filter(u->u.getPassword().equals(password))
    		.findAny().orElse(null);
    }
    
    /**
     * Returns a new <i>copy</i> of the users list, changes are not
     * reflected back to this instance's list.
     * 
     * @return a <i>copy</i> of the users list
     */
    public List<User> getAllUsers() {
    	return new ArrayList<>(users);
    }

	public String getNextUserId() {
		while(true) {
			int uid = (int) (Math.random() * 1E9);
			String generated = String.format("%09d", uid);
			if(users.stream().map(User::getId).allMatch(id->generated.equals(id)))
				continue;
			if(!generated.matches(USER_ID_FORMAT))
				throw new RuntimeException("Invalid generated id");
			return generated;
		}
	}
    
}
