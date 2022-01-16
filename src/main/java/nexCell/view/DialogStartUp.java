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
import nexCell.controller.io.Autosave;
import nexCell.controller.io.OpenFile;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the dialog showed at startup.
 *
 * @author Federico Pierantoni
 */
public class DialogStartUp extends JDialog {

    /**
     * Construct the JDialog with a JList which show all unsaved file.
     *
     * @param frame the main frame
     * @see Gui
     */
    public DialogStartUp(Gui frame) {
        super(new JFrame());
        this.setLayout(new BorderLayout());

        // list of paths
        List<File> files = new ArrayList<>(3);
        // model of the JList
        DefaultListModel<String> listModel = new DefaultListModel<>();
        // populate the model and the List
        try {
            String line;
            BufferedReader in = new BufferedReader(new FileReader(Main.PREFERENCES_FILE));
            while ((line = in.readLine()) != null) {
                files.add(new File(line.substring(0, line.indexOf(':'))));
                listModel.addElement(line.substring(line.indexOf(':') + 1));
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // add the JList to the panel
        JList<String> jList = new JList<>(listModel);
        this.getContentPane().add(jList, BorderLayout.CENTER);

        // panel with the action button
        JPanel south = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JButton newFile = new JButton("Scarta");
        newFile.addActionListener(actionEvent -> {
            try {
                Files.deleteIfExists(Paths.get(Main.PREFERENCES_FILE.toURI()));
                Files.createFile(Paths.get(Main.PREFERENCES_FILE.toURI()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            dispose();
        });
        south.add(newFile);

        JButton oldFile = new JButton("Ok");
        oldFile.addActionListener(actionEvent -> {
            int index = jList.getSelectedIndex();
            if (index != -1) {
                File temp = new File(files.get(index), listModel.get(index));
                Thread thread = new Thread(new OpenFile(temp, frame.getModel()));
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    String name = "." + listModel.get(index) + ".tmp";
                    try {
                        Files.deleteIfExists(Paths.get(Main.PREFERENCES_FILE.toURI()));
                        new Autosave(frame.saveTemp(files.get(index), name));
                        Files.createFile(Paths.get(Main.PREFERENCES_FILE.toURI()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dispose();
                }
            }
        });
        south.add(oldFile);
        this.getContentPane().add(south, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
