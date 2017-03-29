package com.intergalacticpenguin.androidblitzscreen.ui.button.specific;

import com.intergalacticpenguin.androidblitzscreen.code.DataManager;
import com.intergalacticpenguin.androidblitzscreen.ui.button.ImageButton;
import com.intergalacticpenguin.androidblitzscreen.ui.window.specific.SettingsWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsButton extends ImageButton {

    public SettingsButton() {
        super("ic_info.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (DataManager.getSettingsInstance() == null) {
                    DataManager.setSettingsInstance(new SettingsWindow());
                } else {
                    DataManager.getSettingsInstance().dispose();
                    DataManager.setSettingsInstance(null);
                }
            }
        });
    }
}
