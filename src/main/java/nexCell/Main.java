package nexCell;

import com.formdev.flatlaf.FlatLightLaf;
import nexCell.view.Gui;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        UIManager.put("TextComponent.arc", 30);
        new Gui();
    }
}