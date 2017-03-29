package com.intergalacticpenguin.androidblitzscreen.ui.button.specific;

import com.intergalacticpenguin.androidblitzscreen.code.AdbCommandDispatcher;
import com.intergalacticpenguin.androidblitzscreen.ui.button.ImageButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScreenshotButton extends ImageButton {
    public ScreenshotButton() {
        super("ic_screenshot.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdbCommandDispatcher.takeScreenshotOfDefaultDevice();
            }
        });

    }
}
