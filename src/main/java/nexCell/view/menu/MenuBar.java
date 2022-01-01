package nexCell.view.menu;

import nexCell.view.Gui;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    public MenuBar(Gui frame) {
        this.add(new MenuFile(frame));
    }
}
