package nexCell.model.cell;

public class CellInt extends Cell {

    private int value;

    public CellInt(int x, int y, int value) {
        super(x, y);
        this.setValue(value);
    }

    @Override
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = (int) value;
    }
}
