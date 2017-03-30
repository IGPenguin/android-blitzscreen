package com.intergalacticpenguin.androidblitzscreen.code;

import com.intergalacticpenguin.androidblitzscreen.ui.window.specific.MainWindow;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        System.out.println("Android Blitzscreen " + DataManager.VERSION + "\n\n" +
                "Shift + Alt + A - take screenshot of all connected devices\n" +
                "Shift + Alt + P - take screenshot of default device\n" +
                "Shift + Alt + R - start/stop recording on default device\n" +
                "Shift + Alt + D - change default device\n");

        new MainWindow();
        registerNativeKeyListener(); // TODO make it work with swing - https://github.com/kwhat/jnativehook/wiki/Swing
        registerShutdownHook();
        Reporter.report("Ready to use");
    }

    private static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                if (DataManager.isRecordingInProgress()) {
                    AdbCommandDispatcher.recordDefaultDevice();
                }
            }
        });
    }

    private static void registerNativeKeyListener() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            Reporter.report("Native hook cant be registered, accessibility settings on your computer probably not set");
            ex.printStackTrace();
        }
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
    }
}