import java.io.IOException;

public class GraphicOutput {
    static void showMacNotification(String subtitle, String body) {
        if (DataManager.isThisMac()) {
            try {
                Runtime.getRuntime().exec(new String[]{"osascript", "-e", "display notification \"" + body + "\" with title \"" + "Easy Device Capture" + "\" subtitle \"" + subtitle + "\""});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void showMacNotification(String subtitle) {
        if (DataManager.isThisMac()) {
            try {
                Runtime.getRuntime().exec(new String[]{"osascript", "-e", "display notification \"" + "" + "\" with title \"" + "Easy Device Capture" + "\" subtitle \"" + subtitle + "\""});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
