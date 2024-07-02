package instrumentos.Presentations.TipoInstrumento;

import instrumentos.Presentations.TipoInstrumento.Model;
import instrumentos.Presentations.TipoInstrumento.TableModel;
import instrumentos.logic.TipoInstrumento;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import instrumentos.Presentations.TipoInstrumento.Controller;

public class View implements Observer {
    private JPanel panel;
    private JTextField searchNombre;
    private JButton search;
    private JButton save;
    private JTable list;
    private JButton delete;
    private JLabel searchNombreLbl;
    private JButton report;
    private JTextField codigo;
    private JTextField nombre;
    private JTextField unidad;
    private JLabel codigoLbl;
    private JLabel nombreLbl;
    private JLabel unidadLbl;
    private JButton clear;


    public View() {
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!searchNombre.getText().isEmpty()) {
                    try {
                        TipoInstrumento filter = new TipoInstrumento();
                        filter.setNombre(searchNombre.getText());
                        controller.search(filter);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, "No se encontro ninguna coincidencia");
                    }
                }else{
                    JOptionPane.showMessageDialog(panel, "El campo esta vacio");
                }
            }
        });
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = list.getSelectedRow();
                model.setModo(2);
                controller.edit(row);
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(model.getModo() == 1) {
                    if( (!codigo.getText().isEmpty())&& (!nombre.getText().isEmpty()) && (!unidad.getText().isEmpty())){
                        TipoInstrumento istrumento1 = new TipoInstrumento(codigo.getText(), nombre.getText(), unidad.getText());
                        try {
                            controller.save(istrumento1);
                            codigo.setText("");
                            nombre.setText("");
                            unidad.setText("");
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(panel, "Instrumento ya existente");
                        }
                    }
                }else if(model.getModo() == 2){
                    TipoInstrumento istrumento2 = new TipoInstrumento(codigo.getText(), nombre.getText(), unidad.getText());
                    try {
                        controller.modificar(istrumento2);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TipoInstrumento istrumento3 = new TipoInstrumento(codigo.getText(), nombre.getText(), unidad.getText());
                try {
                    controller.borrar(istrumento3);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setModo(1);
                codigo.setEnabled(true);
                delete.setEnabled(false);
                codigo.setText("");
                nombre.setText("");
                unidad.setText("");
            }
        });
        report.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        controller.imprimir();
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    if(Desktop.isDesktopSupported()){
                        File myFile = new File("ListadeTiposInstrumentos.pdf");
                        try {
                            Desktop.getDesktop().open(myFile);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }

        });
    }

    public JPanel getPanel() {
        return panel;
    }

    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    //cambiar la forma de la vista y el listado
    @Override
    public void update(Observable updatedModel, Object properties) {
        int changedProps = (int) properties;
        if ((changedProps & Model.LIST) == Model.LIST) {
            int[] cols = {TableModel.CODIGO, TableModel.NOMBRE, TableModel.UNIDAD};
            list.setModel(new TableModel(cols, model.getList()));
            list.setRowHeight(30);
            TableColumnModel columnModel = list.getColumnModel();
            columnModel.getColumn(1).setPreferredWidth(200);
            //list.setRowSelectionInterval(0,0);
        }
        if ((changedProps & Model.CURRENT) == Model.CURRENT) {
            codigo.setText(model.getCurrent().getCodigo());
            nombre.setText(model.getCurrent().getNombre());
            unidad.setText(model.getCurrent().getUnidad());
        }
        //habilitar y desabilitar botones
        if(model.getModo() == 1){
            delete.setEnabled(false);
        }else if(model.getModo() == 2){
            delete.setEnabled(true);
            codigo.setEnabled(false);
        }
        this.panel.revalidate();
        if(model.getList().isEmpty()){

        }else{
            list.setRowSelectionInterval(0,0);
        }
    }

    private boolean isValid(){
        boolean valid = true;
        if (codigo.getText().isEmpty()){
            valid = false;
            codigo.setBackground(Color.red);
            codigo.setToolTipText("Codigo requerido");
        }else{
            codigo.setBackground(Color.white);
            codigo.setToolTipText(null);
        }

        if(nombre.getText().isEmpty()){
            valid = false;
            nombre.setBackground(Color.red);
            nombre.setToolTipText("Nombre requerido");
        }else{
            nombre.setBackground(Color.white);
            nombre.setToolTipText(null);
        }

        if(unidad.getText().isEmpty()){
            valid = false;
            unidad.setBackground(Color.red);
            unidad.setToolTipText("Unidad requerida");
        }else{
            unidad.setBackground(Color.white);
            unidad.setToolTipText(null);
        }
        if(valid == false){
            JOptionPane.showMessageDialog(panel, "Campos vacios");
        }
        return valid;
    }

}
