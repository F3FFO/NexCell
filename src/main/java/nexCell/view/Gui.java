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
import nexCell.controller.MyDataModel;
import nexCell.controller.SheetStructure;
import nexCell.controller.io.SaveFile;
import nexCell.view.customElement.panel.InfoPanel;
import nexCell.view.customElement.panel.SheetsView;
import nexCell.view.menu.MenuBar;
import nexCell.view.rowHeader.RowHeader;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Gui extends JFrame {

    private static final JScrollPane SCROLLPANE = new JScrollPane();
    private final InfoPanel INFO;
    private final SheetsView SHEETS;

    public Gui() {
        super("NexCell");
        this.setLayout(new MigLayout("ins 0", "[grow,fill]", "[grow,fill]"));
        this.setPreferredSize(new Dimension(900, 600));
        this.setMinimumSize(new Dimension(600, 400));

        SheetStructure sheetStructure = new SheetStructure();
        MyDataModel model = new MyDataModel(sheetStructure);
        this.INFO = new InfoPanel();
        this.SHEETS = new SheetsView(sheetStructure, model, this.INFO.getCELL_SELECTED(), this.INFO.getFORMULA());

        this.setJMenuBar(new MenuBar(this, sheetStructure, model, this.SHEETS));
        this.add(INFO, "wrap, pushx, growx");

        SCROLLPANE.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        SCROLLPANE.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        SCROLLPANE.setRowHeaderView(new RowHeader(SHEETS.getSHEETS()));
        SCROLLPANE.setColumnHeaderView(SHEETS.getSHEETS().getTableHeader());
        SCROLLPANE.getViewport().add(SHEETS);
        this.add(SCROLLPANE, "push, grow");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public SheetsView getSHEETS() {
        return SHEETS;
    }

    public Runnable saveTemp(File file) {
        Runnable runnable = new SaveFile(file, this.getSHEETS().getSheetStructure().getMatrix());
        new Thread(runnable).start();
        return runnable;
    }
}
