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
public class CellNumber extends Cell implements Serializable {

    /**
     * Numeric value of a cell.
     */
    private Number value;

    /**
     * Construct a numeric cell.
     *
     * @param row    {@link Cell#row}
     * @param column {@link  Cell#column}
     * @param value  of the cell
     */
    public CellNumber(int row, int column, Number value) {
        super(row, column);
        this.setValue(value);
    }

    /**
     * Checks if the {@link CellNumber#value} is an integer or a double and returns it.
     *
     * @return the value of the cell
     */
    @Override
    public Number getValue() {
        if ((((double) value) % 1) == 0) {
            return value.intValue();
        } else
            return value;
    }

    /**
     * Set the value of the cell.
     *
     * @param value the value to set
     */
    public void setValue(Number value) {
        this.value = value;
    }
}
