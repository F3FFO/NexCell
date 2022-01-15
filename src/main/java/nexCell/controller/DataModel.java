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

package nexCell.controller;

import nexCell.cell.Cell;
import nexCell.cell.CellFormula;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 * This class is the data model of the JTable.
 *
 * @author Federico Pierantoni
 */
public class DataModel extends DefaultTableModel {

    /**
     * Vector that contains identifiers of the row
     */
    private final Vector<Integer> rowIdentifiers;
    /**
     * Vector that contains identifiers of the column
     */
    private final Vector<String> columnIdentifiers;
    /**
     * Object of the data structure
     *
     * @see nexCell.controller.SheetStructure
     */
    private final SheetStructure sheetStructure;

    /**
     * Initialize and populate table {@link DataModel#populateTable()}.
     *
     * @param sheetStructure {@link DataModel#sheetStructure}
     */
    public DataModel(SheetStructure sheetStructure) {
        this.rowIdentifiers = new Vector<>();
        this.columnIdentifiers = new Vector<>();
        this.sheetStructure = sheetStructure;
        this.populateTable();
    }

    /**
     * Returns the Vector of the row identifier: {@link DataModel#rowIdentifiers}.
     *
     * @return the Vector of the row identifier
     */
    public Vector<Integer> getRowIdentifiers() {
        return rowIdentifiers;
    }

    /**
     * Returns data structure object.
     * {@link DataModel#sheetStructure}
     *
     * @return data structure object
     */
    public SheetStructure getSheetStructure() {
        return sheetStructure;
    }

    /**
     * Populate JTable with generic cell and fill vector identifiers.
     * {@link DataModel#rowIdentifiers}
     * {@link DataModel#columnIdentifiers}
     */
    private void populateTable() {
        // populate row identifier with number from 0 to 1000
        for (int i = 0; i < SheetStructure.ROW; i++)
            rowIdentifiers.addElement(i + 1);

        // add array of row for each column and populate column identifier
        for (int j = 0; j < SheetStructure.COLUMN; j++) {
            columnIdentifiers.add(Character.toString(('A' + j)));
            super.addColumn('A' + j, new Cell[SheetStructure.ROW]);
        }
        this.setColumnIdentifiers(columnIdentifiers);
    }

    /**
     * Update the table values when a new value is entered.
     */
    @Override
    public void fireTableDataChanged() {
        for (int i = 0; i < sheetStructure.getCellFormula().size(); i++)
            this.setValueAt(sheetStructure.getCellFormula().get(i).getOriginalValue(), sheetStructure.getCellFormula().get(i).getRow(), sheetStructure.getCellFormula().get(i).getColumn(), false);
        super.fireTableDataChanged();
    }

    /**
     * This method check the type of the cell based on the input and cast it into it:
     * <table>
     *   <tr>
     *     <th>Class Name</th>
     *     <th>Data Types</th>
     *     <th>Description</th>
     *   </tr>
     *   <tr>
     *     <td>Cell</td>
     *     <td>Maria Anders</td>
     *     <td>{@link nexCell.cell.Cell}</td>
     *   </tr>
     *   <tr>
     *     <td>CellNumber</td>
     *     <td>Number</td>
     *     <td>{@link nexCell.cell.CellNumber}</td>
     *   </tr>
     *   <tr>
     *     <td>CellString</td>
     *     <td>String</td>
     *     <td>{@link nexCell.cell.CellString}</td>
     *   </tr>
     *   <tr>
     *     <td>CellFormula</td>
     *     <td>Object</td>
     *     <td>{@link nexCell.cell.CellFormula}</td>
     *   </tr>
     * </table>
     * And after that if it's a formula cell, it calculates the value of the operation, sets it and
     * update {@link SheetStructure#cellFormula} otherwise set the value in the JTable and updates
     * all value inside JTable if isFire is true.
     *
     * @param value  take in input from the user
     * @param row    of the value {@link Cell#row}
     * @param column of the value {@link Cell#column}
     * @param isFire whether the table need to be updated
     */
    private void setValueAt(Object value, int row, int column, boolean isFire) {
        // check the type of cell
        int type = sheetStructure.checkTypeCell(value);
        // cast the cell
        sheetStructure.parseCell(row, column, value, type);
        if (type != SheetStructure.CELL_FORMULA) {
            if (!sheetStructure.getCellFormula().isEmpty())
                // if the cell changes from a formula cell to a normal cell, it removes the data from the array cellFormula
                for (int i = 0; i < sheetStructure.getCellFormula().size(); i++) {
                    if (sheetStructure.getCellFormula().get(i).getRow() == row && sheetStructure.getCellFormula().get(i).getColumn() == column)
                        sheetStructure.getCellFormula().remove(i);
                }
            super.setValueAt(value, row, column);
        } else {
            // calc the value
            Object res = sheetStructure.calcFormula(value);
            ((CellFormula) (sheetStructure.getMatrix().get(row).get(column))).setValue(res);
            // insert the first value into the array cellFormula
            if (isFire && sheetStructure.getCellFormula().isEmpty()) {
                sheetStructure.getCellFormula().add(((CellFormula) (sheetStructure.getMatrix().get(row).get(column))));
                // avoid updating table data
                isFire = false;
            } else if (isFire) {
                int size = sheetStructure.getCellFormula().size();
                // populate cellFormula array
                for (int i = 0; i < size; i++) {
                    if (sheetStructure.getCellFormula().get(i).getRow() != row || sheetStructure.getCellFormula().get(i).getColumn() != column)
                        sheetStructure.getCellFormula().add(((CellFormula) (sheetStructure.getMatrix().get(row).get(column))));
                    else
                        sheetStructure.getCellFormula().set(i, ((CellFormula) (sheetStructure.getMatrix().get(row).get(column))));
                }
            }
            if (res instanceof Number)
                super.setValueAt(sheetStructure.getMatrix().get(row).get(column).getValue(), row, column);
            else
                super.setValueAt(CellFormula.ERROR, row, column);
        }
        // update table data
        if (isFire)
            this.fireTableDataChanged();
    }

    /**
     * Check if the value taken in input is null and the value saved in the matrix{@link DataModel#sheetStructure}
     * is different from null, in this case call {@link DataModel#setValueAt(Object, int, int, boolean)}
     * with empty value otherwise call {@link DataModel#setValueAt(Object, int, int, boolean)} with the
     * value taken in input.
     *
     * @param value  take in input from the user
     * @param row    of the value {@link Cell#row}
     * @param column of the value {@link Cell#column}
     */
    @Override
    public void setValueAt(Object value, int row, int column) {
        // check if the value in the JTable is different from the input parameter
        if (value != null)
            this.setValueAt(value, row, column, true);
        else if (sheetStructure.getMatrix().get(row).get(column).getValue() != null)
            this.setValueAt("", row, column, true);
    }
}
