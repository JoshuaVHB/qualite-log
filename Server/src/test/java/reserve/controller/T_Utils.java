package reserve.controller;

import reserve.model.User;

public class T_Utils {

    public static User dummyUser() {
    	return new User(false, "name", "phone", "id", "email", "password");
    }
    
}
