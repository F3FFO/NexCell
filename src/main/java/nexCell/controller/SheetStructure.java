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
            if (!Pattern.matches(CellFormula.PATTERN, (String) value))
                return CELL_STRING;
            else if (value != "")
                return CELL_FORMULA;
        }
        return CELL;
    }

    public void convertCell(int row, int col, Object value, int type) {
        Cell general;
        if (type == CELL)
            general = new Cell(row, col);
        else if (type == CELL_NUMBER)
            general = new CellNumber(row, col, Double.parseDouble(value.toString()));
        else if (type == CELL_STRING)
            general = new CellString(row, col, value.toString());
        else
            general = new CellFormula(row, col, value.toString());

        matrix.set(row, matrix.get(row)).set(col, general);
    }

    private Number[] extractPos(Object input) {
        Pattern ptnNum = Pattern.compile(CellFormula.PATTERNNUMBER);
        Pattern ptnMix1 = Pattern.compile(CellFormula.PATTERNMIX1);
        Pattern ptnMix2 = Pattern.compile(CellFormula.PATTERNMIX2);
        Pattern patternLet = Pattern.compile("[A-Z]+");
        Pattern patternNum = Pattern.compile("([0-9]+)(([\\.]?[0-9]+)?)");

        Matcher m1 = ptnNum.matcher(input.toString());
        Matcher m2 = ptnMix1.matcher(input.toString());
        Matcher m3 = ptnMix2.matcher(input.toString());

        Number[] data = new Number[4];
        int op = (input.toString().contains("+")) ? input.toString().indexOf('+') : (input.toString().contains("-")) ? input.toString().indexOf('-') : (input.toString().contains("*")) ? input.toString().indexOf('*') : (input.toString().contains("/")) ? input.toString().indexOf('/') : -1;
        input = input.toString().replaceAll(",", ".");

        if (m1.matches()) {
            data[0] = -1;
            data[1] = Double.parseDouble(input.toString().substring(1, op));
            data[2] = Double.parseDouble(input.toString().substring(op + 1));
        } else if (m2.matches()) {
            data[0] = -2;
            Matcher matcher = patternLet.matcher(input.toString());
            if (matcher.find())
                data[1] = matcher.group(0).charAt(0) - 'A';

            matcher = patternNum.matcher(input.toString());
            if (matcher.find())
                data[2] = Integer.parseInt(matcher.group(0));

            data[3] = Double.parseDouble(input.toString().substring(op + 1));
        } else if (m3.matches()) {
            data[0] = -3;
            data[1] = Double.parseDouble(input.toString().substring(1, op));
            Matcher matcher = patternLet.matcher(input.toString());
            if (matcher.find())
                data[2] = matcher.group(0).charAt(0) - 'A';

            matcher = patternNum.matcher(input.toString());
            if (matcher.find())
                data[3] = Integer.parseInt(matcher.group(0));
        } else {
            Matcher matcher = patternLet.matcher(input.toString());
            if (matcher.find())
                data[0] = matcher.group(0).charAt(0) - 'A';
            if (matcher.find())
                data[2] = matcher.group(0).charAt(0) - 'A';

            matcher = patternNum.matcher(input.toString());
            if (matcher.find())
                data[1] = Integer.parseInt(matcher.group(0));
            if (matcher.find())
                data[3] = Integer.parseInt(matcher.group(0));
        }
        return data;
    }

    public Object calcFormula(Object input) {
        Number[] values = extractPos(input);
        char op = '+';
        if (input.toString().contains("-"))
            op = '-';
        else if (input.toString().contains("*"))
            op = '*';
        else if (input.toString().contains("/"))
            op = '/';

        try {
            if (values[0].intValue() == -1) {
                return new CellFormula().doOp(values[1], values[2], op);
            } else if (values[0].intValue() == -2) {
                Object val1 = matrix.get(values[2].intValue() - 1).get(values[1].intValue()).getValue();
                return new CellFormula().doOp((Number) val1, values[3], op);
            } else if (values[0].intValue() == -3) {
                Object val2 = matrix.get(values[3].intValue() - 1).get(values[2].intValue()).getValue();
                return new CellFormula().doOp(values[1], (Number) val2, op);
            } else {
                Object val1 = matrix.get(values[1].intValue() - 1).get(values[0].intValue()).getValue();
                Object val2 = matrix.get(values[3].intValue() - 1).get(values[2].intValue()).getValue();

                return new CellFormula().doOp((Number) val1, (Number) val2, op);
            }
        } catch (Exception e) {
            return CellFormula.ERROR;
        }
    }
}
