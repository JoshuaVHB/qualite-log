package reserve.model;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    private User        owner;
    private Material    material;
    private LocalDate   beginning;
    private LocalDate   ending;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    public Reservation(User owner, Material material, LocalDate beginning, LocalDate ending) {
        this.owner = Objects.requireNonNull(owner);
        this.material = Objects.requireNonNull(material);
        this.beginning = Objects.requireNonNull(beginning);
        this.ending = Objects.requireNonNull(ending);

        if (ending.compareTo(beginning) <= 0) // reservation starts after or at the end ...
            throw new IllegalArgumentException("The reservation starting date must be before the ending date !");
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




