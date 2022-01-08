package nexCell.view.customElement.panel;

import nexCell.controller.MyDataModel;
import nexCell.controller.SheetStructure;
import nexCell.view.customElement.MyCellEditor;
import nexCell.view.customElement.MyJTable;

import javax.swing.*;

public class SheetsView extends JPanel {

    private final MyJTable SHEETS;
    private final SheetStructure sheetStructure;
    private final MyDataModel model;

    public SheetsView(SheetStructure sheetStructure, MyDataModel model, JTextField CELL_SELECTED, JTextField FORMULA) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.sheetStructure = sheetStructure;
        this.model = model;
        this.SHEETS = new MyJTable(CELL_SELECTED, FORMULA);

        SHEETS.setModel(this.model);
        SHEETS.changeSelection(0, 0, false, false);
        SHEETS.setDefaultEditor(Object.class, new MyCellEditor(sheetStructure, FORMULA));
        this.add(SHEETS);
    }

    public MyDataModel getModel() {
        return this.model;
    }

    public MyJTable getSHEETS() {
        return this.SHEETS;
    }

    public SheetStructure getSheetStructure() {
        return sheetStructure;
    }
}
