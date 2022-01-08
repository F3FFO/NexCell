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

    public SheetsView(JTextField CELLSELECTED, JTextField FORMULA) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        sheetStructure = new SheetStructure();
        SHEETS = new MyJTable(CELLSELECTED, FORMULA);
        model = new MyDataModel(sheetStructure);

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
