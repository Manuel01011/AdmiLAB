package instrumentos.logic;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Instrumento {
    @XmlIDREF
    private TipoInstrumento tipo;
    @XmlID
    private String numSerie;
    private String description;
    private String rmax;
    private String rmin;
    private String tolerancia;
    @XmlIDREF
    @XmlElementWrapper(name = "Calibraciones")
    @XmlElement(name = "Calibracion")
    List<Calibraciones> listCalibracion;

    public Instrumento() {
        this("",null,"","","","");
    }

    public Instrumento(String s, TipoInstrumento o, String s1, String i, String i1, String i2) {
        listCalibracion = new ArrayList<>();
        numSerie=s;
        tipo=o;
        description=s1;
        rmax=i;
        rmin=i1;
        tolerancia=i2;
    }

    public String getDescription() {
        return description;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public String getRmax() {
        return rmax;
    }

    public String getRmin() {
        return rmin;
    }

    public TipoInstrumento getTipo() {
        return tipo;
    }
    public String StringTipo() {
        return tipo.toString();
    }

    public  List<Calibraciones> getLista(){
        return listCalibracion;
    }


    public String getTolerancia() {
        return tolerancia;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public void setRmax(String rmax) {
        this.rmax = rmax;
    }

    public void setRmin(String rmin) {
        this.rmin = rmin;
    }

    public void setTipo(TipoInstrumento tipo) {
        this.tipo = tipo;
    }

    public void setTolerancia(String tolerancia) {
        this.tolerancia = tolerancia;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Instrumento other = (Instrumento) obj;
        if (!Objects.equals(this.numSerie, other.numSerie)) {
            return false;
        }
        return true;
    }
   public String tosstring() {
       if (tipo != null) {
           return numSerie + " - " + description + " (" +  rmin + " - " + rmax + " " + tipo.getUnidad() + ")";
       }
       return "Ningun instrumento";
   }

    public String toString() {
        return "No de serie: " + numSerie + ", Tipo: " + tipo.getNombre() + ", Description: " + description + ", Rango maximo: " + rmax +
                ", Rango Minimo: " + rmin +
                ", Tolerancia: " + tolerancia; //+
             //   ", Calibracion: " + listCalibracion;
    }
}
