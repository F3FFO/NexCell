package nexCell;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.util.SystemInfo;
import nexCell.view.Gui;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        if (SystemInfo.isMacOS) {
            // enable screen menu bar
            // (moves menu bar from JFrame window to top of screen)
            System.setProperty("apple.laf.useScreenMenuBar", "true");

            // application name used in screen menu bar
            // (in first menu after the "apple" menu)
            System.setProperty("apple.awt.application.name", "NexCell");

            // appearance of window title bars
            // possible values:
            //   - "system": use current macOS appearance (light or dark)
            //   - "NSAppearanceNameAqua": use light appearance
            //   - "NSAppearanceNameDarkAqua": use dark appearance
            System.setProperty("apple.awt.application.appearance", "NSAppearanceNameAqua");
        }

        SwingUtilities.invokeLater(() -> {
            // set look and feel
            FlatLaf.registerCustomDefaultsSource("nexCell");
            FlatLightLaf.setup();

            // create frame
            Gui frame = new Gui();

            // run autosave
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            try {
                Runnable save = frame.saveTemp(File.createTempFile(".unsaved", ".tmp"));
                executor.scheduleAtFixedRate(save, 10, 15, TimeUnit.SECONDS);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // show frame
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
