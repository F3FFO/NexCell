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
        int type = sheetStructure.checkTypeCell(SHEETS.getModel().getValueAt(SHEETS.getSelectedRow(), SHEETS.getSelectedColumn()));
        System.out.println("prova tipo -> " + type);
    }
}
