/*
 * Copyright 2022 F3FFO - Federico Pierantoni
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nexCell.controller;

import nexCell.cell.Cell;
import nexCell.cell.CellFormula;
import nexCell.cell.CellNumber;
import nexCell.cell.CellString;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is contains the logic necessary for the program to work.
 *
 * @author Federico Pierantoni
 */
public class SheetStructure {

    /**
     * Number of row
     */
    public static final int ROW = 1000;
    /**
     * Number of column
     */
    public static final int COLUMN = 26;
    /**
     * The Integer object corresponding to {@link nexCell.cell.Cell}
     */
    public static final int CELL = -1;
    /**
     * The Integer object corresponding to {@link nexCell.cell.CellNumber}
     */
    public static final int CELL_NUMBER = 1;
    /**
     * The Integer object corresponding to {@link nexCell.cell.CellString}
     */
    public static final int CELL_STRING = 2;
    /**
     * The Integer object corresponding to {@link nexCell.cell.CellFormula}
     */
    public static final int CELL_FORMULA = 3;
    /**
     * Data structure of the JTable
     */
    private List<List<Cell>> matrix;
    /**
     * Array that contains only formula cells
     */
    private final List<CellFormula> cellFormula;

    /**
     * Construct the data structure by initializing the elements.
     */
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

    /**
     * Returns the data structure: {@link SheetStructure#matrix}.
     *
     * @return the data structure
     */
    public List<List<Cell>> getMatrix() {
        return matrix;
    }

    /**
     * Returns the {@link SheetStructure#cellFormula} array that contains only formula cells.
     *
     * @return the array that contains only formula cells.
     */
    public List<CellFormula> getCellFormula() {
        return this.cellFormula;
    }

    /**
     * Return an Integer corresponding to the type of cell.
     * <ul>
     *     <li>{@link SheetStructure#CELL}</li>
     *     <li>{@link SheetStructure#CELL_NUMBER}</li>
     *     <li>{@link SheetStructure#CELL_STRING}</li>
     *     <li>{@link SheetStructure#CELL_FORMULA}</li>
     * </ul>
     *
     * @param value taken in input from the user
     * @return an Integer corresponding to the type of cell
     */
    public int checkTypeCell(Object value) {
        try {
            // try to parse in a Number
            NumberFormat.getInstance().parse(value.toString());
            return CELL_NUMBER;
        } catch (ParseException | NumberFormatException e) {
            // try to math with formula pattern
            if (!(Pattern.matches(CellFormula.PATTERN, value.toString())) && !value.toString().equals(""))
                return CELL_STRING;
            else if (!value.toString().equals(""))
                return CELL_FORMULA;
        }
        return CELL;
    }

    /**
     * Performs cell parsing.
     *
     * @param row    {@link Cell#row}
     * @param column {@link Cell#column}
     * @param value  value of the cell
     * @param type   Integer corresponding to the type of cell to parse current cell
     */
    public void parseCell(int row, int column, Object value, int type) {
        Cell general;
        if (type == CELL)
            general = new Cell(row, column);
        else if (type == CELL_NUMBER)
            general = new CellNumber(row, column, Double.parseDouble(value.toString()));
        else if (type == CELL_STRING)
            general = new CellString(row, column, value.toString());
        else
            general = new CellFormula(row, column, value.toString());

        matrix.set(row, matrix.get(row)).set(column, general);
    }

    /**
     * Return the position of the symbol.
     *
     * @param value taken in input form the user
     * @return the position of the symbol
     */
    private int getSymbolPos(Object value) {
        String temp = value.toString().substring(2);
        return 2 + ((temp.contains("+")) ? temp.indexOf('+') : (temp.contains("*")) ? temp.indexOf('*') : (temp.contains("/")) ? temp.indexOf('/') : (temp.contains("-")) ? temp.indexOf('-') : -1);
    }

    /**
     * Extracts the operand and the symbol of the operation written by the user.
     *
     * @param value taken in input from the user
     * @return an array that contain the value to use for the calcs
     */
    private Number[] extractPos(Object value) {
        Pattern ptnNum = Pattern.compile(CellFormula.PATTERN_NUMBER);
        Pattern ptnMix1 = Pattern.compile(CellFormula.PATTERN_MIX1);
        Pattern ptnMix2 = Pattern.compile(CellFormula.PATTERN_MIX2);
        Pattern patternLet = Pattern.compile("[A-Z]+");
        Pattern patternNum = Pattern.compile("([0-9]+)(([\\.]?[0-9]+)?)");
        Matcher m1 = ptnNum.matcher(value.toString());
        Matcher m2 = ptnMix1.matcher(value.toString());
        Matcher m3 = ptnMix2.matcher(value.toString());

        Number[] data = new Number[4];
        int symbol = getSymbolPos(value);
        value = value.toString().replaceAll(",", ".");

        // case 1: operation consisting of numbers only
        if (m1.matches()) {
            data[0] = -1;
            data[1] = Double.parseDouble(value.toString().substring(1, symbol));
            data[2] = Double.parseDouble(value.toString().substring(symbol + 1));
            // case 2: operation consisting of cell on the left and number on the right
        } else if (m2.matches()) {
            data[0] = -2;
            String temp = value.toString().substring(1, symbol);
            Matcher matcher = patternLet.matcher(temp);
            if (matcher.find())
                data[1] = matcher.group(0).charAt(0) - 'A';

            matcher = patternNum.matcher(temp);
            if (matcher.find())
                data[2] = Integer.parseInt(matcher.group(0));

            data[3] = Double.parseDouble(value.toString().substring(symbol + 1));
            // case 3: operation consisting of number on the left and cell on the right
        } else if (m3.matches()) {
            data[0] = -3;
            data[1] = Double.parseDouble(value.toString().substring(1, symbol));
            String temp = value.toString().substring(symbol + 1);
            Matcher matcher = patternLet.matcher(temp);
            if (matcher.find())
                data[2] = matcher.group(0).charAt(0) - 'A';

            matcher = patternNum.matcher(temp);
            if (matcher.find())
                data[3] = Integer.parseInt(matcher.group(0));
            // case 4: operation consisting of cell only
        } else {
            Matcher matcher = patternLet.matcher(value.toString());
            if (matcher.find())
                data[0] = matcher.group(0).charAt(0) - 'A';
            if (matcher.find())
                data[2] = matcher.group(0).charAt(0) - 'A';

            matcher = patternNum.matcher(value.toString());
            if (matcher.find())
                data[1] = Integer.parseInt(matcher.group(0));
            if (matcher.find())
                data[3] = Integer.parseInt(matcher.group(0));
        }
        return data;
    }

    /**
     * Extract position of element and return the value to show to the user.
     *
     * @param value taken in input from the user
     * @return the value to show to the user
     * @see SheetStructure#extractPos(Object)
     * @see CellFormula#doOp(Number, Number, char)
     */
    public Object calcFormula(Object value) {
        Number[] values = extractPos(value);
        char symbol = value.toString().charAt(getSymbolPos(value));

        try {
            if (values[0].intValue() == -1) {
                return CellFormula.doOp(values[1], values[2], symbol);
            } else if (values[0].intValue() == -2) {
                Object val1 = matrix.get(values[2].intValue() - 1).get(values[1].intValue()).getValue();
                return CellFormula.doOp((Number) val1, values[3], symbol);
            } else if (values[0].intValue() == -3) {
                Object val2 = matrix.get(values[3].intValue() - 1).get(values[2].intValue()).getValue();
                return CellFormula.doOp(values[1], (Number) val2, symbol);
            } else {
                Object val1 = matrix.get(values[1].intValue() - 1).get(values[0].intValue()).getValue();
                Object val2 = matrix.get(values[3].intValue() - 1).get(values[2].intValue()).getValue();

                return CellFormula.doOp((Number) val1, (Number) val2, symbol);
            }
        } catch (Exception e) {
            return CellFormula.ERROR;
        }
    }
}
