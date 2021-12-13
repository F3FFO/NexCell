package nexCell.model.cell;

public class CellString extends Cell {

    private String value;

    public CellString(int x, int y, String value) {
        super(x, y);
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
