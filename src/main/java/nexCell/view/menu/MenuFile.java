package nexCell.view.menu;

import nexCell.controller.io.OpenFile;
import nexCell.controller.io.SaveFile;
import nexCell.view.Gui;

import javax.swing.*;
import java.util.Locale;

public class MenuFile extends JMenu {

    private final int ELEMENT = 5;
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

                new Thread(runnable).start();
            }
        });

        item[2] = new JMenuItem("Salva");
        item[2].addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salva");
            fileChooser.setLocale(Locale.getDefault());

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                System.out.println(fileChooser.getSelectedFile().getName());
                // TODO check if file name field is not empty
                Runnable runnable = new SaveFile(fileChooser.getSelectedFile(), frame.getSHEETS().getSheetStructure().getMatrix());

                new Thread(runnable).start();
            }
        });
        item[3] = new JMenuItem("Salva con nome...");
        item[3].addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salva con nome...");
            fileChooser.setLocale(Locale.getDefault());

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                Runnable runnable = new SaveFile(fileChooser.getSelectedFile(), frame.getSHEETS().getSheetStructure().getMatrix());

                new Thread(runnable).start();
            }
        });
        item[4] = new JMenuItem("Esci");
        item[4].addActionListener(actionEvent -> {
            frame.dispose();
        });
        for (JMenuItem jMenuItem : item) this.add(jMenuItem);
    }
}
