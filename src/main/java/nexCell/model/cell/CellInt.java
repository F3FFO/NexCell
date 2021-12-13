package nexCell.model.cell;

public class CellInt extends Cell {

    private int value;

    public CellInt(int x, int y, int value) {
        super(x, y);
        this.setValue(value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
