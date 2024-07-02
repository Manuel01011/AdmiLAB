package instrumentos.logic;
import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
public class Mediciones {
    @XmlID
    private String numero;
    private String referencia;
    private String lectura;

    public Mediciones(String numero, String referencia, String lectura) {
        this.numero = numero;
        this.referencia = referencia;
        this.lectura = lectura;
    }

    public Mediciones() {
        this.numero = "";
        this.referencia = "";
        this.lectura = "";
    }

    public String getNumero() {
        return numero;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getLectura() {
        return lectura;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setLectura(String lectura) {
        this.lectura = lectura;
    }

    @Override
    public String toString() {
        return "Mediciones => " + "numero: " + numero + ", referencia: " + referencia + ", lectura: " + lectura;
    }
}
