import java.io.IOException;

class GraphicOutput {
    static void showMacNotification(String subtitle, String body) {
        if (DataManager.isThisMac()) {
            try {
                Runtime.getRuntime().exec(new String[]{"osascript", "-e", "display notification \"" + body + "\" with title \"" + "Android Blitzscreen" + "\" subtitle \"" + subtitle + "\""});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void showMacNotification(String subtitle) {
        showMacNotification(subtitle, "");
    }
}
