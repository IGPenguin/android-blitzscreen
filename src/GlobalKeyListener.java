import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

    private boolean shiftPressed = false;
    private boolean altPressed = false;

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
            }
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }

}
