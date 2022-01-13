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

import nexCell.controller.MyDataModel;
import nexCell.controller.SheetStructure;
import nexCell.view.customElement.MyCellEditor;
import nexCell.view.customElement.MyJTable;

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
     * @see nexCell.view.customElement.MyJTable
     */
    private final MyJTable SHEETS;
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
     * @see nexCell.view.customElement.MyJTable
     * @see nexCell.view.customElement.panel.InfoPanel
     */
    public SheetsView(SheetStructure sheetStructure, MyDataModel model, JTextField CELL_SELECTED, JTextField FORMULA) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.sheetStructure = sheetStructure;
        this.SHEETS = new MyJTable(CELL_SELECTED, FORMULA);

        SHEETS.setModel(model);
        SHEETS.changeSelection(0, 0, false, false);
        SHEETS.setDefaultEditor(Object.class, new MyCellEditor(sheetStructure, FORMULA));
        this.add(SHEETS);
    }

    /**
     * Return the {@link SheetsView#SHEETS} object.
     *
     * @return the JTable object
     */
    public MyJTable getSHEETS() {
        return this.SHEETS;
    }

    /**
     * Return the data structure: {@link SheetsView#sheetStructure}.
     *
     * @return the data structure
     */
    public SheetStructure getSheetStructure() {
        return sheetStructure;
    }
}
