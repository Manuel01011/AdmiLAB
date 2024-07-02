package instrumentos.Presentations.Calibraciones;

import instrumentos.logic.Calibraciones;
import instrumentos.logic.Instrumento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observer;

import javax.swing.table.TableColumnModel;
import java.util.Observable;


public class ViewCalibracion implements Observer {

    private JPanel panel2;
    private JLabel searchNombreLbl;
    private JTextField searchNombre;
    private JButton search;
    private JButton report;
    private JTable list;
    private JButton save;
    private JButton delete;
    private JButton clear;
    private JLabel codigoLbl;
    private JTextField numero;
    private JLabel nombreLbl;
    private JTextField mediciones;
    private JLabel unidadLbl;
    private JTextField fecha;
    private JLabel instrumento;
    private JTable medicione;
    private JScrollPane panelMedi;

    public JPanel getPanel2() {
        return panel2;
    }

    public ViewCalibracion() {
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!searchNombre.getText().isEmpty()) {
                    try {
                        Calibraciones filter = new Calibraciones();
                        filter.setNumero(searchNombre.getText());
                        controller.search2(model.getInstrumento(),filter);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel2, "No se encontro ninguna coincidencia");
                    }
                } else {
                    JOptionPane.showMessageDialog(panel2, "El campo esta vacio");
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
                if (model.getModo() == 1) {
                    if ((!numero.getText().isEmpty()) && (!fecha.getText().isEmpty()) && (!mediciones.getText().isEmpty()) ) {
                            Calibraciones calibracion1 = new Calibraciones(model.getInstrumento(),numero.getText(),mediciones.getText(), fecha.getText());
                            try {
                                controller.save(model.getInstrumento(),calibracion1);
                                numero.setText("");
                                fecha.setText("");
                                mediciones.setText("");
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(panel2, "Calibracion ya existente");
                            }
                    }
                } else if (model.getModo() == 2) {
                    Calibraciones Calibracion2 = new Calibraciones(model.getInstrumento(),numero.getText(),mediciones.getText(), fecha.getText());
                    try {
                        controller.modificar(model.getInstrumento(),Calibracion2);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calibraciones Calibracion3 = new Calibraciones(model.getInstrumento(),numero.getText(),mediciones.getText(),  fecha.getText());
                try {
                    controller.borrar2(model.getCurrent().getInstumento(),Calibracion3);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setModo(1);
                delete.setEnabled(false);
                numero.setEnabled(true);
                numero.setText("");
                fecha.setText("");
                mediciones.setText("");

            }
        });
        report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    controller.imprimir(model.getInstrumento());
                if(Desktop.isDesktopSupported()){
                    File myFile = new File("ListadeCalibraciones.pdf");
                    try {
                        Desktop.getDesktop().open(myFile);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    instrumentos.Presentations.Calibraciones.Controller controller;
    instrumentos.Presentations.Calibraciones.Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    @Override
    public void update(Observable updatedModel, Object properties) {
        int[] cools = {instrumentos.Presentations.Calibraciones.TableModel.NUMERO, instrumentos.Presentations.Calibraciones.TableModel.FECHA
                , TableModel.MEDICIONES};
            int changedProps = (int) properties;
            if ((changedProps & instrumentos.Presentations.Calibraciones.Model.LIST) == instrumentos.Presentations.Calibraciones.Model.LIST) {
                list.setModel(new TableModel(cools, model.getInstrumento().getLista()));
                list.setRowHeight(30);
                TableColumnModel columnModel = list.getColumnModel();
                columnModel.getColumn(1).setPreferredWidth(200);
              //  list.setRowSelectionInterval(0,0);
            }
            if ((changedProps & instrumentos.Presentations.Calibraciones.Model.CURRENT) == instrumentos.Presentations.Calibraciones.Model.CURRENT) {
                numero.setText(model.getCurrent().getNumero());
                mediciones.setText(model.getCurrent().getNumMediciones());
                fecha.setText(model.getCurrent().getFecha());

            }
        //int changedPropsMediciones = (int) properties;
        if ((changedProps & Model.MEDICIONES) == Model.MEDICIONES) {
            int[] cols = {TableModelMedicione.MEDIDA, TableModelMedicione.REFERENCIA
                    , TableModelMedicione.LECTURA};
            medicione.setModel(new TableModelMedicione(cols, model.getListMedicione()));
            medicione.setRowHeight(30);
            TableColumnModel columnModel = medicione.getColumnModel();
            columnModel.getColumn(1).setPreferredWidth(200);
            //medicione.setRowSelectionInterval(0,0);
        }
        if ((changedProps & Model.INTRUMENTO) == Model.INTRUMENTO){
            instrumento.setText(model.getInstrumento().tosstring());
            instrumento.setForeground(Color.red);
        }
            //habilitar y desabilitar botones
            if(model.getModo() == 1){
                delete.setEnabled(false);
                panelMedi.setEnabled(false);
            }else if(model.getModo() == 2){
                delete.setEnabled(true);
                numero.setEnabled(false);
                medicione.setEnabled(true);
            }
            this.panel2.revalidate();
            list.setModel(new TableModel(cools, model.getInstrumento().getLista()));
            if(model.getInstrumento().getLista().isEmpty()){

            }else{
                list.setRowSelectionInterval(0,0);
            }
    }
    private boolean isValid(){
        boolean valid = true;
        if (numero.getText().isEmpty()){
            valid = false;
            numero.setBackground(Color.red);
            numero.setToolTipText("serie requerida");
        }else{
            numero.setBackground(Color.white);
            numero.setToolTipText(null);
        }

        if(fecha.getText().isEmpty()){
            valid = false;
            fecha.setBackground(Color.red);
            fecha.setToolTipText("Descripcion requerida");
        }else{
            fecha.setBackground(Color.white);
            fecha.setToolTipText(null);
        }

        if(mediciones.getText().isEmpty()){
            valid = false;
            mediciones.setBackground(Color.red);
            mediciones.setToolTipText("Unidad Minima requerida");
        }else{
            mediciones.setBackground(Color.white);
            mediciones.setToolTipText(null);
        }

        if(valid == false){
            JOptionPane.showMessageDialog(panel2, "Campos vacios");
        }
        return valid;
    }
}
