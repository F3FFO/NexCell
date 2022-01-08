package nexCell;

import com.formdev.flatlaf.FlatLightLaf;
import nexCell.view.Gui;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException {
        FlatLightLaf.setup();
        UIManager.put("TextComponent.arc", 30);
        Gui g = new Gui();

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable save = g.saveTemp(File.createTempFile(".unsaved", ".tmp"));
        executor.scheduleAtFixedRate(save, 10, 15, TimeUnit.SECONDS);
    }
}