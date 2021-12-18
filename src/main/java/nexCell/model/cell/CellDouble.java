package nexCell.model.cell;

public class CellDouble extends Cell {

    private double value;

    public CellDouble(int x, int y, double value) {
        super(x, y);
        this.setValue(value);
        System.out.println("SONO DOUBLE");
    }

    @Override
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = (double) value;
    }
}
