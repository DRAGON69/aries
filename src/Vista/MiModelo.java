/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import javax.swing.table.DefaultTableModel;

public class MiModelo extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        // Aquí devolvemos true o false según queramos que una celda
        // identificada por fila,columna (row,column), sea o no editable
//        if (column == 8 || column == 9 || column == 12 || column == 13 || column == 15) {
//            return true;
//        }
        return false;
    }

    @Override
    public Class getColumnClass(int c) {
        if (c == 14) {
            return Boolean.class;
        }
        return String.class;
    }
}
