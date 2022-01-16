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

import nexCell.controller.DataModel;
import nexCell.controller.SheetStructure;
import nexCell.view.MyCellEditor;
import nexCell.view.MyJTable;

import javax.swing.*;

/**
 * This class is the panel that contain the JTable.
 *
 * @author Federico Pierantoni
 */
public class SheetsView extends JPanel {

    /**
     * Object of the JTable
     *
     * @see MyJTable
     */
    private final MyJTable sheetsTable;
    /**
     * Object of the JTable
     *
     * @see nexCell.controller.SheetStructure
     */
    private final SheetStructure sheetStructure;

    /**
     * Construct the panel and initialize the object.
     *
     * @param sheetStructure Object of the data structure
     * @param model          Object of the data model of the JTable
     * @param CELL_SELECTED  JTextField object that contain cell selected: {@link InfoPanel#CELL_SELECTED}
     * @param FORMULA        JTextField object that contain input user: {@link InfoPanel#FORMULA}
     * @see nexCell.controller.SheetStructure
     * @see MyJTable
     * @see InfoPanel
     */
    public SheetsView(SheetStructure sheetStructure, DataModel model, JTextField CELL_SELECTED, JTextField FORMULA) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.sheetStructure = sheetStructure;
        this.sheetsTable = new MyJTable(CELL_SELECTED, FORMULA);

        sheetsTable.setModel(model);
        sheetsTable.changeSelection(0, 0, false, false);
        sheetsTable.setDefaultEditor(Object.class, new MyCellEditor(sheetStructure, FORMULA));
        this.add(sheetsTable);
    }

    /**
     * Returns the {@link SheetsView#sheetsTable} object.
     *
     * @return the JTable object
     */
    public MyJTable getSheetsTable() {
        return this.sheetsTable;
    }
}
