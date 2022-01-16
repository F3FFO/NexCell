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

package nexCell.view;

import net.miginfocom.swing.MigLayout;
import nexCell.controller.DataModel;
import nexCell.controller.SheetStructure;
import nexCell.controller.io.Autosave;
import nexCell.controller.io.SaveFile;
import nexCell.view.menu.MenuBar;
import nexCell.view.panel.InfoPanel;
import nexCell.view.panel.SheetsView;
import nexCell.view.rowHeader.RowHeader;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * This class contain the main frame.
 *
 * @author Federico Pierantoni
 */
public class Gui extends JFrame {

    /**
     * Object of the top panel
     *
     * @see InfoPanel
     */
    private final InfoPanel INFO;
    /**
     * Object of {@link Autosave}
     *
     * @see Autosave
     */
    private Autosave toSave;
    /**
     * Object of the data structure
     *
     * @see SheetStructure
     */
    private SheetStructure sheetStructure;
    /**
     * Object of the data model
     *
     * @see DataModel
     */
    private DataModel model;

    /**
     * Construct and initialize the frame.
     *
     * @param file temporary save file
     */
    public Gui(File file) {
        super("NexCell");
        this.setLayout(new MigLayout("ins 0", "[grow,fill]", "[grow,fill]"));
        this.setPreferredSize(new Dimension(900, 600));
        this.setMinimumSize(new Dimension(600, 400));

        // initialize the main object
        setSheetStructure(new SheetStructure());
        setModel(new DataModel(sheetStructure));
        this.INFO = new InfoPanel();
        SheetsView SHEETS_PANEL = new SheetsView(sheetStructure, model, this.INFO.getCELL_SELECTED(), this.INFO.getFORMULA());
        this.setJMenuBar(new MenuBar(this, sheetStructure, model, SHEETS_PANEL));
        this.add(INFO, "wrap, pushx, growx");

        // JScrollPane
        JScrollPane SCROLL_PANE = new JScrollPane();
        SCROLL_PANE.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        SCROLL_PANE.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        SCROLL_PANE.setRowHeaderView(new RowHeader(SHEETS_PANEL.getSheetsTable()));
        SCROLL_PANE.setColumnHeaderView(SHEETS_PANEL.getSheetsTable().getTableHeader());
        SCROLL_PANE.getViewport().add(SHEETS_PANEL);
        this.add(SCROLL_PANE, "push, grow");
        // auto-save
        toSave = new Autosave(saveTemp(file));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Returns the data structure: {@link Gui#sheetStructure}.
     *
     * @return the data structure
     * @see SheetStructure
     */
    public SheetStructure getSheetStructure() {
        return sheetStructure;
    }

    /**
     * Sets data structure of the JTable.
     *
     * @param sheetStructure data structure of the JTable
     */
    public void setSheetStructure(SheetStructure sheetStructure) {
        this.sheetStructure = sheetStructure;
    }

    /**
     * Returns the data model: {@link Gui#model}.
     *
     * @return the data structure
     * @see DataModel
     */
    public DataModel getModel() {
        return model;
    }

    /**
     * Sets the model of the JTable.
     *
     * @param model to set
     */
    public void setModel(DataModel model) {
        this.model = model;
    }

    /**
     * Returns the object of AutoSave class.
     *
     * @return the object of AutoSave class
     * @see Autosave
     */
    public Autosave getToSave() {
        return toSave;
    }

    /**
     * Start the thread to save the file.
     *
     * @param file to save
     * @return the runnable which contain the thread
     * @see SaveFile
     */
    public Runnable saveTemp(File file) {
        Runnable runnable = new SaveFile(file, this.sheetStructure.getMatrix());
        new Thread(runnable).start();
        return runnable;
    }
}
