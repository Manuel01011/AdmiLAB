package instrumentos.presentation.tipos;

import instrumentos.Application;
import instrumentos.logic.Service;
import instrumentos.logic.TipoInstrumento;

import java.util.List;

public class Controller{
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.init(Service.instance().search(new TipoInstrumento()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void search(TipoInstrumento filter) throws  Exception{
        List<TipoInstrumento> rows = Service.instance().search(filter);
        if (rows.isEmpty()){
            throw new Exception("NINGUN REGISTRO COINCIDE");
        }
        model.setList(rows);
       // model.setCurrent(new TipoInstrumento());
        model.setCurrent(rows.get(0));
        model.commit();
    }

    public void modificar(TipoInstrumento _istrumento) throws Exception {
        Service.instance().update(_istrumento);
        this.search(new TipoInstrumento()); //sirve para acutualizar la lista
    }

    public void borrar(TipoInstrumento _istrumento) throws Exception {
        Service.instance().delete(_istrumento);
        model.setModo(1);
        this.search(new TipoInstrumento());
    }


    public void save(TipoInstrumento a) throws Exception {
        try {
            Service.instance().create(a);
            model.setModo(1);
            this.search(new TipoInstrumento());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void edit(int row){
        //poner el current y cambiar el modo

        TipoInstrumento e = model.getList().get(row);
        try {
            model.setCurrent(Service.instance().read(e));
            model.commit();
        } catch (Exception ex) {}
    }
}
