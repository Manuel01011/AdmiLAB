package instrumentos.Presentations.Calibraciones;

import instrumentos.logic.Calibraciones;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel implements javax.swing.table.TableModel{

        List<Calibraciones> rows;
        int[] cols;

        public TableModel(int[] cols, List<Calibraciones> rows){
            this.cols=cols;
            this.rows=rows;
            initColNames();
        }

        public int getColumnCount() {
            return cols.length;
        }

        public String getColumnName(int col){
            return colNames[cols[col]];
        }

        public Class<?> getColumnClass(int col){
            switch (cols[col]){
                default: return super.getColumnClass(col);
            }
        }

        public int getRowCount() {
            return rows.size();
        }

        public Object getValueAt(int row, int col) {
            Calibraciones sucursal = rows.get(row);
            switch (cols[col]){
                case NUMERO: return sucursal.getNumero();
                case FECHA: return sucursal.getFecha();
                case MEDICIONES: return sucursal.getNumMediciones();
                default: return "";
            }
        }

        public Calibraciones getRowAt(int row) {
            return rows.get(row);
        }

        public static final int NUMERO=0;
        public static final int FECHA=1;
        public static final int MEDICIONES=2;


        String[] colNames = new String[6];
        private void initColNames(){
            colNames[NUMERO]= "Numero";
            colNames[FECHA]= "Fecha";
            colNames[MEDICIONES]= "Mediciones";

        }

    }


