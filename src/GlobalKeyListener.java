import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.IOException;

public class GlobalKeyListener implements NativeKeyListener {

    private boolean shiftPressed = false;
    private boolean altPressed = false;

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
                        AdbCommandDispatcher.takeScreenshotOfAllDevices();
                        break;

                    case "P":
                        AdbCommandDispatcher.takeScreenshotOfDefaultDevice();
                        break;

                    case "R":
                        AdbCommandDispatcher.recordDefaultDevice();
                        break;

                    case "D":
                        AdbCommandDispatcher.cycleDefaultDevice();
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
