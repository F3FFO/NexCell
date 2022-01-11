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

public class SheetsView extends JPanel {

    private final MyJTable SHEETS;
    private final SheetStructure sheetStructure;
    private final MyDataModel model;

    public SheetsView(SheetStructure sheetStructure, MyDataModel model, JTextField CELL_SELECTED, JTextField FORMULA) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.sheetStructure = sheetStructure;
        this.model = model;
        this.SHEETS = new MyJTable(CELL_SELECTED, FORMULA);

        SHEETS.setModel(this.model);
        SHEETS.changeSelection(0, 0, false, false);
        SHEETS.setDefaultEditor(Object.class, new MyCellEditor(sheetStructure, FORMULA));
        this.add(SHEETS);
    }

    public MyDataModel getModel() {
        return this.model;
    }

    public MyJTable getSHEETS() {
        return this.SHEETS;
    }

    public SheetStructure getSheetStructure() {
        return sheetStructure;
    }
}
