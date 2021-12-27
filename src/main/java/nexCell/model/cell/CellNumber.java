package nexCell.model.cell;

public class CellNumber extends Cell {

    private Number value;

    public CellNumber(int x, int y, Number value) {
        super(x, y);
        this.setValue(value);
    }

    @Override
    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = value;
    }
}
