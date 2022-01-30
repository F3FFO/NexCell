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

package nexCell.view;

import nexCell.cell.CellFormula;
import nexCell.controller.SheetStructure;
import nexCell.view.panel.InfoPanel;

import javax.swing.*;
import java.awt.*;

/**
 * This class is the default editor for table and tree cells.
 *
 * @author Federico Pierantoni
 */
public class MyCellEditor extends DefaultCellEditor {

    /**
     * {@link SheetStructure}
     */
    private final SheetStructure sheetStructure;

    /**
     * Constructs a DefaultCellEditor that uses a text field.
     *
     * @param sheetStructure data structure: {@link SheetStructure}
     * @param FORMULA        {@link InfoPanel#FORMULA}
     * @see SheetStructure
     * @see InfoPanel
     */
    public MyCellEditor(SheetStructure sheetStructure, JTextField FORMULA) {
        super(new TextFieldCell(FORMULA));
        this.sheetStructure = sheetStructure;
    }

    /**
     * Returns the component for editing.
     *
     * @param table      the JTable that is asking the editor to edit; can be null
     * @param value      the value of the cell to be edited; it is up to the specific editor to interpret and draw the value. For example, if value is the string "true", it could be rendered as a string or it could be rendered as a check box that is checked. null is a valid value
     * @param isSelected true if the cell is to be rendered with highlighting
     * @param row        the row of the cell being edited
     * @param column     the column of the cell being edited
     * @return the component for editing
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (sheetStructure.getMatrix().get(row).get(column) instanceof CellFormula) {
            String formula = ((CellFormula) sheetStructure.getMatrix().get(row).get(column)).getOriginalValue();
            return super.getTableCellEditorComponent(table, formula, isSelected, row, column);
        } else
            return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }
}
