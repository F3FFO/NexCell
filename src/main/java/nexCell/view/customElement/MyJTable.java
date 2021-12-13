package nexCell.view.customElement;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class MyJTable extends JTable {

    @Override
    public void setModel(TableModel dataModel) {
        super.setModel(dataModel);
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
