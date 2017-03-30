package com.intergalacticpenguin.androidblitzscreen.ui.window.specific;

import com.apple.eawt.Application;
import com.intergalacticpenguin.androidblitzscreen.code.DataManager;
import com.intergalacticpenguin.androidblitzscreen.ui.Text;
import com.intergalacticpenguin.androidblitzscreen.ui.button.specific.RecordButton;
import com.intergalacticpenguin.androidblitzscreen.ui.button.specific.ScreenshotButton;
import com.intergalacticpenguin.androidblitzscreen.ui.button.specific.SettingsButton;
import com.intergalacticpenguin.androidblitzscreen.ui.window.CustomWindow;

import javax.swing.*;
import java.awt.*;


public class MainWindow extends CustomWindow {
    static private Text defaultDeviceText = new Text("", SwingConstants.CENTER);

    public MainWindow() {
        super(320, 140, "Android Blitzscreen " + DataManager.VERSION, true);
        setLayout(new GridBagLayout());

        addToGrid(1, 0, 3, defaultDeviceText);
        addToGrid(1, 2, new ScreenshotButton());
        addToGrid(2, 2, new RecordButton());
        addToGrid(3, 2, new SettingsButton());

        Application.getApplication().setDockIconImage(new ImageIcon(getClass().getResource("/ic_icon.png")).getImage());
        refreshDefaultDeviceText();
    }

    static public void refreshDefaultDeviceText() {
        if (DataManager.getDefaultAdbDeviceIndex() != -1) {
            defaultDeviceText.setText("Default device: " + DataManager.getAdbDeviceId(DataManager.getDefaultAdbDeviceIndex()));
        } else {
            defaultDeviceText.setText("No device connected");
        }
    }

}
