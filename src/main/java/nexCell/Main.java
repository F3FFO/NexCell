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

package nexCell;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.util.SystemInfo;
import nexCell.cell.Cell;
import nexCell.controller.io.AutoSave;
import nexCell.controller.io.SaveFile;
import nexCell.view.DialogStartUp;
import nexCell.view.Gui;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * This class is the main class.
 *
 * @author Federico Pierantoni
 */
public class Main {

    /**
     * Path representing the current directory
     */
    private static final File HERE = new File("");
    /**
     * File of preference
     */
    public static final File PREFERENCES_FILE = new File(HERE.getAbsolutePath(), "preference.json");
    /**
     * Object of {@link AutoSave}
     *
     * @see AutoSave
     */
    private static AutoSave toSave;
    /**
     * File representing last file saved
     */
    private static File lastSaved;

    public static void main(String[] args) {
        if (SystemInfo.isMacOS) {
            // enable screen menu bar
            // (moves menu bar from JFrame window to top of screen)
            System.setProperty("apple.laf.useScreenMenuBar", "true");

            // application name used in screen menu bar
            // (in first menu after the "apple" menu)
            System.setProperty("apple.awt.application.name", "NexCell");

            // appearance of window title bars
            // possible values:
            //   - "system": use current macOS appearance (light or dark)
            //   - "NSAppearanceNameAqua": use light appearance
            //   - "NSAppearanceNameDarkAqua": use dark appearance
            System.setProperty("apple.awt.application.appearance", "NSAppearanceNameAqua");
        }

        SwingUtilities.invokeLater(() -> {
            // set look and feel
            FlatLaf.registerCustomDefaultsSource("nexCell");
            FlatLightLaf.setup();

            // create frame
            Gui frame = new Gui();

            // create dialog on start
            if (Files.exists(Paths.get(Main.PREFERENCES_FILE.toURI()))) {
                // list of paths
                List<File> pathFiles = new ArrayList<>(2);
                Vector<String> nameFiles = new Vector<>(2);
                Vector<String> dateFiles = new Vector<>(2);

                // populate the model and the List
                try {
                    String line;
                    BufferedReader in = new BufferedReader(new FileReader(Main.PREFERENCES_FILE));
                    while ((line = in.readLine()) != null) {
                        String[] splitted = line.split(";");
                        if (Files.exists(Paths.get(new File(splitted[0], splitted[1]).toURI()))) {
                            pathFiles.add(new File(splitted[0]));
                            nameFiles.add(splitted[1]);
                            dateFiles.add(splitted[2]);
                        }
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (!pathFiles.isEmpty() && !nameFiles.isEmpty()) {
                        DialogStartUp dialogStartUp = new DialogStartUp(frame, pathFiles, nameFiles, dateFiles);
                        // init dialog startup
                        dialogStartUp.setModal(true);
                        dialogStartUp.pack();
                        dialogStartUp.setLocationRelativeTo(null);
                        dialogStartUp.setResizable(false);
                        dialogStartUp.setVisible(true);
                    }
                }
            } else {
                try {
                    Files.createFile(Paths.get(PREFERENCES_FILE.toURI()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Main.saveFile(new File(System.getProperty("java.io.tmpdir")), ".unsaved-nexcell.tmp", frame.getSheetStructure().getMatrix());
            }
            // show frame
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    /**
     * Starts the thread for saving the file and stores the file data.
     *
     * @param path   where to save file
     * @param name   of the file to save
     * @param matrix data structure
     * @see SaveFile
     */
    public static void saveFile(File path, String name, List<List<Cell>> matrix) {
        File newFile = new File(path.getPath(), name);
        if (lastSaved != null && !lastSaved.getAbsolutePath().equals(newFile.getAbsolutePath())) {
            toSave.kill();
        }
        Runnable runnable = new SaveFile(newFile, matrix);
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            if (!Files.exists(Paths.get(Main.PREFERENCES_FILE.toURI())))
                Files.createFile(Paths.get(Main.PREFERENCES_FILE.toURI()));
            // calculate date and hour of unsaved file
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
            String date = dateFormat.format(Files.getLastModifiedTime(Paths.get(path.toURI())).toMillis());
            // write on file the data of unsaved file
            Files.write(Paths.get(Main.PREFERENCES_FILE.toURI()), (path.getPath() + ";" + name + ";" + date + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return runnable;
        toSave = new AutoSave(runnable);
        lastSaved = newFile;
    }
}
