package com.intergalacticpenguin.androidblitzscreen.ui;

import javax.swing.*;

public class Text extends JLabel {
    public Text(String text, int alignment) {
        setHorizontalAlignment(alignment);
        setText("<html>" + text + "</html>");
        setVisible(true);
    }
}
