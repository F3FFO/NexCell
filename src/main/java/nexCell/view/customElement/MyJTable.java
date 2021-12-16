package nexCell.view.customElement;

import javax.swing.*;
import javax.swing.table.TableColumnModel;

public class MyJTable extends JTable {

    public MyJTable() {
        super.setCellSelectionEnabled(true);
    }

    @Override
    public void doLayout() {
        if (tableHeader != null) {
            if (tableHeader.getResizingColumn() == null) {
                TableColumnModel tcm = getColumnModel();
                int lastColumn = tcm.getColumnCount() - 1;
                tableHeader.setResizingColumn(tcm.getColumn(lastColumn));
            }
        }
        super.doLayout();
    }
}
