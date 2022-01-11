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

package nexCell.view.customElement;

import nexCell.controller.SheetStructure;
import nexCell.cell.CellFormula;

import javax.swing.*;
import java.awt.*;

public class MyCellEditor extends DefaultCellEditor {

    private final SheetStructure sheetStructure;

    public MyCellEditor(SheetStructure sheetStructure, JTextField FORMULA) {
        super(new TextFieldCell(FORMULA));
        this.sheetStructure = sheetStructure;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (sheetStructure.getMatrix().get(row).get(column) instanceof CellFormula) {
            String formula = ((CellFormula) sheetStructure.getMatrix().get(row).get(column)).getOriginalValue();
            return super.getTableCellEditorComponent(table, formula, isSelected, row, column);
        } else
            return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }
}
