/*
 * Copyright 2022 F3FFO - Federico Pierantoni
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nexCell.view.menu;

import nexCell.controller.MyDataModel;
import nexCell.controller.SheetStructure;
import nexCell.controller.io.OpenFile;
import nexCell.controller.io.SaveFile;
import nexCell.view.Gui;
import nexCell.view.customElement.panel.SheetsView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Locale;

public class MenuFile extends JMenu {

    private JMenuItem newMenuItem = new JMenuItem();
    private JMenuItem openMenuItem = new JMenuItem();
    private JMenuItem saveAsMenuItem = new JMenuItem();
    private JMenuItem exitMenuItem = new JMenuItem();

    public MenuFile(Gui frame, SheetStructure sheetStructure, MyDataModel model, SheetsView SHEETS) {
        super("File");
        //---- newMenuItem ----
        newMenuItem.setText("Nuovo");
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        newMenuItem.addActionListener(new NewActionPerformed(sheetStructure, model, SHEETS));
        this.add(newMenuItem);
        //---- openMenuItem ----
        openMenuItem.setText("Apri...");
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        openMenuItem.addActionListener(new OpenActionPerformed(this, model));
        this.add(openMenuItem);
        //---- saveAsMenuItem ----
        saveAsMenuItem.setText("Salva");
        saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        saveAsMenuItem.addActionListener(new SaveAsActionPerformed(this, sheetStructure));
        this.add(saveAsMenuItem);
        this.addSeparator();
        //---- exitMenuItem ----
        exitMenuItem.setText("Esci");
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        exitMenuItem.addActionListener(new ExitActionPerformed(frame));
        this.add(exitMenuItem);
    }

    private static class NewActionPerformed implements ActionListener {

        private SheetStructure sheetStructure;
        private MyDataModel model;
        private final SheetsView SHEETS;

        public NewActionPerformed(SheetStructure sheetStructure, MyDataModel model, SheetsView SHEETS) {
            this.sheetStructure = sheetStructure;
            this.model = model;
            this.SHEETS = SHEETS;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            this.sheetStructure = new SheetStructure();
            this.model.setRowCount(0);
            this.model = new MyDataModel(sheetStructure);
            this.SHEETS.getSHEETS().setModel(model);
        }
    }

    private static class OpenActionPerformed implements ActionListener {

        private final JMenu menu;
        private final MyDataModel model;

        public OpenActionPerformed(JMenu menu, MyDataModel model) {
            this.menu = menu;
            this.model = model;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Apri");
            fileChooser.setLocale(Locale.getDefault());
            int userSelection = fileChooser.showOpenDialog(this.menu);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                Runnable runnable = new OpenFile(fileChooser.getSelectedFile(), this.model);

                new Thread(runnable).start();
            }
        }
    }

    private static class SaveAsActionPerformed implements ActionListener {

        private final JMenu menu;
        private final SheetStructure sheetStructure;

        public SaveAsActionPerformed(JMenu menu, SheetStructure sheetStructure) {
            this.menu = menu;
            this.sheetStructure = sheetStructure;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salva");
            fileChooser.setLocale(Locale.getDefault());

            int userSelection = fileChooser.showSaveDialog(this.menu);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                System.out.println(fileChooser.getSelectedFile().getName());
                // TODO check if file name field is not empty
                Runnable runnable = new SaveFile(fileChooser.getSelectedFile(), this.sheetStructure.getMatrix());

                new Thread(runnable).start();
            }
        }
    }

    private static class ExitActionPerformed implements ActionListener {

        private final Gui frame;

        public ExitActionPerformed(Gui frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            this.frame.dispose();
        }
    }
}
