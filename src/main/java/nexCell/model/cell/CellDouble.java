package nexCell.model.cell;

public class CellDouble extends Cell {

    private double value;

    public CellDouble(int x, int y, double value) {
        super(x, y);
        this.setValue(value);
    }

    @Override
    public Object getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
