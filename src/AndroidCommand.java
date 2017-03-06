import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

final class AndroidCommand {

    public static String getAndroidHome() {
        String path;
        if (System.getenv("ANDROID_HOME") != null) {
            path = System.getenv("ANDROID_HOME");
        } else {
            throw new RuntimeException("Enviroment variable \"ANDROID_HOME\" not set, adb not found");
        }
        return path;
    }

    public static void executeAdb(int deviceIndex, String command) {
        try {
            String deviceId = DeviceManager.getAdbDeviceList().get(deviceIndex);
            String shellCommand = getAndroidHome() + "/platform-tools/adb -s " + deviceId + " " + command;
            Process process = Runtime.getRuntime().exec(shellCommand);
            process.waitFor();

//            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//
//            // read the output from the command
//            System.out.println("Here is the standard output of the command:");
//            String s;
//            while ((s = stdInput.readLine()) != null) {
//                System.out.println(s);
//            }
//
//            // read any errors from the attempted command
//            System.out.println("Here is the standard error of the command (if any):");
//            while ((s = stdError.readLine()) != null) {
//                System.out.println(s);
//            }

        } catch (IOException ex) {
            System.out.println("Executing adb command failed");
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            System.out.println("Waiting for adb execution interrupted");
            ex.printStackTrace();
        }
    }

    public static void takeScreenshot(int deviceIndex, String fileName) {
        String screenshotStoragePath = System.getenv("HOME") + "/Desktop/" + fileName + ".png";

        System.out.println("Saving screenshot from \"" + DeviceManager.getAdbDeviceList().get(deviceIndex) + "\" to " + screenshotStoragePath);
        executeAdb(deviceIndex, " shell screencap -p /mnt/sdcard/screenshot.png");
        executeAdb(deviceIndex, " pull /mnt/sdcard/screenshot.png " + screenshotStoragePath);
        executeAdb(deviceIndex, " shell rm /mnt/sdcard/screenshot.png");
    }

    public static void takeScreenshot(int deviceIndex) {
        String fileName = Calendar.getInstance().getTime().toString().replaceAll("\\s", "-").replaceAll(":", "-");
        takeScreenshot(deviceIndex, fileName);
    }

}
