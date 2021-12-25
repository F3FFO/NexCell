package nexCell.model.cell;

public class Cell {

    private int row;
    private int column;

    public Cell() {
        this.row = 0;
        this.column = 0;
    }

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Object getValue() {
        return null;
    }
}
