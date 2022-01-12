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

import nexCell.controller.MyDataModel;
import nexCell.view.customElement.MyJTable;

import javax.swing.*;

/**
 * This class is the class the define the row header of the JTable.
 *
 * @author Federico Pierantoni
 */
public class RowHeader extends JList<Integer> {

    /**
     * Construct and initialize the header.
     *
     * @param SHEETS JTable: {@link MyJTable}
     * @see MyJTable
     * @see RowRenderer
     */
    public RowHeader(MyJTable SHEETS) {
        this.setListData(((MyDataModel) SHEETS.getModel()).getRowIdentifiers());
        this.setFixedCellWidth(50);
        this.setFixedCellHeight(SHEETS.getRowHeight());
        this.setCellRenderer(new RowRenderer(SHEETS));
    }
}
