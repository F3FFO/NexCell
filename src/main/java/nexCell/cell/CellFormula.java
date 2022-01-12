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

package nexCell.cell;

import java.io.Serializable;

/**
 * This class is a model class for the project.
 *
 * @author Federico Pierantoni
 * @see nexCell.cell.Cell
 */
public class CellFormula extends Cell implements Serializable {

    /**
     * Pattern to match all formulas:
     * <ul>
     *     <li>=A1+B2</li>
     *     <li>=9*A1</li>
     *     <li>=A1/-3.6</li>
     * </ul>
     */
    public static final String PATTERN = "^=(([A-Z][0-9]+)|([\\-]?([0-9]+)(([\\,\\.]?[0-9]+)?)))[+|\\-|*|/](([A-Z][0-9]+)|([\\-]?([0-9]+)(([\\,\\.]?[0-9]+)?)))$";
    /**
     * Pattern to match formulas like:
     * <ul>
     *     <li>=5-8</li>
     *     <li>=9.8+2</li>
     *     <li>=9*-1,7</li>
     * </ul>
     */
    public static final String PATTERN_NUMBER = "^=[-]?([0-9]+)(([\\,\\.]?[0-9]+)?)[+|\\-|*|/][-]?([0-9]+)(([\\,\\.]?[0-9]+)?)$";
    /**
     * Pattern to match formulas like:
     * <ul>
     *     <li>=A1--3</li>
     *     <li>=A1*2.3</li>
     *     <li>=9*-1</li>
     * </ul>
     */
    public static final String PATTERN_MIX1 = "^=([A-Z][0-9]+)[+|\\-|*|/][-]?([0-9]+)(([\\,\\.]?[0-9]+)?)$";
    /**
     * Pattern to match formulas like:
     * <ul>
     *     <li>=8.6*A1</li>
     *     <li>=-2+A1</li>
     *     <li>=9-A1</li>
     * </ul>
     */
    public static final String PATTERN_MIX2 = "^=[-]?([0-9]+)(([\\,\\.]?[0-9]+)?)[+|\\-|*|/]([A-Z][0-9]+)$";
    /**
     * Error after a calc.
     */
    public static final String ERROR = "#VALORE!";
    /**
     * Value entered by the user.
     */
    private final String originalValue;
    /**
     * Value shown to the user.
     */
    private Object value;

    /**
     * Construct a formula cell.
     *
     * @param row           {@link Cell#row}
     * @param column        {@link  Cell#column}
     * @param originalValue {@link CellFormula#originalValue}
     */
    public CellFormula(int row, int column, String originalValue) {
        super(row, column);
        this.originalValue = originalValue;
    }

    /**
     * Returns the {@link CellFormula#originalValue} entered by the user.
     *
     * @return the value entered by the user
     */
    public String getOriginalValue() {
        return originalValue;
    }

    /**
     * Return the calculated value.
     *
     * @param val1   value of the first operand of the numerical operation
     * @param val2   value of the second operand of the numerical operation
     * @param symbol type of numerical operation
     * @return the calculated value
     */
    public static Number doOp(Number val1, Number val2, char symbol) {
        if (symbol == '+') {
            return val1.doubleValue() + val2.doubleValue();
        } else if (symbol == '-') {
            return val1.doubleValue() - val2.doubleValue();
        } else if (symbol == '*') {
            return val1.doubleValue() * val2.doubleValue();
        } else {
            return val1.doubleValue() / val2.doubleValue();
        }
    }

    /**
     * Checks if the {@link CellFormula#value} is an integer or a double and returns it.
     *
     * @return the calculated value of the cell
     */
    @Override
    public Object getValue() {
        double valTemp = Double.parseDouble(value.toString());
        if ((valTemp % 1) == 0)
            return (int) valTemp;
        else
            return valTemp;
    }

    /**
     * Set the value of the cell.
     *
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }
}
