package nexCell.controller;

import nexCell.model.cell.Cell;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public class MyDataModel extends DefaultTableModel {

    private int rowCount;
    private int columnCount;
    private final Vector<Integer> rowIdentifiers;
    private final Vector<String> colIdentifiers;
    private final List<List<Cell>> data;

    public MyDataModel(int row, int col, List<List<Cell>> data) {
        this.setRowCount(row);
        this.setColumnCount(col);
        this.rowIdentifiers = new Vector<>();
        this.colIdentifiers = new Vector<>();
        this.data = data;
        this.populateTable();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public Vector<Integer> getRowIdentifiers() {
        return rowIdentifiers;
    }

    private void populateTable() {
        updateTablePopulation(0, 0);
    }

    protected void updateTablePopulation(int i, int j) {
        columnIdentifiers(j);
        for (; i < this.rowCount; i++) {
            this.rowIdentifiers.addElement(i + 1);
            for (; j < this.columnCount; j++)
                addColumn(j);
        }
    }

    public void addColumn(int column) {
        super.addColumn(this.colIdentifiers.get(column), this.data.get(column).toArray());
    }

    private void columnIdentifiers(int startColumn) {
        for (int i = startColumn; i < this.columnCount; i++) {
            int letCode = i + 65;
            char unicode = (char) (letCode);
            this.colIdentifiers.add(Character.toString((unicode)));
        }
    }
}

