package reserve.T_model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reserve.controller.UserController;
import reserve.model.User;

public class T_User {

    // --------- UserController.createUser() --------------- //
    @Test
    public void should_throw_exception_when_user_added_with_null_field_name() {

       Assertions.assertThrows(NullPointerException.class, () -> {
            UserController.createUser(null, "na", "na", "na");
        });
    }
    @Test
    public void should_throw_exception_when_user_added_with_null_field_phone() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            UserController.createUser("na", null, "na", "na");
        });
    }
    @Test
    public void should_throw_exception_when_user_added_with_null_field_id() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            UserController.createUser("na", "na", null, "na");
        });
    }
    @Test
    public void should_throw_exception_when_user_added_with_null_field_email() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            UserController.createUser("na", "na", "na", null);
        });
    }

    // --------- UserController.removeUser() --------------- //

    @Test
    public void should_throw_exception_when_removed_user_is_null() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            UserController.removeUser(null);
        });

    }

    @Test
    public void should_return_false_when_removed_user_does_not_exist() {

        Assertions.assertFalse(UserController.removeUser(new User(false, "name", "phone", "id", "email")));
    }

    @Test
    public void should_return_true_when_removed_user_exists() {

        User testUser = UserController.createUser("name", "phone","id", "email");
        Assertions.assertTrue(UserController.removeUser(testUser));

    }

    // --------- UserController.getById() --------------- //

    @Test
    public void should_return_null_when_user_asked_with_id_is_not_in_users() {
        Assertions.assertNull(UserController.getById("notAnId"));
    }

    @Test
    public void should_return_user_when_asked_id_is_valid_and_user_exists() {
        // -- Setup
        User testUser = UserController.createUser("name", "phone","itExists", "email");

        // -- Test
        Assertions.assertNotNull(UserController.getById("itExists"));

        // -- Cleanup
        UserController.removeUser(testUser);

    }









}
