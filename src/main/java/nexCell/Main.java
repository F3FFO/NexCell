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

package nexCell;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.util.SystemInfo;
import nexCell.view.Gui;

import javax.swing.*;
import java.io.File;

/**
 * This class is the main class.
 *
 * @author Federico Pierantoni
 */
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

            // file temp
            File file = new File(System.getProperty("java.io.tmpdir"), ".unsaved-nexcell.tmp");

            // create frame
            Gui frame = new Gui(file);

            //ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            //Runnable save = frame.saveTemp(file);
            //executor.scheduleWithFixedDelay(save, 10, 15, TimeUnit.SECONDS);

            // show frame
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
