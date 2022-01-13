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
 */
public class Cell implements Serializable {

    /**
     * row oh the element.
     */
    private final int row;
    /**
     * column oh the element.
     */
    private final int column;

    /**
     * Construct a standard generic cell.
     *
     * @deprecated
     */
    @Deprecated
    public Cell() {
        this.row = 0;
        this.column = 0;
    }

    /**
     * Construct a new generic cell.
     *
     * @param row    of the element
     * @param column of the element
     */
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Return the {@link Cell#row} of the element.
     *
     * @return the row of the element
     */
    public int getRow() {
        return row;
    }

    /**
     * Return the {@link Cell#column} of the element.
     *
     * @return the column of the element
     */
    public int getColumn() {
        return column;
    }

    /**
     * Return the default value of the generic cell
     *
     * @return the default value of the generic cell
     */
    public Object getValue() {
        return null;
    }
}
