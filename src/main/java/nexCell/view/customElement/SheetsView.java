package nexCell.view.customElement;

import nexCell.controller.MyDataModel;
import nexCell.controller.SheetStructure;

import javax.swing.*;

public class SheetsView extends JPanel {

    private final SheetStructure sheetStructure;
    private final MyDataModel model;

    private final MyJTable SHEETS = new MyJTable();

    public SheetsView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.sheetStructure = new SheetStructure();
        model = new MyDataModel(this.sheetStructure.getROW(), this.sheetStructure.getCOLUMN(), this.sheetStructure.getMatrice());

        SHEETS.setModel(this.model);
        this.add(SHEETS);
    }

    public MyJTable getSHEETS() {
        return this.SHEETS;
    }
}
