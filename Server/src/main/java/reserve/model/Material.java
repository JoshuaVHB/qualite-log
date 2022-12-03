package reserve.model;

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

    public Material() { // TODO is a default constructor necessary ?
        this.reservation = null;
        this.os = OperatingSystem.XX;
        this.type = MaterialType.OTHER;
        this.name = "N/A";
        this.version = "N/A"; // TODO why not null ?
        this.numRef = 0;
        this.id = UUID.randomUUID();
    }

    public Material(OperatingSystem os, MaterialType type, String name, String version, Integer numRef) {
    	// TODO require fields not to be null in constructor and setters
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

    public Material setReservation(Reservation reservation)     { this.reservation = reservation;   return this; }
    public Material setOs(OperatingSystem os)                   { this.os = os;                     return this; }
    public Material setType(MaterialType type)                  { this.type = type;                 return this; }
    public Material setName(String name)                        { this.name = name;                 return this; }
    public Material setNumRef(int numRef)                       { this.numRef = numRef;             return this; }
    public Material setVersion(String version)                  { this.version = version;           return this; }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

}
