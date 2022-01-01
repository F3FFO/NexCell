package nexCell.view.customElement.panel;

import nexCell.controller.MyDataModel;
import nexCell.controller.SheetStructure;
import nexCell.view.customElement.MyCellEditor;
import nexCell.view.customElement.MyJTable;

import javax.swing.*;

public class SheetsView extends JPanel {

    private final MyJTable SHEETS = new MyJTable();
    private final SheetStructure sheetStructure;
    private final MyDataModel model;

    public SheetsView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        sheetStructure = new SheetStructure();
        model = new MyDataModel(sheetStructure);

        SHEETS.setModel(this.model);
        SHEETS.getTableHeader().setReorderingAllowed(Boolean.FALSE);
        SHEETS.setDefaultEditor(Object.class, MyCellEditor.make(sheetStructure));
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
