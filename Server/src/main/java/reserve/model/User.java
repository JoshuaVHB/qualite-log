package reserve.model;

public class User {

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    private boolean isAdmin;
    private String nom; // FIX translate to english
    private String phone;
    private String id;
    private String email;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    public User(boolean isAdmin, String name, String phone, String id, String email) {
        this.isAdmin = isAdmin;
        this.nom = name;
        this.phone = phone;
        this.id = id;
        this.email = email;
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    // -- Getters

    public boolean isAdmin()        {return isAdmin;    }
    public String getNom()          {return nom;        }
    public String getPhone()    {return phone;  }
    public String getId()    {return id;  }
    public String getEmail()        {return email;      }

    // -- Setters

    public User setAdmin(boolean admin)         { this.isAdmin = admin;         return this;}
    public User setNom(String nom)              { this.nom = nom;               return this;}
    public User setPhone(String phone)  { this.phone = phone;   return this;}
    public User setId(String id)  { this.id = id;   return this;}
    public User setEmail(String email)          { this.email = email;           return this;}
    
    @Override
    public String toString() {
    	return String.format("User(id=%s name=%s%s)", id, nom, isAdmin ? " admin" : "");
    }

}
