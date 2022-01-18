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

package nexCell.view;

import nexCell.Main;
import nexCell.controller.io.OpenFile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Vector;

/**
 * This class is the dialog showed at startup.
 *
 * @author Federico Pierantoni
 */
public class DialogStartUp extends JDialog {

    /**
     * Construct the JDialog with a JList which show all unsaved file.
     *
     * @param frame     the main frame
     * @param pathFiles list of the paths where the files are saved
     * @param nameFiles Vector of saved file names
     * @param dateFiles Vector of dates of saved files
     * @see Gui
     */
    public DialogStartUp(Gui frame, List<File> pathFiles, Vector<String> nameFiles, Vector<String> dateFiles) {
        super(new JFrame());
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(600, 500));

        // initialize JTextArea for the description
        JTextArea intro = new JTextArea("NexCell tenterà di ripristinare lo stato dei file su cui stavi lavorando prima dell'errore." +
                " Fai clicl su 'Avvio' per iniziare il processo oppure su 'Scarta' per annullare il ripristino.");
        intro.setLineWrap(true);
        this.add(intro, BorderLayout.NORTH);

        // model of the JList
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Nome", nameFiles);
        tableModel.addColumn("Ultima modifica", dateFiles);

        // add the JList to the panel
        JTable jTable = new JTable(tableModel);
        jTable.setShowGrid(true);
        jTable.setGridColor(Color.LIGHT_GRAY);
        this.getContentPane().add(jTable, BorderLayout.CENTER);

        // panel with the action button
        JPanel south = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JButton newFile = new JButton("Scarta");
        newFile.addActionListener(actionEvent -> {
            try {
                Files.deleteIfExists(Paths.get(Main.PREFERENCES_FILE.toURI()));
                Main.saveFile(new File(System.getProperty("java.io.tmpdir")), ".unsaved-nexcell.tmp", frame.getSheetStructure().getMatrix());
            } catch (IOException e) {
                e.printStackTrace();
            }
            dispose();
        });
        south.add(newFile);

        JButton oldFile = new JButton("Avvio");
        oldFile.addActionListener(actionEvent -> {
            int index = jTable.getSelectedRow();
            if (index != -1) {
                File temp = new File(pathFiles.get(index), nameFiles.get(index));
                Thread thread = new Thread(new OpenFile(temp, frame.getModel()));
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Files.deleteIfExists(Paths.get(Main.PREFERENCES_FILE.toURI()));
                    Main.saveFile(pathFiles.get(index), nameFiles.get(index), frame.getSheetStructure().getMatrix());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.dispose();
            }
        });
        south.add(oldFile);
        this.getContentPane().add(south, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
