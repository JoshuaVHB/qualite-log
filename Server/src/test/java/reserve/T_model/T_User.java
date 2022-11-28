package reserve.T_model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reserve.controller.UserController;

public class T_User {

    @Test
    public void should_throw_exception_when_user_added_with_null_field_name() {

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            UserController.createUser(null, "na", "na", "na");
        });
    }
    @Test
    public void should_throw_exception_when_user_added_with_null_field_phone() {

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            UserController.createUser("na", null, "na", "na");
        });
    }
    @Test
    public void should_throw_exception_when_user_added_with_null_field_id() {

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            UserController.createUser("na", "na", null, "na");
        });
    }
    @Test
    public void should_throw_exception_when_user_added_with_null_field_email() {

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            UserController.createUser("na", "na", "na", null);
        });
    }







}
