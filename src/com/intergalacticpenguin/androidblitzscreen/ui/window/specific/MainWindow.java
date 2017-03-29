package com.intergalacticpenguin.androidblitzscreen.ui.window.specific;

import com.intergalacticpenguin.androidblitzscreen.code.DataManager;
import com.intergalacticpenguin.androidblitzscreen.ui.button.specific.RecordButton;
import com.intergalacticpenguin.androidblitzscreen.ui.button.specific.SettingsButton;
import com.intergalacticpenguin.androidblitzscreen.ui.window.CustomWindow;

import javax.swing.*;
import java.awt.*;


public class MainWindow extends CustomWindow {

    public MainWindow() {
        super(320, 140, "Android Blitzscreen " + DataManager.VERSION, true);
        setIconImage(new ImageIcon(getClass().getResource("ic_screenshot.png")).getImage());
        setLayout(new GridBagLayout());

        addToGrid(1, 0, new com.intergalacticpenguin.androidblitzscreen.ui.button.specific.ScreenshotButton());
        addToGrid(2, 0, new RecordButton());
        addToGrid(3, 0, new SettingsButton());

    }

}
