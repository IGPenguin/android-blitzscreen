package com.intergalacticpenguin.androidblitzscreen.ui.button.specific;

import com.intergalacticpenguin.androidblitzscreen.code.AdbCommandDispatcher;
import com.intergalacticpenguin.androidblitzscreen.ui.button.ImageButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecordButton extends ImageButton {
    public RecordButton() {
        super("ic_record.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdbCommandDispatcher.recordDefaultDevice();
            }
        });

    }
}
