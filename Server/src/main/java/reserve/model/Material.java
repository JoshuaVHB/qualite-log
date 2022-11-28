package reserve.model;

public class Material
{

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    private Reservation     reservation;
    private OperatingSystem os;
    private MaterialType    type;
    private String          name;
    private String          version; // V0.8 ex
    private Integer         numRef; // INT(10) // TODO why not an int?

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    public Material() {
        this.reservation = null;
        this.os = OperatingSystem.XX;
        this.type = MaterialType.OTHER;
        this.name = "N/A";
        this.version = "N/A";
        this.numRef = 0;
    }

    public Material(OperatingSystem os, MaterialType type, String name, String version, Integer numRef) {
        this.os = os;
        this.type = type;
        this.name = name;
        this.version = version;
        this.numRef = numRef;
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    // -- Getters

    public Reservation getReservation()         { return reservation;   }
    public OperatingSystem getOs()              { return os;            }
    public String getName()                     { return name;          }
    public MaterialType getType()               { return type;          }
    public String getVersion()                  { return version;       }
    public Integer getNumRef()                  { return numRef;        }

    // -- Setters

    public Material setReservation(Reservation reservation)     { this.reservation = reservation;   return this;}
    public Material setOs(OperatingSystem os)                   { this.os = os;                     return this;}
    public Material setType(MaterialType type)                  { this.type = type;                 return this;}
    public Material setName(String name)                        { this.name = name;                 return this;}
    public Material setNumRef(Integer numRef)                   { this.numRef = numRef;             return this;}
    public Material setVersion(String version)                  { this.version = version;           return this;}

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

}
