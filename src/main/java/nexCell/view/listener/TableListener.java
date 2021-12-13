package nexCell.view.listener;

import nexCell.controller.SheetStructure;
import nexCell.view.customElement.MyJTable;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class TableListener implements TableModelListener {

    private SheetStructure sheetStructure;
    private MyJTable SHEETS;

    public TableListener(SheetStructure sheetStructure, MyJTable SHEETS) {
        this.sheetStructure = sheetStructure;
        this.SHEETS = SHEETS;
    }

    @Override
    public void tableChanged(TableModelEvent tableModelEvent) {
        int row = SHEETS.getSelectedRow();
        int column = SHEETS.getSelectedColumn();
        Object value = SHEETS.getModel().getValueAt(row, column);
        int type = this.sheetStructure.checkTypeCell(value);
        try {
            this.sheetStructure.convertCell(row, column, value, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
