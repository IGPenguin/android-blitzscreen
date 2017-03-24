class AdbCommand extends ShellCommand {

    AdbCommand(int deviceIndex, String command, boolean waitForFinish) {
        super(DataManager.getAdbLocation() + "adb -s " + DataManager.getAdbDeviceList().get(deviceIndex) + " " + command, waitForFinish);
    }

    AdbCommand(int deviceIndex, String command) {
        super(DataManager.getAdbLocation() + "adb -s " + DataManager.getAdbDeviceList().get(deviceIndex) + " " + command, true);
    }
}
