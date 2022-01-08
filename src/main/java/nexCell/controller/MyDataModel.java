package nexCell.controller;

import nexCell.model.cell.Cell;
import nexCell.model.cell.CellFormula;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class MyDataModel extends DefaultTableModel {

    private final Vector<Integer> rowIdentifiers;
    private final SheetStructure sheetStructure;
    private final Vector<String> colIdentifiers;

    public MyDataModel(SheetStructure sheetStructure) {
        this.rowIdentifiers = new Vector<>();
        this.colIdentifiers = new Vector<>();
        this.sheetStructure = sheetStructure;
        this.populateTable();
    }

    public Vector<Integer> getRowIdentifiers() {
        return rowIdentifiers;
    }

    public SheetStructure getSheetStructure() {
        return sheetStructure;
    }

    private void populateTable() {
        for (int i = 0; i < SheetStructure.ROW; i++)
            rowIdentifiers.addElement(i + 1);

        for (int j = 0; j < SheetStructure.COLUMN; j++) {
            colIdentifiers.add(Character.toString(('A' + j)));
            super.addColumn('A' + j, new Cell[SheetStructure.ROW]);
        }
        this.setColumnIdentifiers(colIdentifiers);
    }

    @Override
    public void fireTableDataChanged() {
        for (int i = 0; i < sheetStructure.getCellFormula().size(); i++)
            this.setValueAt(sheetStructure.getCellFormula().get(i).getOriginalValue(), sheetStructure.getCellFormula().get(i).getRow(), sheetStructure.getCellFormula().get(i).getColumn(), false);
        super.fireTableDataChanged();
    }

    private void setValueAt(Object value, int row, int column, boolean isFire) {
        int type = sheetStructure.checkTypeCell(value);
        sheetStructure.convertCell(row, column, value, type);
        if (type != SheetStructure.CELL_FORMULA) {
            if (!sheetStructure.getCellFormula().isEmpty())
                for (int i = 0; i < sheetStructure.getCellFormula().size(); i++) {
                    if (sheetStructure.getCellFormula().get(i).getRow() == row && sheetStructure.getCellFormula().get(i).getColumn() == column)
                        sheetStructure.getCellFormula().remove(i);
                }
            super.setValueAt(value, row, column);
        } else {
            Object res = sheetStructure.calcFormula(value);
            ((CellFormula) (sheetStructure.getMatrix().get(row).get(column))).setValue(res);
            if (isFire && sheetStructure.getCellFormula().isEmpty()) {
                sheetStructure.getCellFormula().add(((CellFormula) (sheetStructure.getMatrix().get(row).get(column))));
            } else if (isFire) {
                for (int i = 0; i < sheetStructure.getCellFormula().size(); i++) {
                    if (sheetStructure.getCellFormula().get(i).getRow() != row && sheetStructure.getCellFormula().get(i).getColumn() != column)
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
        if (isFire)
            this.fireTableDataChanged();
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        if (sheetStructure.getMatrix().get(row).get(column) instanceof CellFormula) {
            if (!((CellFormula) sheetStructure.getMatrix().get(row).get(column)).getOriginalValue().equals(value.toString()))
                this.setValueAt(value, row, column, true);
        } else {
            if (value != sheetStructure.getMatrix().get(row).get(column).getValue())
                this.setValueAt(value, row, column, true);
        }
    }
}
