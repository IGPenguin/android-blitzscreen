package com.intergalacticpenguin.androidblitzscreen.ui.window;

import javax.swing.*;
import java.awt.*;

public class CustomWindow extends JFrame {
    int width;
    int height;
    String title;
    boolean exitOnClosing;

    public CustomWindow(int width, int height, String title, boolean exitOnClosing) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.exitOnClosing = exitOnClosing;

        initialize();
    }

    public CustomWindow(int width, int height, String title) {
        this(width, height, title, false);
    }

    void initialize() {
        setTitle(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        if (exitOnClosing) {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }

    public void addToGrid(int gridx, int gridy, JComponent component) {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = gridx;
        constraints.gridy = gridy;

        getContentPane().add(component, constraints);
        setVisible(true);
    }

}
