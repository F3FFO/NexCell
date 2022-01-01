package nexCell.model.cell;

import java.io.Serializable;

public class CellFormula extends Cell implements Serializable {

    public static final String PATTERN = "^=[A-Z][0-9]+?[+|\\-|*|/][A-Z][0-9]+?$";
    public static final String ERROR = "#VALORE!";
    private Object value;
    private final String originalValue;

    public CellFormula() {
        this.setValue(null);
        this.originalValue = "";
    }

    public CellFormula(int x, int y, String originalValue) {
        super(x, y);
        this.originalValue = originalValue;
    }

    public CellFormula(int x, int y, String originalValue, Object value) {
        this(x, y, originalValue);
        this.setValue(value);
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public double doOp(Number val1, Number val2, char op) {
        if (op == '+') {
            return val1.doubleValue() + val2.doubleValue();
        } else if (op == '-') {
            return val1.doubleValue() - val2.doubleValue();
        } else if (op == '*') {
            return val1.doubleValue() * val2.doubleValue();
        } else {
            return val1.doubleValue() / val2.doubleValue();
        }
    }

    @Override
    public Object getValue() {
        double valTemp = (double) value;
        if ((valTemp % 1) == 0)
            return (int) valTemp;
        else
            return valTemp;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
