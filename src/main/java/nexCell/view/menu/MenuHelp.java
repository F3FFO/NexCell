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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * This class contains 'help' menu item.
 *
 * @author Federico Pierantoni
 */
public class MenuHelp extends JMenu {

    /**
     * Item 'Informazioni sulla licenza'
     */
    private final JMenuItem licenseItem = new JMenuItem();

    /**
     * Construct the menu.
     */
    public MenuHelp() {
        super("Aiuto");
        //---- newLicenseItem ----
        licenseItem.setText("Informazioni sulla licenza");
        licenseItem.addActionListener(new LicenseActionPerformed());
        this.add(licenseItem);
    }

    /**
     * Action listener for 'Informazioni sulla licenza' menu item.
     * {@link MenuHelp#licenseItem}
     *
     * @see java.awt.event.ActionListener
     */
    private class LicenseActionPerformed implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JDialog dialog = new JDialog(new JFrame());
            dialog.setLayout(new BorderLayout());
            dialog.setLocationRelativeTo(null);
            dialog.setResizable(false);
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("nexCell/license.txt")).getFile());
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
                String line = "";
                JTextArea text = new JTextArea();
                try {
                    while ((line = br.readLine()) != null)
                        text.append(line + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                text.setEditable(false);
                dialog.getContentPane().add(new JScrollPane(text), BorderLayout.CENTER);
            } catch (IOException e) {
                e.printStackTrace();
            }
            JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton close = new JButton("Chiudi");
            close.addActionListener(actionEvent1 -> dialog.dispose());
            south.add(close);
            dialog.getContentPane().add(south, BorderLayout.SOUTH);
            dialog.pack();
            dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        }
    }
}
