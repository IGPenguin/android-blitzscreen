import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

class AndroidCommand {

    static String getAdbPath() {
        String path;
        if (System.getenv("ANDROID_HOME") != null) {
            path = System.getenv("ANDROID_HOME") + "platform-tools/";
        } else {
            throw new RuntimeException("Environment variable \"ANDROID_HOME\" not set, adb not found");
        }

        File adbFile = new File(path + "adb");
        if (!adbFile.exists() || adbFile.isDirectory()) {
            throw new RuntimeException("Adb not found in \"" + path + "\"");
        }

        return path;
    }

    static String executeAdb(int deviceIndex, String command, boolean waitForFinish) {
        StringBuilder output = new StringBuilder();
        try {
            String deviceId = DataManager.getAdbDeviceList().get(deviceIndex);
            String shellCommand = getAdbPath() + "adb -s " + deviceId + " " + command;
            Process process = Runtime.getRuntime().exec(shellCommand);
            if (waitForFinish) {
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s;
                while ((s = stdInput.readLine()) != null) {
                    output.append(s);
                }
                while ((s = stdError.readLine()) != null) {
                    output.append(s);
                }
            }
        } catch (IOException ex) {
            System.out.println("Executing adb command failed");
            ex.printStackTrace();
        }

        return output.toString();
    }

    static boolean recordingAvailable(int deviceIndex) {
        if (executeAdb(deviceIndex, "shell ls system/bin/screenrecord", true).equals("system/bin/screenrecord")) {
            return true;
        } else {
            GraphicOutput.showMacNotification("Device " + DataManager.getAdbDeviceList().get(DataManager.getDefaultAdbDevice()) + " does not support recording");
            return false;
        }
    }

    static void cycleDefaultAdbDevice() {
        DataManager.updateAdbDeviceList();
        if (DataManager.getDefaultAdbDevice() != (-1)) {
            int deviceCount = DataManager.getAdbDeviceList().size();
            if (DataManager.getDefaultAdbDevice() < (deviceCount - 1)) {
                DataManager.setDefaultAdbDevice(DataManager.getDefaultAdbDevice() + 1);
            } else {
                DataManager.setDefaultAdbDevice(0);
            }
            System.out.println("Device " + DataManager.getAdbDeviceList().get(DataManager.getDefaultAdbDevice()) + " set as default");
            GraphicOutput.showMacNotification("Device " + DataManager.getAdbDeviceList().get(DataManager.getDefaultAdbDevice()) + " set as default");
        }
    }

    static void takeScreenshot(int deviceIndex, String fileName) {
        String deviceName = DataManager.getAdbDeviceList().get(deviceIndex);
        String outputPath = DataManager.getOutputPath(fileName) + ".png";

        System.out.println("Saving screenshot from \"" + deviceName + "\" to " + outputPath);
        GraphicOutput.showMacNotification("Saving screenshot from " + deviceName, "to " + outputPath);
        executeAdb(deviceIndex, "shell screencap -p /mnt/sdcard/screenshot.png", true);
        executeAdb(deviceIndex, "pull /mnt/sdcard/screenshot.png " + DataManager.getOutputPath(fileName) + ".png", true);
        executeAdb(deviceIndex, "shell rm /mnt/sdcard/screenshot.png", true);
    }

    static void takeScreenshot(int deviceIndex) {
        String fileName = Calendar.getInstance().getTime().toString().replaceAll("\\s", "-").replaceAll(":", "-");
        takeScreenshot(deviceIndex, fileName);
    }

    static void startRecordingScreen(int deviceIndex) {
        String deviceName = DataManager.getAdbDeviceList().get(deviceIndex);

        System.out.println("Started recording screen on \"" + deviceName + "\"");
        GraphicOutput.showMacNotification("Started recording screen on " + deviceName);
        executeAdb(deviceIndex, "shell screenrecord --verbose /mnt/sdcard/recording.mp4", false);
    }

    static void stopRecordingScreen(int deviceIndex, String fileName) {
        String deviceName = DataManager.getAdbDeviceList().get(deviceIndex);
        String outputPath = DataManager.getOutputPath(fileName) + ".mp4";

        System.out.println("Stopping recording on \"" + deviceName + "\"");
        GraphicOutput.showMacNotification("Stopping recording on " + deviceName);
        executeAdb(deviceIndex, "shell pkill -SIGINT screenrecord", true);
        while (!executeAdb(deviceIndex, "shell top -n 1|grep screenrecord", true).isEmpty()) {
        }
        System.out.println("Saving recording from \"" + deviceName + "\" to " + outputPath);
        executeAdb(deviceIndex, "pull /mnt/sdcard/recording.mp4 " + outputPath, true);
        executeAdb(deviceIndex, "shell rm /mnt/sdcard/recording.mp4", true);
        System.out.println("Saved recording from \"" + deviceName + "\"");
        GraphicOutput.showMacNotification("Saved recording from " + deviceName, "to " + outputPath);

    }

    static void stopRecordingScreen(int deviceIndex) {
        String fileName = Calendar.getInstance().getTime().toString().replaceAll("\\s", "-").replaceAll(":", "-");
        stopRecordingScreen(deviceIndex, fileName);
    }

}
