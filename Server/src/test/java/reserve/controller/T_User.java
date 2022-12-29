package reserve.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reserve.Main;
import reserve.model.User;
import reserve.util.AnsiLogger;
import reserve.util.Logger;

public class T_User {
	
	private UserController users;
	
	@BeforeAll
	public static void prepare_suite() {
		// keep only error logs
		Main.LOGGER_FACTORY = (name, logLevel) -> new AnsiLogger(name, Logger.LEVEL_ERROR);
	}
	
	@BeforeEach
	public void prepare_test() {
		users = new UserController();
	}

    // --------- users.addUser(new User(false, )) --------------- //
    @Test
    public void should_throw_exception_when_user_added_with_null_field_name() {

       Assertions.assertThrows(NullPointerException.class, () -> {
    	   users.addUser(new User(false, null, "na", "na", "na", "na"));
        });
    }
    @Test
    public void should_throw_exception_when_user_added_with_null_field_phone() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            users.addUser(new User(false, "na", null, "na", "na", "na"));
        });
    }
    @Test
    public void should_throw_exception_when_user_added_with_null_field_id() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            users.addUser(new User(false, "na", "na", null, "na", "na"));
        });
    }
    @Test
    public void should_throw_exception_when_user_added_with_null_field_email() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            users.addUser(new User(false, "na", "na", "na", null, "na"));
        });
    }
    @Test
    public void should_throw_exception_when_user_added_with_null_field_password() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            users.addUser(new User(false, "na", "na", "na", "na", null));
        });
    }

    // --------- UserController.removeUser() --------------- //

    @Test
    public void should_throw_exception_when_removed_user_is_null() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            users.removeUser(null);
        });

    }

    @Test
    public void should_return_false_when_removed_user_does_not_exist() {

        Assertions.assertFalse(users.removeUser(new User(false, "name", "phone", "id", "email", "password")));
    }

    @Test
    public void should_return_true_when_removed_user_exists() {

        User testUser = new User(false, "name", "phone","id", "email", "password");
        users.addUser(testUser);
        Assertions.assertTrue(users.removeUser(testUser));

    }

    // --------- UserController.getById() --------------- //

    @Test
    public void should_return_null_when_user_asked_with_id_is_not_in_users() {
        Assertions.assertNull(users.getById("notAnId"));
    }

    @Test
    public void should_return_user_when_asked_id_is_valid_and_user_exists() {
        // -- Setup
        User testUser = new User(false, "name", "phone", "itExists", "email", "password");
        users.addUser(testUser);

        // -- Test
        Assertions.assertNotNull(users.getById("itExists"));

        // -- Cleanup
        users.removeUser(testUser);

    }









}
