package instrumentos.data;
import instrumentos.logic.Mediciones;
import instrumentos.logic.TipoInstrumento;
import instrumentos.logic.Instrumento;
import instrumentos.logic.Calibraciones;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {
    @XmlElementWrapper(name = "Tipos")
    @XmlElement(name = "tipo")
    private List<TipoInstrumento> tipos;
    @XmlElementWrapper(name = "Instrumentos")
    @XmlElement(name = "Instrumento")
    private List<Instrumento> instru;

    public Data(){
        tipos = new ArrayList<>();
        instru = new ArrayList<>();
        /*
        tipos.add(new TipoInstrumento("BAL","Balanza","Gramos"));
        TipoInstrumento t1 =new TipoInstrumento("TER","Termometro","Celcios");

        instru.add(new Instrumento("01",t1,"Termometro nex","90","0","1000"));
        Instrumento in = new Instrumento("2020",t1,"Balanza Hash","2","10","2000");
        Calibraciones a = new Calibraciones(in,"100","5","13/9/2023");
        in.getLista().add(a);
        calibra.add(a);
        mediciones.add(new Mediciones("1","0","11"));

        //tipos.add(t1);*/
    }

    public List<TipoInstrumento> getTipos() {
        return tipos;
    }

    public List<Instrumento> getIntru() {
        return instru;
    }
}

