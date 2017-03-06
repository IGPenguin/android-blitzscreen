import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DeviceManager {
    private static DeviceManager deviceManager = null;
    private List<String> deviceList;

    private DeviceManager() {
    }

    private static DeviceManager getInstance() {
        if (deviceManager == null) {
            deviceManager = new DeviceManager();
            updateAdbDeviceList();
        }
        return deviceManager;
    }

    public static List<String> getAdbDeviceList() {
        return getInstance().deviceList;
    }

    public static void updateAdbDeviceList() {
        List<String> deviceList = new ArrayList<String>();
        try {
            Process process = Runtime.getRuntime().exec(AndroidCommand.getAndroidHome() + "/platform-tools/adb" + " devices");
            String line;

            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (line.endsWith("device")) {
                    deviceList.add(line.split("\\t")[0]);
                }
            }
        } catch (IOException ex) {
            System.out.println("Getting connected devices failed");
            ex.printStackTrace();
        }
        if (!deviceList.isEmpty()) {
            getInstance().deviceList = deviceList;
        } else {
            throw new RuntimeException("No devices detected");
        }
    }
}
