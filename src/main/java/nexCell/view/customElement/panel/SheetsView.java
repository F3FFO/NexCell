package nexCell.view.customElement.panel;

import nexCell.controller.MyDataModel;
import nexCell.controller.SheetStructure;
import nexCell.view.customElement.MyCellEditor;
import nexCell.view.customElement.MyJTable;

import javax.swing.*;

public class SheetsView extends JPanel {

    private SheetStructure sheetStructure;
    private MyDataModel model;

    private final MyJTable SHEETS = new MyJTable();

    public SheetsView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        sheetStructure = new SheetStructure();
        model = new MyDataModel(sheetStructure.getROW(), sheetStructure.getCOLUMN(), sheetStructure);

        SHEETS.setModel(this.model);
        SHEETS.setDefaultEditor(Object.class, MyCellEditor.make(sheetStructure));
        this.add(SHEETS);
    }

    public MyDataModel getModel() {
        return this.model;
    }

    public MyJTable getSHEETS() {
        return this.SHEETS;
    }
}
