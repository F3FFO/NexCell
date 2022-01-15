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

import nexCell.view.MyJTable;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * This class define the row renderer of the JTable.
 *
 * @author Federico Pierantoni
 */
public class RowRenderer extends JLabel implements ListCellRenderer<Integer> {

    /**
     * Construct and initialize the header.
     *
     * @param SHEETS JTable: {@link MyJTable}
     * @see MyJTable
     */
    public RowRenderer(MyJTable SHEETS) {
        JTableHeader header = SHEETS.getTableHeader();
        this.setOpaque(true);
        this.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        this.setHorizontalAlignment(CENTER);
        this.setBackground(header.getBackground());
        this.setForeground(header.getForeground());
        this.setFont(header.getFont());
    }

    /**
     * Return a component that has been configured to display the specified value. That component's paint method is then called to "render" the cell. If it is necessary to
     * compute the dimensions of a list because the list cells do not have a fixed size, this method is called to generate a component on which getPreferredSize can be invoked.
     *
     * @param list         The JList we're painting.
     * @param value        The value returned by list.getModel().getElementAt(index).
     * @param index        The cells index.
     * @param isSelected   True if the specified cell was selected.
     * @param cellHasFocus True if the specified cell has the focus.
     * @return A component whose paint() method will render the specified value.
     * @see ListCellRenderer#getListCellRendererComponent(JList, Object, int, boolean, boolean)
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index, boolean isSelected, boolean cellHasFocus) {
        this.setText((value == null) ? "" : value.toString());
        return this;
    }
}
