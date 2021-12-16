package nexCell.model.cell;

public class CellFormula extends Cell {

    public static final String PATTERN = "^=[A-Z][0-9]+?[+|\\-|*|/][A-Z][0-9]+?$";

    public CellFormula() {
        System.out.println("SONO FORMULA");
    }

    public CellFormula(int x, int y) {
        super(x, y);
        System.out.println("SONO FORMULA");
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
}
