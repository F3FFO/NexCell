package nexCell.model.cell;

public class CellFormula extends Cell {

    public static final String PATTERN = "^=[A-Z][0-9]+?[+|-][A-Z][0-9]+?$";

    public CellFormula(int x, int y) {
        super(x, y);
    }
}
