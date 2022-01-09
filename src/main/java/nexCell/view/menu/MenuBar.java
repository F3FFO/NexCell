package nexCell.view.menu;

import nexCell.controller.MyDataModel;
import nexCell.controller.SheetStructure;
import nexCell.view.Gui;
import nexCell.view.customElement.panel.SheetsView;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    public MenuBar(Gui frame, SheetStructure sheetStructure, MyDataModel model, SheetsView SHEETS) {
        this.add(new MenuFile(frame, sheetStructure, model, SHEETS));
    }
}
