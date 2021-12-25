package nexCell.model.cell;

public class CellString extends Cell {

    private String value;

    public CellString(int x, int y, String value) {
        super(x, y);
        this.setValue(value);
    }

    @Override
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = (String) value;
    }
}
