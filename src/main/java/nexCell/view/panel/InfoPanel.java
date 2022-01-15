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

package nexCell.view.panel;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * This class is the top panel.
 *
 * @author Federico Pierantoni
 */
public class InfoPanel extends JPanel {

    /**
     * JTextField which contains coordinates of the cell selected by the user
     */
    private final JTextField CELL_SELECTED = new JTextField("");
    private static final JLabel LABEL1 = new JLabel("|");
    /**
     * JTextField which contains the original values inserted by the user in the cell
     */
    private final JTextField FORMULA = new JTextField("");

    /**
     * Construct the panel and initialize the object.
     */
    public InfoPanel() {
        this.setLayout(new MigLayout("fillx"));

        CELL_SELECTED.setEditable(false);
        CELL_SELECTED.setMinimumSize(new Dimension(100, CELL_SELECTED.getPreferredSize().height));

        this.add(CELL_SELECTED);
        this.add(LABEL1);
        this.add(FORMULA, "pushx, growx");
    }

    /**
     * Return JTextField that contain coordinates of the cell selected by the user.
     *
     * @return JTextField that contain coordinates of the cell selected by the user
     */
    public JTextField getCELL_SELECTED() {
        return CELL_SELECTED;
    }

    /**
     * Returns JTextField which contains the original values inserted by the user in the cell.
     *
     * @return JTextField which contains the original values inserted by the user in the cell
     */
    public JTextField getFORMULA() {
        return FORMULA;
    }
}
