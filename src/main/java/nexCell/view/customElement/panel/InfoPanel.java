package nexCell.view.customElement.panel;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    private final JTextField CELL_SELECTED = new JTextField("");
    private static final JLabel LABEL1 = new JLabel("|");
    private static final JLabel LABEL2 = new JLabel("=");
    private final JTextField FORMULA = new JTextField("");

    public InfoPanel() {
        this.setLayout(new MigLayout("fillx"));

        CELL_SELECTED.setEditable(false);
        CELL_SELECTED.setMinimumSize(new Dimension(100, CELL_SELECTED.getPreferredSize().height));

        this.add(CELL_SELECTED);
        this.add(LABEL1);
        this.add(LABEL2);
        this.add(FORMULA, "pushx, growx");
    }

    public JTextField getCELL_SELECTED() {
        return CELL_SELECTED;
    }

    public JTextField getFORMULA() {
        return FORMULA;
    }
}
