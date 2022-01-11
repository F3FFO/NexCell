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

package nexCell.view.rowHeader;

import nexCell.view.customElement.MyJTable;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class RowRenderer extends JLabel implements ListCellRenderer<Integer> {

    public RowRenderer(MyJTable SHEETS) {
        JTableHeader header = SHEETS.getTableHeader();
        this.setOpaque(true);
        this.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        this.setHorizontalAlignment(CENTER);
        this.setBackground(header.getBackground());
        this.setForeground(header.getForeground());
        this.setFont(header.getFont());
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Integer> jList, Integer value, int index, boolean selected, boolean focused) {
        this.setText((value == null) ? "" : value.toString());
        return this;
    }
}
