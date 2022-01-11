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

package nexCell.controller.io;

import nexCell.cell.Cell;

import java.io.*;
import java.util.List;

/**
 * This class contains the logic for saving files
 *
 * @author Federico Pierantoni
 */
public class SaveFile implements Runnable {

    /**
     * Path were the file is stored
     */
    private final File fileSelected;
    /**
     * {@link nexCell.controller.SheetStructure#matrix}
     */
    private final List<List<Cell>> matrix;

    /**
     * Initialize the attributes.
     *
     * @param fileSelected {@link SaveFile#fileSelected}
     * @param matrix       {@link SaveFile#matrix}
     */
    public SaveFile(File fileSelected, List<List<Cell>> matrix) {
        this.fileSelected = fileSelected;
        this.matrix = matrix;
    }

    /**
     * Execute the saving of the file.
     */
    @Override
    public void run() {
        try {
            // open the statement
            ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(fileSelected));
            objOut.writeObject(matrix);
            // close the statement
            objOut.close();
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        } catch (IOException e2) {
            System.out.println("Errore chiusura stream");
        }
    }
}
