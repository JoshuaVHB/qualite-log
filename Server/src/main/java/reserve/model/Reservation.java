package reserve.model;

import java.time.LocalDate;

public class Reservation {


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    private User        owner;
    private Material    material;
    private LocalDate   beginning;
    private LocalDate   ending;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    public Reservation(User owner, Material material, LocalDate beginning, LocalDate ending) {
        this.owner = owner;
        this.material = material;
        this.beginning = beginning;
        this.ending = ending;
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    public User getOwner()              { return owner;     }
    public Material getMaterial()       { return material;  }
    public LocalDate getBeginning()     { return beginning; }
    public LocalDate getEnding()        { return ending;    }

    public Reservation setOwner(User owner)                { this.owner = owner;            return this;}
    public Reservation setMaterial(Material material)      { this.material = material;      return this;}
    public Reservation setBeginning(LocalDate beginning)   { this.beginning = beginning;    return this;}
    public Reservation setEnding(LocalDate ending)         { this.ending = ending;          return this;}






}




