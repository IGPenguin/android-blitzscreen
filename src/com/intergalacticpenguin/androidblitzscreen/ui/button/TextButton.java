package com.intergalacticpenguin.androidblitzscreen.ui.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class TextButton extends JButton {
    public TextButton(String text, ActionListener clickAction) {
        setText(text);
        setVisible(true);
        //setBorder(BorderFactory.createEmptyBorder());

        addActionListener(clickAction);
    }
}
