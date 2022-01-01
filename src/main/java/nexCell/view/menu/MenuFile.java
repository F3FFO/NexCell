package nexCell.view.menu;

import nexCell.controller.io.OpenFile;
import nexCell.controller.io.SaveFile;
import nexCell.view.Gui;

import javax.swing.*;
import java.util.Locale;

public class MenuFile extends JMenu {

    private final int ELEMENT = 4;
    private final JMenuItem[] item;

    public MenuFile(Gui frame) {
        super("File");
        item = new JMenuItem[ELEMENT];
        item[0] = new JMenuItem("Nuovo");
        item[1] = new JMenuItem("Apri");
        item[1].addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Apri");
            fileChooser.setLocale(Locale.getDefault());

            int userSelection = fileChooser.showOpenDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                Runnable runnable = new OpenFile(fileChooser.getSelectedFile(), frame.getSHEETS().getModel());

                Thread thread = new Thread(runnable);
                thread.start();
            }
        });

        item[2] = new JMenuItem("Salva");
        item[2].addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salva");
            fileChooser.setLocale(Locale.getDefault());

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                // TODO check if file name field is not empty
                Runnable runnable = new SaveFile(fileChooser.getSelectedFile(), frame.getSHEETS().getSheetStructure().getMatrix());

                Thread thread = new Thread(runnable);
                thread.start();
            }
        });

        item[3] = new JMenuItem("Esci");
        item[3].addActionListener(actionEvent -> {
            frame.dispose();
        });
        for (JMenuItem jMenuItem : item) this.add(jMenuItem);
    }
}
