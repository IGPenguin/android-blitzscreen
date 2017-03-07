import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager dataManager = null;
    private List<String> adbDevicesList;

    private DataManager() {
    }

    private static DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
            updateAdbDeviceList();
        }
        return dataManager;
    }

    public static List<String> getAdbDeviceList() {
        return getInstance().adbDevicesList;
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
            getInstance().adbDevicesList = deviceList;
        } else {
            throw new RuntimeException("No devices detected");
        }
    }
}
