package com.intergalacticpenguin.androidblitzscreen.ui.button.specific;

import com.intergalacticpenguin.androidblitzscreen.code.DataManager;
import com.intergalacticpenguin.androidblitzscreen.ui.Text;
import com.intergalacticpenguin.androidblitzscreen.ui.button.ImageButton;
import com.intergalacticpenguin.androidblitzscreen.ui.button.UrlButton;
import com.intergalacticpenguin.androidblitzscreen.ui.window.CustomWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsButton extends ImageButton {
    public SettingsButton() {
        super("ic_info.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomWindow settingsWindow = new CustomWindow(320, 180, "Android Blitzscreen " + DataManager.VERSION + " - information");
                Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
                settingsWindow.setLocation((int) screenDimensions.getWidth() / 2 + 160, (int) screenDimensions.getHeight() / 2 - 61); // Ugly but positions info right next to main window
                settingsWindow.setLayout(new GridBagLayout());
                settingsWindow.setIconImage(new ImageIcon("res/com/intergalacticpenguin/androidblitzscreen/ic_settings.png").getImage());


                settingsWindow.addToGrid(0, 0, new Text("Hold Shift + Alt and press", SwingConstants.CENTER));
                settingsWindow.addToGrid(0, 1, new Text("<br>P - default device screenshot", SwingConstants.LEFT));
                settingsWindow.addToGrid(0, 2, new Text("R - default device start/stop recording", SwingConstants.LEFT));
                settingsWindow.addToGrid(0, 3, new Text("D - cycle default device", SwingConstants.LEFT));
                settingsWindow.addToGrid(0, 4, new Text("A - all devices screenshot", SwingConstants.LEFT));
                settingsWindow.addToGrid(0, 5, new Text("", SwingConstants.LEFT));
                settingsWindow.addToGrid(0, 6, new UrlButton("☆ on GitHub", "https://github.com/eidamsvoboda/android-blitzscreen"));

            }
        });

    }
}
