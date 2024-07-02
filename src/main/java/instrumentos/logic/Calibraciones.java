package instrumentos.logic;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@XmlAccessorType(XmlAccessType.FIELD)
public class Calibraciones {
    @XmlIDREF
    private Instrumento instrumento;
    @XmlID
    private String numero;
    private String fecha;
    @XmlIDREF
    @XmlElementWrapper(name = "Mediciones")
    @XmlElement(name = "Medicion")
    private List<Mediciones> listMediciones;
    private String numMediciones;

    public Calibraciones() {
        this(null,"","","");
    }
    public Calibraciones(Instrumento o,String s,String a,  String s1) {
        listMediciones = new ArrayList<>();
        numero=s;
        instrumento=o;
        fecha=s1;
        numMediciones = a;
    }

    public List<Mediciones> getListMediciones() {
        return listMediciones;
    }

    public String getFecha() {
        return fecha;
    }
    public Instrumento getInstumento() {
        return instrumento;
    }
    public String getNumero() {
        return numero;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public void setInstumento(Instrumento instumento) {
        this.instrumento = instumento;
    }

    public String getNumMediciones() {
        return numMediciones;
    }

    public void setNumMediciones(String numMediciones) {
        this.numMediciones = numMediciones;
    }

    public void setListMediciones(List<Mediciones> listMediciones) {
        this.listMediciones = listMediciones;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
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
        final Calibraciones other = (Calibraciones) obj;
        if (!Objects.equals(this.numero, other.numero)) {
            return false;
        }
        return true;
    }
    public void crearMediciones(){
        int max = Integer. parseInt(instrumento.getRmax());
        int min = Integer. parseInt(instrumento.getRmin());
        int mediciones = Integer. parseInt(numMediciones);
        int n = (max-min)/mediciones;
        String num;
        String ref = String.valueOf(min);
        Mediciones a = new Mediciones("1",ref,"0");
        listMediciones.add(a);
        for (int i=1; i<mediciones;i++){
            num = String.valueOf(i+1);
            ref = String.valueOf(n);
            a = new Mediciones(num,ref,"0");
            listMediciones.add(a);
            n+=n;
        }
    }

    @Override
    public String toString() {
        return "Calibraciones => " + "instrumento: " + instrumento.getDescription() + ", numero: " + numero + ", fecha: " + fecha + ", numMediciones: " + numMediciones;
    }
}
