package nexCell.model.cell;

import java.io.Serializable;

public class CellNumber extends Cell implements Serializable {

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
