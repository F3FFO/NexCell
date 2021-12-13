package nexCell.view.customElement;

import nexCell.controller.MyDataModel;
import nexCell.controller.SheetStructure;
import nexCell.view.listener.TableListener;

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
        SHEETS.getModel().addTableModelListener(new TableListener(sheetStructure, SHEETS));
        this.add(SHEETS);
    }

    public MyDataModel getModel() {
        return this.model;
    }

    public MyJTable getSHEETS() {
        return this.SHEETS;
    }
}
