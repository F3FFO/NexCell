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

    private static final JScrollPane SCROLL_PANE = new JScrollPane();
    /**
     * Object of the top panel
     *
     * @see InfoPanel
     */
    private final InfoPanel INFO;
    /**
     * Object of the JTable panel
     *
     * @see SheetsView
     */
    private final SheetsView SHEETS;

    /**
     * Construct and initialize the frame.
     */
    public Gui() {
        super("NexCell");
        this.setLayout(new MigLayout("ins 0", "[grow,fill]", "[grow,fill]"));
        this.setPreferredSize(new Dimension(900, 600));
        this.setMinimumSize(new Dimension(600, 400));

        // initialize the main object
        SheetStructure sheetStructure = new SheetStructure();
        DataModel model = new DataModel(sheetStructure);
        this.INFO = new InfoPanel();
        this.SHEETS = new SheetsView(sheetStructure, model, this.INFO.getCELL_SELECTED(), this.INFO.getFORMULA());

        this.setJMenuBar(new MenuBar(this, sheetStructure, model, this.SHEETS));
        this.add(INFO, "wrap, pushx, growx");

        // initialize the JScrollPane
        SCROLL_PANE.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        SCROLL_PANE.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        SCROLL_PANE.setRowHeaderView(new RowHeader(SHEETS.getSHEETS()));
        SCROLL_PANE.setColumnHeaderView(SHEETS.getSHEETS().getTableHeader());
        SCROLL_PANE.getViewport().add(SHEETS);
        this.add(SCROLL_PANE, "push, grow");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Return the object of the panel that contain the JTable.
     *
     * @return the panel that contain the JTable
     * @see SheetsView
     */
    public SheetsView getSHEETS() {
        return SHEETS;
    }

    /**
     * Start the thread to save the file.
     *
     * @param file to save
     * @return the runnable which contain the thread
     * @see SaveFile
     */
    public Runnable saveTemp(File file) {
        Runnable runnable = new SaveFile(file, this.getSHEETS().getSheetStructure().getMatrix());
        new Thread(runnable).start();
        return runnable;
    }
}
