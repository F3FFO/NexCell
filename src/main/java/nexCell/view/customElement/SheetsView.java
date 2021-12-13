package nexCell.view.customElement;

import nexCell.controller.MyDataModel;

import javax.swing.*;

public class SheetsView extends JPanel {

    private final MyDataModel model;

    private final JTable SHEETS = new JTable();

    public SheetsView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        model = new MyDataModel();

        SHEETS.setModel(this.model);
        this.add(SHEETS);
    }
}
