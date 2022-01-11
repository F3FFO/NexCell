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

package nexCell.view.customElement.panel;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    private final JTextField CELL_SELECTED = new JTextField("");
    private static final JLabel LABEL1 = new JLabel("|");
    private final JTextField FORMULA = new JTextField("");

    public InfoPanel() {
        this.setLayout(new MigLayout("fillx"));

        CELL_SELECTED.setEditable(false);
        CELL_SELECTED.setMinimumSize(new Dimension(100, CELL_SELECTED.getPreferredSize().height));

        this.add(CELL_SELECTED);
        this.add(LABEL1);
        this.add(FORMULA, "pushx, growx");
    }

    public JTextField getCELL_SELECTED() {
        return CELL_SELECTED;
    }

    public JTextField getFORMULA() {
        return FORMULA;
    }
}
