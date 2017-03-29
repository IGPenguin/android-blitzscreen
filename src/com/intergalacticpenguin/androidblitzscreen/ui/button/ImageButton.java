package com.intergalacticpenguin.androidblitzscreen.ui.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ImageButton extends JButton {
    public ImageButton(String resourceName, ActionListener clickAction) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/" + resourceName));

        setIcon(icon);
        setVisible(true);
        setBorder(BorderFactory.createEmptyBorder());

        addActionListener(clickAction);
    }
}
