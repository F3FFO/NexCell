package nexCell.controller;

import nexCell.model.cell.Cell;
import nexCell.model.cell.CellFormula;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class MyDataModel extends DefaultTableModel {

    private int rowCount;
    private int columnCount;
    private final Vector<Integer> rowIdentifiers;
    private final Vector<String> colIdentifiers;
    private SheetStructure sheetStructure;

    public MyDataModel(int row, int col, SheetStructure sheetStructure) {
        super.setRowCount(row);
        super.setColumnCount(col);
        this.rowCount = super.getRowCount();
        this.columnCount = super.getColumnCount(); //TODO check why I need to do this
        this.rowIdentifiers = new Vector<>();
        this.colIdentifiers = new Vector<>();
        this.sheetStructure = sheetStructure;
        this.populateTable();
    }

    public Vector<Integer> getRowIdentifiers() {
        return rowIdentifiers;
    }

    @Override
    public void fireTableDataChanged() {
        for (int i = 0; i < sheetStructure.getCellFormula().size(); i++)
            this.setValueAt(sheetStructure.getCellFormula().get(i).getOriginalValue(), sheetStructure.getCellFormula().get(i).getRow(), sheetStructure.getCellFormula().get(i).getColumn(), false);
        super.fireTableDataChanged();
    }

    public void setValueAt(Object value, int row, int column, boolean isFire) {
        int type = sheetStructure.checkTypeCell(value);
        try {
            sheetStructure.convertCell(row, column, value, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (type != 4)
            super.setValueAt(value, row, column);
        else {
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
            if (res instanceof Double)
                super.setValueAt(sheetStructure.getMatrix().get(row).get(column).getValue(), row, column);
            else
                super.setValueAt(CellFormula.ERROR, row, column);
        }
        if (isFire)
            this.fireTableDataChanged();
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        this.setValueAt(value, row, column, true);
    }

    private void populateTable() {
        columnIdentifiers();
        for (int i = 0; i < rowCount; i++)
            rowIdentifiers.addElement(i + 1);

        for (int j = 0; j < columnCount; j++)
            super.addColumn(colIdentifiers.get(j), new Cell[rowCount]);
    }

    private void columnIdentifiers() {
        for (int i = 0; i < columnCount; i++) {
            int letCode = i + 'A';
            char unicode = (char) (letCode);
            colIdentifiers.add(Character.toString((unicode)));
        }
    }
}
