package instrumentos.presentation.tipos;

import instrumentos.Application;
import instrumentos.logic.TipoInstrumento;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

//toda lo que esta en la pantalla
public class Model extends java.util.Observable{
    List<TipoInstrumento> list;
    TipoInstrumento current;

    int modo = 1; //1 = agregar    2 = modificar  3 = borrar

    int changedProps = NONE;

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
        commit();
    }

    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public void commit(){
        setChanged();
        notifyObservers(changedProps);
        changedProps = NONE;
    }

    public Model() {
    }

    public void init(List<TipoInstrumento> list){
        setList(list);
        setCurrent(new TipoInstrumento());
    }

    public List<TipoInstrumento> getList() {
        return list;
    }
    public void setList(List<TipoInstrumento> list){
        this.list = list;
        changedProps +=LIST;
    }

    public TipoInstrumento getCurrent() {
        return current;
    }
    public void setCurrent(TipoInstrumento current) {
        changedProps +=CURRENT;
        this.current = current;
    }

    public static int NONE=0;
    public static int LIST=1;
    public static int CURRENT=2;
}
