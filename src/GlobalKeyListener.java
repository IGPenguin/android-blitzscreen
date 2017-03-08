import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

    private boolean shiftPressed = false;
    private boolean altPressed = false;
    private boolean recordingInProgress = false;

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        //System.out.println("Pressed: " + NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));

        if (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).equalsIgnoreCase("shift")) {
            shiftPressed = true;
        }

        if (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).equalsIgnoreCase("alt")) {
            altPressed = true;
        }

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        //System.out.println("Released: " + NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));

        if (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).equalsIgnoreCase("shift")) {
            shiftPressed = false;
        }

        if (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).equalsIgnoreCase("alt")) {
            altPressed = false;
        }
        if (shiftPressed && altPressed) {
            switch (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode())) {

                case "P":
                    DataManager.updateAdbDeviceList();
                    if (DataManager.getAdbDeviceList().size() > 0) {
                        for (int i = 0; i < DataManager.getAdbDeviceList().size(); i++) {
                            AndroidCommand.takeScreenshot(i);
                        }
                    } else {
                        System.out.println("Cannot take screenshot, no Android device detected");
                    }
                    break;

                case "R":
                    DataManager.updateAdbDeviceList();
                    if (DataManager.getAdbDeviceList().size() > 0) {
                        if (!recordingInProgress) {
                            AndroidCommand.startRecordingScreen(0);
                            recordingInProgress = true;
                        } else {
                            AndroidCommand.stopRecordingScreen(0);
                            recordingInProgress = false;
                        }
                    } else {
                        System.out.println("Cannot start recording, no Android device detected");
                    }
            }
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }

}
