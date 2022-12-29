package reserve.model;

import java.util.Objects;

public class User {

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	private final String id;
    private boolean isAdmin;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    public User(boolean isAdmin, String firstName, String lastName, String phone, String id, String email, String password) {
        this.isAdmin = isAdmin;
        this.firstName = Objects.requireNonNull(firstName);
        this.lastName = Objects.requireNonNull(lastName);
        this.phone = Objects.requireNonNull(phone);
        this.id = Objects.requireNonNull(id);
        this.email = Objects.requireNonNull(email);
        this.password = Objects.requireNonNull(password);
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    // -- Getters

    public boolean isAdmin()        {return isAdmin; }
    public String getFirstName()    {return firstName;}
    public String getLastName()     {return lastName;}
    public String getPhone()        {return phone;   }
    public String getId()           {return id;      }
    public String getEmail()        {return email;   }
    public String getPassword()     {return password;}

    // -- Setters
    
    // TODO add Objects.requireNonNull in setters
    public User setAdmin(boolean admin)         { this.isAdmin = admin;     return this;}
    public User setFirstName(String name)       { this.firstName = name;    return this;}
    public User setLastName(String name)        { this.lastName = name;     return this;}
    public User setPhone(String phone)          { this.phone = phone;       return this;}
    public User setEmail(String email)          { this.email = email;       return this;}
    
    @Override
    public String toString() {
    	return String.format("User(id=%s name=%s %s%s)", id, firstName, lastName, isAdmin ? " admin" : "");
    }
    
    @Override
    public boolean equals(Object o) {
    	return o instanceof User && ((User) o).id.equals(id);
    }
    
    @Override
    public int hashCode() {
    	return id.hashCode();
    }

}
