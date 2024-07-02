package instrumentos.logic;

import  instrumentos.data.Data;
import instrumentos.data.XmlPersister;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Service {
    private static Service theInstance;

    public static Service instance(){
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }
    private Data data;

    private Service(){
        try {
            data = XmlPersister.instance().load();
        }
        catch (Exception e)
        {
            data = new Data();
        }
    }

    public void stop(){
        try{
            XmlPersister.instance().store(data);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    //================= TIPOS DE INSTRUMENTO ============
    public void create(TipoInstrumento e) throws Exception{
        TipoInstrumento result = data.getTipos().stream()
                .filter(i->i.getCodigo().equals(e.getCodigo())).findFirst().orElse(null);
        if (result==null) data.getTipos().add(e);
        else throw new Exception("Tipo ya existe");
    }

    public void create(Instrumento e) throws Exception{
        Instrumento result = data.getIntru().stream()
                .filter(i->i.getNumSerie().equals(e.getNumSerie())).findFirst().orElse(null);
        if (result==null) data.getIntru().add(e);
        else throw new Exception("Instrumento ya existe");
    }

    public void createe(Instrumento a,Calibraciones e) throws Exception{
        Calibraciones result = a.getLista().stream()
                .filter(i->i.getNumero().equals(e.getNumero())).findFirst().orElse(null);
        if (result==null) {
            e.crearMediciones();
            a.getLista().add(e);
        }
        else throw new Exception("Calibracion ya existe");
    }

    public TipoInstrumento read(TipoInstrumento e) throws Exception{
        TipoInstrumento result = data.getTipos().stream()
                .filter(i->i.getCodigo().equals(e.getCodigo())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Tipo no existe");
    }

    public Instrumento read(Instrumento e) throws Exception{
        Instrumento result = data.getIntru().stream()
                .filter(i->i.getNumSerie().equals(e.getNumSerie())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Instrumento no existe");
    }
    public Calibraciones readd(Instrumento a,Calibraciones e) throws Exception{
        Calibraciones result = a.getLista().stream()
                .filter(i->i.getNumero().equals(e.getNumero())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Calibracion no existe");
    }

    public void update(TipoInstrumento e) throws Exception{
        TipoInstrumento result;
        try{
            result = this.read(e);
            data.getTipos().remove(result);
            data.getTipos().add(e);
        }catch (Exception ex) {
            throw new Exception("Tipo no existe");
        }
    }
    public void update(Instrumento e) throws Exception{
        Instrumento result;
        try{
            result = this.read(e);
            data.getIntru().remove(result);
            data.getIntru().add(e);
        }catch (Exception ex) {
            throw new Exception("Instrumento no existe");
        }
    }
    public void update(Instrumento e,Calibraciones b) throws Exception{
        Calibraciones result;
        try{
            result = this.readd(e,b);
            e.getLista().remove(b);
            e.getLista().add(b);
        }catch (Exception ex) {
            throw new Exception("Calibracion no existe");
        }
    }

    //para el boton de elimanar
    public void delete(TipoInstrumento e) throws Exception{
        data.getTipos().remove(e);
     }
    public void delete(Instrumento e) throws Exception{
        data.getIntru().remove(e);
    }

    public void deletee(Instrumento a,Calibraciones e) throws Exception{
        a.getLista().remove(e);
    }

     //cambiar con que atributos se ordena la lista
    public List<TipoInstrumento> search(TipoInstrumento e){
        return data.getTipos().stream()
                .filter(i->i.getNombre().contains(e.getNombre()))
                .sorted(Comparator.comparing(TipoInstrumento::getNombre))
                .collect(Collectors.toList());
    }

    public List<Instrumento> search(Instrumento e){
        return data.getIntru().stream()
                .filter(i->i.getDescription().contains(e.getDescription()))
                .sorted(Comparator.comparing(Instrumento::getDescription))
                .collect(Collectors.toList());
    }

    //necesita un istrumento para setertalo
    public List<Calibraciones> search2(Instrumento a,Calibraciones e){
        return a.getLista().stream()
                .filter(i->i.getNumero().contains(e.getNumero()))
                .sorted(Comparator.comparing(Calibraciones::getNumero))
                .collect(Collectors.toList());
    }
 }
