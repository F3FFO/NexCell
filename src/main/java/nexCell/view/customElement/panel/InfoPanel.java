package nexCell.view.customElement.panel;

import net.miginfocom.swing.MigLayout;
import nexCell.controller.MyDataModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InfoPanel extends JPanel {

    private final JTextField CELL_SELECTED = new JTextField("");
    private static final JLabel LABEL1 = new JLabel("|");
    private static final JLabel LABEL2 = new JLabel("=");
    private final JTextField FORMULA = new JTextField("");

    public InfoPanel(MyDataModel model) {
        this.setLayout(new MigLayout("fillx"));

        CELL_SELECTED.setEditable(false);
        CELL_SELECTED.setMinimumSize(new Dimension(100, CELL_SELECTED.getPreferredSize().height));

        FORMULA.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                System.out.println("ENTER pressed");
                //model.setValueAt(FORMULA.getText(), );
            }
        });

        FORMULA.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("ENTER pressed");
                }
            }
        });

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
