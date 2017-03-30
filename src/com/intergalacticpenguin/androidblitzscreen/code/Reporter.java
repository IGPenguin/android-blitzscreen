package com.intergalacticpenguin.androidblitzscreen.code;

import java.io.IOException;

public class Reporter {

    public static void report(String messageTitle, String messageSubtitle) {
        System.out.println(messageTitle+" "+messageSubtitle);
        if (DataManager.isThisMac()) {
            showMacNotification(messageTitle,messageSubtitle);
        }
    }

    public static void report(String message) {
        System.out.println(message);
        if (DataManager.isThisMac()) {
            showMacNotification(message);
        }
    }

    static private void showMacNotification(String subtitle, String body) {
        try {
            Runtime.getRuntime().exec(new String[]{"osascript", "-e", "display notification \"" + body + "\" with title \"" + "Android Blitzscreen" + "\" subtitle \"" + subtitle + "\""});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static private void showMacNotification(String subtitle) {
        showMacNotification(subtitle, "");
    }
}
