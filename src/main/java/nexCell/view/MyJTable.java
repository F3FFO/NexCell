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
import nexCell.controller.DataModel;
import nexCell.view.panel.InfoPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * This class is redefine the JTable.
 *
 * @author Federico Pierantoni
 */
public class MyJTable extends JTable {

    /**
     * JTextField representing the selected cell
     */
    private final JTextField CELLSELECTED;
    /**
     * JTextField representing the value of the cell
     */
    private final JTextField FORMULA;

    /**
     * Constructs a default JTable that is initialized with a default data model, a default column model, and a default selection model.
     *
     * @param CELLSELECTED {@link InfoPanel#CELL_SELECTED}
     * @param FORMULA      {@link InfoPanel#FORMULA}
     * @see InfoPanel
     */
    public MyJTable(JTextField CELLSELECTED, JTextField FORMULA) {
        super.setCellSelectionEnabled(true);
        this.setShowGrid(true);
        this.setGridColor(Color.LIGHT_GRAY);
        this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.getTableHeader().setReorderingAllowed(Boolean.FALSE);
        this.CELLSELECTED = CELLSELECTED;
        this.FORMULA = FORMULA;
    }

    /**
     * Rethink logic behind selection of cells.
     *
     * @param renderer the TableCellRenderer to prepare
     * @param row      the row of the cell to render, where 0 is the first row
     * @param column   the column of the cell to render, where 0 is the first column
     * @return the Component under the event location
     * @see JTable#prepareRenderer(TableCellRenderer, int, int)
     */
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        boolean isSelected = false;
        boolean hasFocus = false;

        // Only indicate the selection and focused cell if not printing
        if (!isPaintingForPrint()) {
            isSelected = super.isCellSelected(row, column);
            boolean rowIsLead = (selectionModel.getLeadSelectionIndex() == row);
            boolean colIsLead = (columnModel.getSelectionModel().getLeadSelectionIndex() == column);
            hasFocus = (rowIsLead && colIsLead) && super.isFocusOwner();
        }

        Object value = super.getValueAt(row, column);
        JComponent cellRenderer = (JComponent) renderer.getTableCellRendererComponent(this, value, isSelected, hasFocus, row, column);
        Border border = new CompoundBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK), new EmptyBorder(0, 0, 0, 0));

        if (isSelected) {
            cellRenderer.setBackground(new Color(87, 87, 87, 100));
            this.CELLSELECTED.setText(this.getColumnName(column) + "" + (this.getSelectedRow() + 1));
        } else
            cellRenderer.setBackground(null);

        if (isSelected && hasFocus) {
            cellRenderer.setBackground(null);
            cellRenderer.setForeground(Color.BLACK);
            cellRenderer.setBorder(border);
            if (value == null)
                FORMULA.setText("");
            else {
                if (((DataModel) this.getModel()).getSheetStructure().getMatrix().get(row).get(column) instanceof CellFormula)
                    FORMULA.setText(((CellFormula) ((DataModel) this.getModel()).getSheetStructure().getMatrix().get(row).get(column)).getOriginalValue());
                else
                    FORMULA.setText(value.toString());
            }
        }
        return cellRenderer;
    }
}
