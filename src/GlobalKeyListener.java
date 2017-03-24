import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.IOException;

public class GlobalKeyListener implements NativeKeyListener {

    private boolean shiftPressed = false;
    private boolean altPressed = false;
    boolean recordingInProgress = false;

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).equalsIgnoreCase("shift")) {
            shiftPressed = true;
        }

        if (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).equalsIgnoreCase("alt")) {
            altPressed = true;
        }

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        if (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).equalsIgnoreCase("shift")) {
            shiftPressed = false;
        }

        if (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).equalsIgnoreCase("alt")) {
            altPressed = false;
        }
        if (shiftPressed && altPressed) {
            try {
                switch (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode())) {

                    case "A":
                        DataManager.updateAdbDeviceList();
                        if (DataManager.getAdbDeviceList().size() > 0) {
                            for (int i = 0; i < DataManager.getAdbDeviceList().size(); i++) {
                                AdbCommandDispatcher.takeScreenshot(i);
                            }
                        } else {
                            Reporter.report("Cannot take screenshot, no device detected");
                        }
                        break;

                    case "P":
                        DataManager.updateAdbDeviceList();
                        if (DataManager.getDefaultAdbDeviceIndex() != (-1)) {
                            AdbCommandDispatcher.takeScreenshot(DataManager.getDefaultAdbDeviceIndex());
                        } else {
                            Reporter.report("Cannot take screenshot, no device detected");
                        }
                        break;

                    case "R":
                        DataManager.updateAdbDeviceList();
                        if (DataManager.getDefaultAdbDeviceIndex() != (-1)) {
                            if (!recordingInProgress && AdbCommandDispatcher.recordingAvailable()) {
                                AdbCommandDispatcher.startRecordingScreen(DataManager.getDefaultAdbDeviceIndex());
                                recordingInProgress = true;
                            } else if (recordingInProgress) {
                                AdbCommandDispatcher.stopRecordingScreen(DataManager.getDefaultAdbDeviceIndex());
                                recordingInProgress = false;
                            }
                        } else {
                            Reporter.report("Cannot start recording, no device detected");
                        }
                        break;

                    case "D":
                        if (!recordingInProgress) {
                            DataManager.cycleDefaultAdbDevice();
                        } else {
                            Reporter.report("Cannot change default device while recording");
                        }
                        break;

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }

}
