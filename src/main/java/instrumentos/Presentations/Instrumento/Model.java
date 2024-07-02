package instrumentos.Presentations.Instrumento;

import instrumentos.logic.Instrumento;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import instrumentos.Application;
import instrumentos.logic.TipoInstrumento;

public class Model extends java.util.Observable{

    //toda lo que esta en la pantalla

      List<Instrumento> list;
      List <TipoInstrumento> listTipos;
      Instrumento current;

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

        public void init(List<Instrumento> liist,List<TipoInstrumento> listTipos){
            setList(liist);
            setListTipos(listTipos);
            setCurrent(new Instrumento());
        }

        public List<Instrumento> getList() {
            return list;
        }

        public void setList(List<Instrumento> liist){
            this.list = liist;
            changedProps +=LIST;
        }

        public Instrumento getCurrent() {
            return current;
        }
        public void setCurrent(Instrumento current) {
            changedProps +=CURRENT;
            this.current = current;
        }

    public List<TipoInstrumento> getListTipos() {
        return listTipos;
    }

    public void setListTipos(List<TipoInstrumento> listTipos) {
        changedProps +=TYPES;
        this.listTipos = listTipos;
    }

    public static int NONE=0;
        public static int LIST=1;
        public static int CURRENT=2;
        public static int TYPES=4;
    }

