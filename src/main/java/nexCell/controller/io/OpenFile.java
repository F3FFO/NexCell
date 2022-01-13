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
import nexCell.cell.CellFormula;
import nexCell.controller.MyDataModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * This class contains the logic for opening files
 *
 * @author Federico Pierantoni
 */
public class OpenFile implements Runnable {

    /**
     * Path were the file is stored
     */
    private final File fileSelected;
    private final MyDataModel model;

    /**
     * Initialize the attributes.
     *
     * @param fileSelected {@link OpenFile#fileSelected}
     * @param model        model of the JTable
     */
    public OpenFile(File fileSelected, MyDataModel model) {
        this.fileSelected = fileSelected;
        this.model = model;
    }

    /**
     * Execute the opening of the file.
     */
    @Override
    public void run() {
        try {
            // open the statement
            ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(fileSelected));
            List<List<Cell>> list = (List<List<Cell>>) objIn.readObject();

            // insert the values into the matrix
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++)
                    if (list.get(i).get(j) instanceof CellFormula)
                        // set the original value of the cell
                        model.setValueAt(((CellFormula) list.get(i).get(j)).getOriginalValue(), i, j);
                    else
                        // set the standard value of the cell
                        model.setValueAt(list.get(i).get(j).getValue(), i, j);
            }
            // close the statement
            objIn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("File non aperto");
        } catch (IOException e2) {
            System.out.println("Errore chiusura stream");
        }
    }
}
