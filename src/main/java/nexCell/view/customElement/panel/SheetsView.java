package nexCell.view.customElement.panel;

import nexCell.controller.MyDataModel;
import nexCell.controller.SheetStructure;
import nexCell.view.customElement.MyJTable;

import javax.swing.*;

public class SheetsView extends JPanel {

    private final SheetStructure sheetStructure;
    private final MyDataModel model;

    private final MyJTable SHEETS = new MyJTable();

    public SheetsView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        sheetStructure = new SheetStructure();
        model = new MyDataModel(sheetStructure.getROW(), sheetStructure.getCOLUMN(), sheetStructure);

        SHEETS.setModel(this.model);
        SHEETS.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        this.add(SHEETS);
    }

    public MyDataModel getModel() {
        return this.model;
    }

    public MyJTable getSHEETS() {
        return this.SHEETS;
    }
}
