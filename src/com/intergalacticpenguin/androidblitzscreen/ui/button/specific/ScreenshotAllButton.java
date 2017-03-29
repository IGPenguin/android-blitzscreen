package com.intergalacticpenguin.androidblitzscreen.ui.button.specific;

import com.intergalacticpenguin.androidblitzscreen.code.AdbCommandDispatcher;
import com.intergalacticpenguin.androidblitzscreen.ui.button.ImageButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScreenshotAllButton extends ImageButton {
    public ScreenshotAllButton() {
        super("ic_screenshot_all.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdbCommandDispatcher.takeScreenshotOfAllDevices();
            }
        });

    }
}
