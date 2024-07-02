package instrumentos.Presentations.Calibraciones;

import instrumentos.logic.Calibraciones;
import instrumentos.Application;
import instrumentos.logic.Instrumento;
import instrumentos.logic.Mediciones;

import java.util.List;
import java.util.Observer;
import java.util.ArrayList;

public class Model extends java.util.Observable{
    List<Calibraciones> list;
    Calibraciones current;
    Instrumento instrumento;
    List<Mediciones> listMedicione;

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

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
        changedProps +=INTRUMENTO;
    }

        public void init(List<Calibraciones> list){
            setList(list);
            setCurrent(new Calibraciones());
            setInstrumento(new Instrumento());
        }

        public List<Calibraciones> getList() {
            return list;
        }

        public void setList(List<Calibraciones> lisst){
            this.list =lisst;
            changedProps +=LIST;
        }

        public Calibraciones getCurrent() {
            return current;
        }

        public void setCurrent(Calibraciones current) {
            changedProps +=CURRENT;
            this.current = current;
        }

        public void setListMedicione(List<Mediciones> list){
            this.listMedicione = list;
            changedProps +=MEDICIONES;
        }

    public List<Mediciones> getListMedicione() {
        return listMedicione;
    }

    public static int NONE=0;
        public static int LIST=1;
        public static int CURRENT=2;
        public static int INTRUMENTO=4;
        public static int MEDICIONES=8;
    }

