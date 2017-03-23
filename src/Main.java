import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final String VERSION = "1.0";
    private static GlobalKeyListener globalKeyListener;

    public static void main(String[] args) {
        registerShutdownHook();
        System.out.println("Android Blitzscreen " + VERSION + "\n\n" +
                "Shift + Alt + A - take screenshot of all connected devices\n" +
                "Shift + Alt + P - take screenshot of default device\n" +
                "Shift + Alt + R - start/stop recording on default device\n" +
                "Shift + Alt + D - change default device\n");

        initializeGlobalKeyListener();
        GraphicOutput.showMacNotification("Ready to use");
    }


    private static void initializeGlobalKeyListener() {
        // Disable logging for key listening
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.out.println("Native hook cant be registered, accessibility settings on your computer probably not set");
            System.exit(1);
        }

        globalKeyListener = new GlobalKeyListener();
        GlobalScreen.addNativeKeyListener(globalKeyListener);
    }

    private static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                if (globalKeyListener.recordingInProgress) {
                    AndroidCommand.stopRecordingScreen(DataManager.getDefaultAdbDevice());
                }
            }
        });
    }

}