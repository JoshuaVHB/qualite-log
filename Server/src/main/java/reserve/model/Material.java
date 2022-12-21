package reserve.model;

import java.util.Objects;
import java.util.UUID;

public class Material
{

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    private Reservation     reservation;
    private OperatingSystem os;
    private MaterialType    type;
    private String          name;
    private String          version; // V0.8 ex
    private int             numRef; // INT(10)
    private UUID            id;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    public Material() {
        this(OperatingSystem.XX, MaterialType.OTHER, "N/A", "N/A", 0);
    }

    public Material(OperatingSystem os, MaterialType type, String name, String version, int numRef) {

        Objects.requireNonNull(os);
        Objects.requireNonNull(type);
        Objects.requireNonNull(name);
        Objects.requireNonNull(version);
        Objects.requireNonNull(numRef);

        this.os = os;
        this.type = type;
        this.name = name;
        this.version = version;
        this.numRef = numRef;
        this.id = UUID.randomUUID();
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    // -- Getters

    public Reservation getReservation()         { return reservation;   }
    public OperatingSystem getOs()              { return os;            }
    public String getName()                     { return name;          }
    public MaterialType getType()               { return type;          }
    public String getVersion()                  { return version;       }
    public int getNumRef()                      { return numRef;        }
    public UUID getId()                         { return this.id;       }

    // -- Setters

    public Material setReservation(Reservation reservation) {
        this.reservation = reservation;
        return this;
    }
    public Material setOs(OperatingSystem os) {
        Objects.requireNonNull(os);
        this.os = os;
        return this;
    }
    public Material setType(MaterialType type) {
        Objects.requireNonNull(type);
        this.type = type;
        return this;
    }
    public Material setName(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    public Material setNumRef(int numRef) {
        Objects.requireNonNull(numRef);
        this.numRef = numRef;
        return this;
    }
    public Material setVersion(String version) {
        Objects.requireNonNull(version);
        this.version = version;
        return this;
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

}
