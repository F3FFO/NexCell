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
    public void setValueAt(Object value, int row, int column) {
        int type = sheetStructure.checkTypeCell(value);
        try {
            sheetStructure.convertCell(row, column, value, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (type != 4)
            super.setValueAt(value, row, column);
        else {
            double res = sheetStructure.calcFormula(value);
            sheetStructure.getCells().add(row * column, new CellFormula(row, column, (String) value, res));
            sheetStructure.getMatrice().get(row).get(column).setValue(res);
            super.setValueAt(sheetStructure.getMatrice().get(row).get(column).getValue(), row, column);
        }
        super.fireTableDataChanged();
    }

    private void populateTable() {
        updateTablePopulation(0, 0);
    }

    protected void updateTablePopulation(int i, int j) {
        columnIdentifiers(j);
        for (; i < rowCount; i++) {
            rowIdentifiers.addElement(i + 1);
            for (; j < columnCount; j++)
                addColumn(j);
        }
    }

    public void addColumn(int column) {
        super.addColumn(colIdentifiers.get(column), new Cell[rowCount]);
    }

    private void columnIdentifiers(int startColumn) {
        for (int i = startColumn; i < columnCount; i++) {
            int letCode = i + 'A';
            char unicode = (char) (letCode);
            colIdentifiers.add(Character.toString((unicode)));
        }
    }
}
