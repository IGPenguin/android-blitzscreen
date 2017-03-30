package com.intergalacticpenguin.androidblitzscreen.ui.window.specific;

import com.intergalacticpenguin.androidblitzscreen.code.DataManager;
import com.intergalacticpenguin.androidblitzscreen.ui.Text;
import com.intergalacticpenguin.androidblitzscreen.ui.button.UrlButton;
import com.intergalacticpenguin.androidblitzscreen.ui.window.CustomWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SettingsWindow extends CustomWindow {

    public SettingsWindow() {
        super(320, 180, "Android Blitzscreen " + DataManager.VERSION + " - information");
        Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) screenDimensions.getWidth() / 2 + 160, (int) screenDimensions.getHeight() / 2 - 61); // Ugly but positions info right next to main window
        setLayout(new GridBagLayout());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                DataManager.setSettingsInstance(null);
            }
        });

        addToGrid(0, 0, new Text("Hold Shift + Alt and press", SwingConstants.CENTER));
        addToGrid(0, 1, new Text("<br>P - default device screenshot", SwingConstants.LEFT));
        addToGrid(0, 2, new Text("R - default device start/stop recording", SwingConstants.LEFT));
        addToGrid(0, 3, new Text("D - cycle default device", SwingConstants.LEFT));
        addToGrid(0, 4, new Text("A - all devices screenshot", SwingConstants.LEFT));
        addToGrid(0, 5, new Text("", SwingConstants.LEFT));
        addToGrid(0, 6, new UrlButton("☆ on GitHub", "https://github.com/eidamsvoboda/android-blitzscreen"));
    }

}

