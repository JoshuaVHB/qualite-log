package reserve.model;

import java.util.Objects;

public class User {

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    private boolean isAdmin;
    private String name;
    private String phone;
    private String id;
    private String email;
    private String password;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    public User(boolean isAdmin, String name, String phone, String id, String email, String password) {
        this.isAdmin = isAdmin;
        this.name = Objects.requireNonNull(name);
        this.phone = Objects.requireNonNull(phone);
        this.id = Objects.requireNonNull(id);
        this.email = Objects.requireNonNull(email);
        this.password = Objects.requireNonNull(password);
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    // -- Getters

    public boolean isAdmin()        {return isAdmin; }
    public String getName()         {return name;    }
    public String getPhone()        {return phone;   }
    public String getId()           {return id;      }
    public String getEmail()        {return email;   }
    public String getPassword()     {return password;}

    // -- Setters

    public User setAdmin(boolean admin)         { this.isAdmin = admin;     return this;}
    public User setName(String name)            { this.name = name;         return this;}
    public User setPhone(String phone)          { this.phone = phone;       return this;}
    public User setId(String id)                { this.id = id;             return this;}
    public User setEmail(String email)          { this.email = email;       return this;}
    
    @Override
    public String toString() {
    	return String.format("User(id=%s name=%s%s)", id, name, isAdmin ? " admin" : "");
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
