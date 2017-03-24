import java.io.IOException;
import java.util.Calendar;

class AdbCommandDispatcher {

    static boolean recordingAvailable() throws IOException {
        String availabilityCheckOutput = new AdbCommand(DataManager.getDefaultAdbDeviceIndex(), "shell ls system/bin/screenrecord").execute().getOutput();

        if (availabilityCheckOutput.equals("system/bin/screenrecord")) {
            return true;
        } else {
            Reporter.report("Device " + DataManager.getAdbDeviceId(DataManager.getDefaultAdbDeviceIndex()) + " does not support recording");
            return false;
        }
    }

    static void takeScreenshot(int deviceIndex, String fileName) throws IOException {
        String outputPath = DataManager.getOutputPath(fileName) + ".png";

        Reporter.report("Saving screenshot from " + DataManager.getAdbDeviceId(deviceIndex), "to " + outputPath);

        new AdbCommand(deviceIndex, "shell screencap -p /mnt/sdcard/screenshot.png").execute();
        new AdbCommand(deviceIndex, "pull /mnt/sdcard/screenshot.png " + DataManager.getOutputPath(fileName) + ".png").execute();
        new AdbCommand(deviceIndex, "shell rm /mnt/sdcard/screenshot.png").execute();
    }

    static void takeScreenshot(int deviceIndex) throws IOException {
        String fileName = Calendar.getInstance().getTime().toString().replaceAll("\\s", "-").replaceAll(":", "-");
        takeScreenshot(deviceIndex, fileName);
    }

    static void startRecordingScreen(int deviceIndex) throws IOException {
        Reporter.report("Started recording screen on " + DataManager.getAdbDeviceId(deviceIndex));
        new AdbCommand(deviceIndex, "shell screenrecord --verbose /mnt/sdcard/recording.mp4", false).execute();
    }

    static void stopRecordingScreen(int deviceIndex, String fileName) throws IOException {String outputPath = DataManager.getOutputPath(fileName) + ".mp4";
        String pidString;

        Reporter.report("Stopping recording on " + DataManager.getAdbDeviceId(deviceIndex));

        pidString = new AdbCommand(deviceIndex, "shell top -d 0 -n 1 | grep screenrecord ").execute().getOutput();
        pidString = pidString.replaceFirst(" ", "");
        pidString = pidString.substring(0, pidString.indexOf(" "));

        new AdbCommand(deviceIndex, "shell kill -SIGINT " + pidString).execute();
        while(!new AdbCommand(deviceIndex, "shell top -n 1|grep screenrecord").execute().getOutput().isEmpty()){
            // Waiting for recording to stop
        }
        Reporter.report("Saving recording from " + DataManager.getAdbDeviceId(deviceIndex), "to " + outputPath);
        new AdbCommand(deviceIndex, "pull /mnt/sdcard/recording.mp4 " + outputPath).execute();
        new AdbCommand(deviceIndex, "shell rm /mnt/sdcard/recording.mp4").execute();
    }

    static void stopRecordingScreen(int deviceIndex) throws IOException {
        String fileName = Calendar.getInstance().getTime().toString().replaceAll("\\s", "-").replaceAll(":", "-");
        stopRecordingScreen(deviceIndex, fileName);
    }

}
