import java.io.IOException;
import java.util.Calendar;

class AdbCommandDispatcher {

    private static boolean recordingAvailable() throws IOException {
        String availabilityCheckOutput = new AdbCommand(DataManager.getDefaultAdbDeviceIndex(), "shell ls system/bin/screenrecord").execute().getOutput();

        if (availabilityCheckOutput.equals("system/bin/screenrecord")) {
            return true;
        } else {
            Reporter.report("Device " + DataManager.getAdbDeviceId(DataManager.getDefaultAdbDeviceIndex()) + " does not support recording");
            return false;
        }
    }

    private static void takeScreenshot(int deviceIndex, String fileName) throws IOException {
        String outputPath = DataManager.getOutputPath(fileName) + ".png";

        Reporter.report("Saving screenshot from " + DataManager.getAdbDeviceId(deviceIndex), "to " + outputPath);

        new AdbCommand(deviceIndex, "shell screencap -p /mnt/sdcard/screenshot.png").execute();
        new AdbCommand(deviceIndex, "pull /mnt/sdcard/screenshot.png " + DataManager.getOutputPath(fileName) + ".png").execute();
        new AdbCommand(deviceIndex, "shell rm /mnt/sdcard/screenshot.png").execute();
    }

    private static void takeScreenshot(int deviceIndex) throws IOException {
        String fileName = Calendar.getInstance().getTime().toString().replaceAll("\\s", "-").replaceAll(":", "-");
        takeScreenshot(deviceIndex, fileName);
    }

    private static void startRecordingScreen(int deviceIndex) throws IOException {
        Reporter.report("Started recording screen on " + DataManager.getAdbDeviceId(deviceIndex));
        new AdbCommand(deviceIndex, "shell screenrecord --verbose /mnt/sdcard/recording.mp4", false).execute();
    }

    private static void stopRecordingScreen(int deviceIndex, String fileName) throws IOException {
        String outputPath = DataManager.getOutputPath(fileName) + ".mp4";
        Reporter.report("Stopping recording on " + DataManager.getAdbDeviceId(deviceIndex));

        String pidString = new AdbCommand(deviceIndex, "shell top -d 0 -n 1 | grep screenrecord ").execute().getOutput();
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

    private static void stopRecordingScreen(int deviceIndex) throws IOException {
        String fileName = Calendar.getInstance().getTime().toString().replaceAll("\\s", "-").replaceAll(":", "-");
        stopRecordingScreen(deviceIndex, fileName);
    }

    //TODO

    static void takeScreenshotOfDefaultDevice() throws IOException {
        DataManager.updateAdbDeviceList();
        if (DataManager.getDefaultAdbDeviceIndex() != (-1)) {
            takeScreenshot(DataManager.getDefaultAdbDeviceIndex());
        } else {
            Reporter.report("Cannot take screenshot, no device detected");
        }
    }

    static void takeScreenshotOfAllDevices() throws IOException {
        DataManager.updateAdbDeviceList();
        if (DataManager.getAdbDeviceList().size() > 0) {
            for (int i = 0; i < DataManager.getAdbDeviceList().size(); i++) {
                AdbCommandDispatcher.takeScreenshot(i);
            }
        } else {
            Reporter.report("Cannot take screenshot, no device detected");
        }
    }

    static void recordDefaultDevice() throws IOException {
        DataManager.updateAdbDeviceList();
        if (DataManager.getDefaultAdbDeviceIndex() != (-1)) {
            if (!DataManager.isRecordingInProgress() && recordingAvailable()) {
                startRecordingScreen(DataManager.getDefaultAdbDeviceIndex());
                DataManager.setRecordingState(true);
            } else if (DataManager.isRecordingInProgress()) {
                stopRecordingScreen(DataManager.getDefaultAdbDeviceIndex());
                DataManager.setRecordingState(false);
            }
        } else {
            Reporter.report("Cannot start recording, no device detected");
        }
    }

    static void cycleDefaultDevice() {
        if (!DataManager.isRecordingInProgress()) {
            DataManager.cycleDefaultAdbDevice();
        } else {
            Reporter.report("Cannot change default device while recording");
        }
    }

}
