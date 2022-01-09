package nexCell.view.menu;

import nexCell.controller.MyDataModel;
import nexCell.controller.SheetStructure;
import nexCell.controller.io.OpenFile;
import nexCell.controller.io.SaveFile;
import nexCell.view.Gui;
import nexCell.view.customElement.panel.SheetsView;

import javax.swing.*;
import java.util.Locale;

public class MenuFile extends JMenu {

    private SheetStructure sheetStructure;
    private MyDataModel model;

    public MenuFile(Gui frame, SheetStructure sheetStructure, MyDataModel model, SheetsView SHEETS) {
        super("File");
        this.sheetStructure = sheetStructure;
        this.model = model;
        int ELEMENT = 5;
        JMenuItem[] item = new JMenuItem[ELEMENT];
        item[0] = new JMenuItem("Nuovo");
        item[0].addActionListener(actionEvent -> {
            this.sheetStructure = new SheetStructure();
            this.model.setRowCount(0);
            this.model = new MyDataModel(this.sheetStructure);
            SHEETS.getSHEETS().setModel(this.model);
        });

        item[1] = new JMenuItem("Apri");
        item[1].addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Apri");
            fileChooser.setLocale(Locale.getDefault());

            int userSelection = fileChooser.showOpenDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                Runnable runnable = new OpenFile(fileChooser.getSelectedFile(), this.model);

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
                Runnable runnable = new SaveFile(fileChooser.getSelectedFile(), this.sheetStructure.getMatrix());

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
                Runnable runnable = new SaveFile(fileChooser.getSelectedFile(), this.sheetStructure.getMatrix());

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
