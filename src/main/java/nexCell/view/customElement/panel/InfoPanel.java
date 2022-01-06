package nexCell.view.customElement.panel;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    private static final JTextField CELLSELECTED = new JTextField("");
    private static final JLabel LABEL1 = new JLabel("|");
    private static final JLabel LABEL2 = new JLabel("=");
    private static final JTextField FORMULA = new JTextField("");

    public InfoPanel() {
        this.setLayout(new MigLayout("fillx"));

        CELLSELECTED.setEditable(false);
        CELLSELECTED.setMinimumSize(new Dimension(150, CELLSELECTED.getPreferredSize().height));

        this.add(CELLSELECTED);
        this.add(LABEL1);
        this.add(LABEL2);
        this.add(FORMULA, "pushx, growx");
    }
}
