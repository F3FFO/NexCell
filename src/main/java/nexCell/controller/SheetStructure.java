package nexCell.controller;

import nexCell.model.cell.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SheetStructure {

    private int ROW = 1000;
    private int COLUMN = 26;
    private List<List<Cell>> matrix;
    private List<CellFormula> cellFormula;

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

    public int getROW() {
        return ROW;
    }

    public int getCOLUMN() {
        return COLUMN;
    }

    public List<List<Cell>> getMatrix() {
        return matrix;
    }

    public List<CellFormula> getCellFormula() {
        return cellFormula;
    }

    public int checkTypeCell(Object value) {
        String castedVal = (String) value;
        try {
            Integer.parseInt(castedVal);
            return 1;
        } catch (NumberFormatException e) {
            try {
                Double.parseDouble(castedVal);
                return 2;
            } catch (NumberFormatException e2) {
                if (!Pattern.matches(CellFormula.PATTERN, castedVal)) {
                    return 3;
                } else if (value != "") {
                    return 4;
                }
            }
        }
        return -1;
    }

    public void convertCell(int row, int col, Object value, int type) {
        Cell general;
        if (type == -1)
            general = new Cell(row, col);
        else if (type == 1)
            general = new CellInt(row, col, Integer.parseInt((String) value));
        else if (type == 2)
            general = new CellDouble(row, col, Double.parseDouble((String) value));
        else if (type == 3)
            general = new CellString(row, col, (String) value);
        else
            general = new CellFormula(row, col, (String) value);

        matrix.set(row, matrix.get(row)).set(col, general);
    }

    private int[] extractPos(Object input) {
        int[] res = new int[4];
        Pattern patternLet = Pattern.compile("([A-Z])+");
        Pattern patternNum = Pattern.compile("([1-9])+");

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

    public double calcFormula(Object input) {
        int[] val = extractPos(input);
        Object val1 = matrix.get(val[1] - 1).get(val[0]).getValue();
        Object val2 = matrix.get(val[3] - 1).get(val[2]).getValue();
        return new CellFormula().doOp((Number) val1, (Number) val2, input.toString().charAt(3));
    }
}
