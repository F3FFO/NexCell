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

package nexCell.view.menu;

import nexCell.controller.DataModel;
import nexCell.controller.SheetStructure;
import nexCell.view.Gui;
import nexCell.view.panel.SheetsView;

import javax.swing.*;

/**
 * This class contains objects for each menu item.
 *
 * @author Federico Pierantoni
 */
public class MenuBar extends JMenuBar {

    /**
     * Construct the objects of the JMenuBar.
     *
     * @param frame          main JFrame
     * @param sheetStructure data structure
     * @param model          model of the Jtable
     * @param SHEETS         panel to modify
     * @see Gui
     * @see SheetStructure
     * @see DataModel
     * @see SheetsView
     */
    public MenuBar(Gui frame, SheetStructure sheetStructure, DataModel model, SheetsView SHEETS) {
        this.add(new MenuFile(frame, sheetStructure, model, SHEETS));
        this.add(new MenuHelp());
    }
}
