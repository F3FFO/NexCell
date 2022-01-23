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

import nexCell.Main;
import nexCell.controller.DataModel;
import nexCell.controller.SheetStructure;
import nexCell.controller.io.OpenFile;
import nexCell.controller.io.SaveFile;
import nexCell.view.Gui;
import nexCell.view.panel.SheetsView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Locale;

/**
 * This class contains file menu item.
 *
 * @author Federico Pierantoni
 */
public class MenuFile extends JMenu {

    /**
     * Item 'Nuovo'
     */
    private final JMenuItem newMenuItem = new JMenuItem();
    /**
     * Item 'Apri'
     */
    private final JMenuItem openMenuItem = new JMenuItem();
    /**
     * Item 'Salva con nome'
     */
    private final JMenuItem saveAsMenuItem = new JMenuItem();
    /**
     * Item 'Esci'
     */
    private final JMenuItem exitMenuItem = new JMenuItem();

    /**
     * Construct the menu.
     *
     * @param frame  the main frame
     * @param SHEETS the panel which contain the JTable
     * @see Gui
     * @see SheetsView
     */
    public MenuFile(Gui frame, SheetsView SHEETS) {
        super("File");
        //---- newMenuItem ----
        newMenuItem.setText("Nuovo");
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        newMenuItem.addActionListener(new NewActionPerformed(frame, SHEETS));
        this.add(newMenuItem);
        //---- openMenuItem ----
        openMenuItem.setText("Apri...");
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        openMenuItem.addActionListener(new OpenActionPerformed(frame));
        this.add(openMenuItem);
        //---- saveAsMenuItem ----
        saveAsMenuItem.setText("Salva");
        saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        saveAsMenuItem.addActionListener(new SaveAsActionPerformed(frame));
        this.add(saveAsMenuItem);
        this.addSeparator();
        //---- exitMenuItem ----
        exitMenuItem.setText("Esci");
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        exitMenuItem.addActionListener(new ExitActionPerformed(frame));
        this.add(exitMenuItem);
    }

    /**
     * Action listener for 'Nuovo' menu item.
     * {@link MenuFile#newMenuItem}
     *
     * @see java.awt.event.ActionListener
     */
    private static class NewActionPerformed implements ActionListener {

        private final Gui frame;
        private final SheetsView SHEETS;

        public NewActionPerformed(Gui frame, SheetsView SHEETS) {
            this.frame = frame;
            this.SHEETS = SHEETS;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            this.frame.setSheetStructure(new SheetStructure());
            this.frame.setModel(new DataModel(frame.getSheetStructure()));
            this.SHEETS.getSheetsTable().setModel(frame.getModel());
        }
    }

    /**
     * Action listener for 'Apri' menu item.
     * {@link MenuFile#openMenuItem}
     *
     * @see java.awt.event.ActionListener
     */
    private static class OpenActionPerformed implements ActionListener {

        private final Gui frame;

        public OpenActionPerformed(Gui frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Apri");
            fileChooser.setLocale(Locale.getDefault());
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            int userSelection = fileChooser.showOpenDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                Thread thread = new Thread(new OpenFile(fileChooser.getSelectedFile(), frame.getModel()));
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Main.saveFile(fileChooser.getCurrentDirectory(), fileChooser.getSelectedFile().getName(), frame.getSheetStructure().getMatrix());
                }
            }
        }
    }

    /**
     * Action listener for 'Salva con nome' menu item.
     * {@link MenuFile#saveAsMenuItem}
     *
     * @see java.awt.event.ActionListener
     */
    private static class SaveAsActionPerformed implements ActionListener {

        private final Gui frame;

        public SaveAsActionPerformed(Gui frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salva");
            fileChooser.setLocale(Locale.getDefault());
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                if (fileChooser.getSelectedFile().exists()) {
                    String message = "Esiste già un file chiamato \"" + fileChooser.getSelectedFile().getName() + "\". Vuoi sostituirlo?";
                    int result = JOptionPane.showConfirmDialog(null, message, "Salva", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        Thread thread = new Thread(new SaveFile(fileChooser.getSelectedFile(), this.frame.getSheetStructure().getMatrix()));
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            Main.saveFile(fileChooser.getCurrentDirectory(), fileChooser.getSelectedFile().getName(), this.frame.getSheetStructure().getMatrix());
                        }
                    }
                } else {
                    Thread thread = new Thread(new SaveFile(fileChooser.getSelectedFile(), this.frame.getSheetStructure().getMatrix()));
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Main.saveFile(fileChooser.getCurrentDirectory(), fileChooser.getSelectedFile().getName(), this.frame.getSheetStructure().getMatrix());
                    }
                }
            }
        }
    }

    /**
     * Action listener for 'Esci' menu item.
     * {@link MenuFile#exitMenuItem}
     *
     * @see java.awt.event.ActionListener
     */
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
