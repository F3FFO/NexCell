package nexCell.view.customElement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SheetsView extends JPanel {

    private final DefaultTableModel model;

    private final JTable SHEETS = new JTable();

    public SheetsView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        model = new DefaultTableModel();

        SHEETS.setModel(this.model);
        this.add(SHEETS);
    }
}
