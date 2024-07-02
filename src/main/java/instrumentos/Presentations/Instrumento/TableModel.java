package instrumentos.Presentations.Instrumento;

import instrumentos.logic.Instrumento;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel implements javax.swing.table.TableModel {
        List<Instrumento> rows;
        int[] cols;
        public TableModel(int[] cols, List<Instrumento> rows){
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
            Instrumento sucursal = rows.get(row);
            switch (cols[col]){
                case SERIE: return sucursal.getNumSerie();
                case DESCRIPTION: return sucursal.getDescription();
                case MIN: return sucursal.getRmin();
                case MAX: return sucursal.getRmax();
                case TOLERANCIA: return sucursal.getTolerancia();

                default: return "";
            }
        }

        public Instrumento getRowAt(int row) {
            return rows.get(row);
        }

        public static final int SERIE=0;
        public static final int DESCRIPTION=1;
        public static final int MIN=2;
        public static final int MAX=3;
        public static final int TOLERANCIA=4;
        public static final int TIPO=5;

        String[] colNames = new String[6];
        private void initColNames(){
            colNames[SERIE]= "No.Serie";
            colNames[DESCRIPTION]= "Descripcion";
            colNames[MIN]= "Minimo";
            colNames[MAX]= "Maximo";
            colNames[TOLERANCIA]= "Tolerancia";
        }


}
