package nexCell.controller;

import nexCell.model.cell.Cell;
import nexCell.model.cell.CellFormula;
import nexCell.model.cell.CellNumber;
import nexCell.model.cell.CellString;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SheetStructure {

    public static final int ROW = 1000;
    public static final int COLUMN = 26;
    public static final int CELL = -1;
    public static final int CELL_NUMBER = 1;
    public static final int CELL_STRING = 2;
    public static final int CELL_FORMULA = 3;

    private final List<List<Cell>> matrix;
    private final List<CellFormula> cellFormula;

    public SheetStructure() {
        this.cellFormula = new ArrayList<>(5);
        this.matrix = new ArrayList<>();
        for (int i = 0; i < ROW; i++) {
            List<Cell> inRow = new ArrayList<>();
            for (int j = 0; j < COLUMN; j++)
                inRow.add(new Cell(i, j));

            this.matrix.add(inRow);
        }
    }

    public List<List<Cell>> getMatrix() {
        return matrix;
    }

    public List<CellFormula> getCellFormula() {
        return this.cellFormula;
    }

    public int checkTypeCell(Object value) {
        try {
            NumberFormat.getInstance().parse((String) value);
            return CELL_NUMBER;
        } catch (ParseException | NumberFormatException e) {
            if (!Pattern.matches(CellFormula.PATTERN, (String) value)) {
                return CELL_STRING;
            } else if (value != "") {
                return CELL_FORMULA;
            }
        }
        return CELL;
    }

    public void convertCell(int row, int col, Object value, int type) throws ParseException {
        Cell general;
        if (type == CELL)
            general = new Cell(row, col);
        else if (type == CELL_NUMBER)
            general = new CellNumber(row, col, NumberFormat.getInstance().parse((String) value));
        else if (type == CELL_STRING)
            general = new CellString(row, col, (String) value);
        else
            general = new CellFormula(row, col, (String) value);

        matrix.set(row, matrix.get(row)).set(col, general);
    }

    private int[] extractPos(Object input) {
        int[] res = new int[4];
        Pattern patternLet = Pattern.compile("[A-Z]+");
        Pattern patternNum = Pattern.compile("[1-9]+");

        Matcher matcher = patternLet.matcher((String) input);
        if (matcher.find())
            res[0] = matcher.group(0).charAt(0) - 'A';
        if (matcher.find())
            res[2] = matcher.group(0).charAt(0) - 'A';

        matcher = patternNum.matcher((String) input);
        if (matcher.find())
            res[1] = Integer.parseInt(matcher.group(0));
        if (matcher.find())
            res[3] = Integer.parseInt(matcher.group(0));

        return res;
    }

    public Object calcFormula(Object input) {
        int[] values = extractPos(input);
        Object val1 = matrix.get(values[1] - 1).get(values[0]).getValue();
        Object val2 = matrix.get(values[3] - 1).get(values[2]).getValue();
        try {
            char op = '+';
            if (input.toString().contains("-"))
                op = '-';
            else if (input.toString().contains("*"))
                op = '*';
            else if (input.toString().contains("/"))
                op = '/';
            return new CellFormula().doOp((Number) val1, (Number) val2, op);
        } catch (Exception e) {
            return CellFormula.ERROR;
        }
    }
}
