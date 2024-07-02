package instrumentos.Presentations.Instrumento;
import instrumentos.logic.Instrumento;
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

public class view implements Observer{
    private JPanel panel1;

    public JPanel getPanel1() {
        return panel1;
    }

    private JButton save;
    private JButton delete;
    private JButton clear;
    private JLabel codigoLbl;
    private JTextField serie;
    private JLabel nombreLbl;
    private JTextField minimo;
    private JLabel unidadLbl;
    private JTextField descripcion;
    private JLabel searchNombreLbl;
    private JTextField searchNombre;
    private JButton search;
    private JButton report;
    private JTable lista;
    private JComboBox tipo;
    private JTextField tolerancia;
    private JTextField maximo;



public view(){
    search.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!searchNombre.getText().isEmpty()) {
                try {
                    Instrumento filter = new Instrumento();
                    filter.setDescription(searchNombre.getText());
                    controller.search(filter);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel1, "No se encontro ninguna coincidencia");
                }
            }else{
                JOptionPane.showMessageDialog(panel1, "El campo esta vacio");
            }
        }
    });
    lista.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int row = lista.getSelectedRow();
            model.setModo(2);
            controller.edit(row);
        }
    });

    save.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(model.getModo() == 1) {
                if( (!serie.getText().isEmpty())&& (!minimo.getText().isEmpty()) && (!tolerancia.getText().isEmpty()) && (!descripcion.getText().isEmpty()) && (!maximo.getText().isEmpty())){
                    if(Integer.parseInt(maximo.getText())  > Integer.parseInt(minimo.getText())) {
                        Instrumento istrumento1 = new Instrumento(serie.getText(), (TipoInstrumento) tipo.getSelectedItem(), descripcion.getText(), maximo.getText(), minimo.getText(), tolerancia.getText());
                        try {
                            controller.save(istrumento1);
                            serie.setText("");
                            minimo.setText("");
                            tolerancia.setText("");
                            descripcion.setText("");
                            maximo.setText("");
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(panel1, "Instrumento ya existente");
                        }
                    }else{
                        JOptionPane.showMessageDialog(panel1, "Rango Maximo es menor al Rango minimo");
                    }
                }
            }else if(model.getModo() == 2){
                Instrumento istrumento2 = new Instrumento(serie.getText(), (TipoInstrumento) tipo.getSelectedItem(), descripcion.getText(),maximo.getText(),minimo.getText(),tolerancia.getText());
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
            Instrumento istrumento3 = new Instrumento(serie.getText(), (TipoInstrumento) tipo.getSelectedItem(), descripcion.getText(),maximo.getText(),minimo.getText(),tolerancia.getText());
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
            delete.setEnabled(false);
            serie.setEnabled(true);
            serie.setText("");
            minimo.setText("");
            tolerancia.setText("");
            descripcion.setText("");
            maximo.setText("");
        }
    });

    panel1.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentShown(ComponentEvent e) {
            super.componentShown(e);
            controller.show();
        }
    });
    report.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                controller.imprimir();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            if(Desktop.isDesktopSupported()){
                File myFile = new File("archivo.pdf");
                try {
                    Desktop.getDesktop().open(myFile);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    });
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

    @Override
    public void update(Observable updatedModel, Object properties) {
            int changedProps = (int) properties;
            if ((changedProps & instrumentos.Presentations.Instrumento.Model.LIST) == instrumentos.Presentations.Instrumento.Model.LIST) {
                int[] cols = {instrumentos.Presentations.Instrumento.TableModel.SERIE, instrumentos.Presentations.Instrumento.TableModel.DESCRIPTION
                        , instrumentos.Presentations.Instrumento.TableModel.MIN, instrumentos.Presentations.Instrumento.TableModel.MAX, instrumentos.Presentations.Instrumento.TableModel.TOLERANCIA};
                lista.setModel(new TableModel(cols, model.getList()));
                lista.setRowHeight(30);
                TableColumnModel columnModel = lista.getColumnModel();
                columnModel.getColumn(1).setPreferredWidth(200);
                //lista.setRowSelectionInterval(0,0);
            }

            if((changedProps & instrumentos.Presentations.Instrumento.Model.TYPES) == instrumentos.Presentations.Instrumento.Model.TYPES){
                 tipo.setModel(new DefaultComboBoxModel(model.getListTipos().toArray(new TipoInstrumento[0])));
                 if(model.getCurrent() != null) {
                     tipo.setSelectedItem(model.getCurrent().getTipo());
                 }
            }
            //pone los valore de la tabla en los espacios
            if ((changedProps & instrumentos.Presentations.Instrumento.Model.CURRENT) == instrumentos.Presentations.Instrumento.Model.CURRENT) {
                serie.setText(model.getCurrent().getNumSerie());
                minimo.setText(model.getCurrent().getRmin());
                descripcion.setText(model.getCurrent().getDescription());
                tolerancia.setText(model.getCurrent().getTolerancia());
                maximo.setText(model.getCurrent().getRmax());
                tipo.setSelectedItem((TipoInstrumento) model.getCurrent().getTipo());
            }
            //habilitar y desabilitar botones
            if(model.getModo() == 1){
                delete.setEnabled(false);
            }else if(model.getModo() == 2){
                delete.setEnabled(true);
                serie.setEnabled(false);
            }
            this.panel1.revalidate();
            if(model.getList().isEmpty()){
            }else{
                lista.setRowSelectionInterval(0,0);
            }
            }
    private boolean isValid(){
        boolean valid = true;
        if (serie.getText().isEmpty()){
            valid = false;
            serie.setBackground(Color.red);
            serie.setToolTipText("Serie requerida");
        }else{
            serie.setBackground(Color.white);
            serie.setToolTipText(null);
        }

        if(descripcion.getText().isEmpty()){
            valid = false;
            descripcion.setBackground(Color.red);
            descripcion.setToolTipText("Descripcion requerida");
        }else{
            descripcion.setBackground(Color.white);
            descripcion.setToolTipText(null);
        }

        if(minimo.getText().isEmpty()){
            valid = false;
            minimo.setBackground(Color.red);
            minimo.setToolTipText("Unidad Minima requerida");
        }else{
            minimo.setBackground(Color.white);
            minimo.setToolTipText(null);
        }

        if(maximo.getText().isEmpty()){
            valid = false;
            maximo.setBackground(Color.red);
            maximo.setToolTipText("Unidad Maxima requerida");
        }else{
            maximo.setBackground(Color.white);
            maximo.setToolTipText(null);
        }

        if(tolerancia.getText().isEmpty()){
            valid = false;
            tolerancia.setBackground(Color.red);
            tolerancia.setToolTipText("Tolerancia requerida");
        }else{
            tolerancia.setBackground(Color.white);
            tolerancia.setToolTipText(null);
        }

        if(valid == false){
            JOptionPane.showMessageDialog(panel1, "Campos vacios");
        }
        return valid;
    }
}
