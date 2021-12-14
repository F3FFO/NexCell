package nexCell.view.menu;

import javax.swing.*;

public class MenuFile extends JMenu {

    public MenuFile() {
        super("File");
        JMenuItem[] item = new JMenuItem[4];
        item[0] = new JMenuItem("Nuovo");
        item[1] = new JMenuItem("Apri");
        item[2] = new JMenuItem("Salva");
        item[3] = new JMenuItem("Esci");
        item[0].addActionListener(actionEvent -> {
            //TODO something relative to menu item
        });
        for (JMenuItem jMenuItem : item) this.add(jMenuItem);
    }
}
