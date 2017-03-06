public class Main {
    private static final String VERSION = "0.1";

    public static void main(String[] args) {
        System.out.println("Easy device screen capture " + VERSION + "\n");
        System.out.println("Device list:" + DeviceManager.getAdbDeviceList());
        AndroidCommand.takeScreenshot(0, "sample");
    }
}
