import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class DataManager {
    private static DataManager dataManager = null;
    private List<String> adbDevicesList;
    private int defaultAdbDevice;
    private boolean mac = false;

    private DataManager() {
    }

    private static DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
            updateAdbDeviceList();
            getInstance().defaultAdbDevice = -1;
            if (System.getProperty("os.name").toLowerCase().contains("mac") || System.getProperty("os.name").toLowerCase().contains("os x")) {
                getInstance().mac = true;
            }

        }
        return dataManager;
    }

    static String getOutputPath(String fileName) {
        return System.getenv("HOME") + "/Desktop/" + fileName;
    }

    static List<String> getAdbDeviceList() {
        return getInstance().adbDevicesList;
    }

    static void updateAdbDeviceList() {
        List<String> deviceList = new ArrayList<String>();
        try {
            Process process = Runtime.getRuntime().exec(AndroidCommand.getAdbPath() + "adb" + " devices");
            String line;

            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (line.endsWith("device")) {
                    deviceList.add(line.split("\\t")[0]);
                }
            }
        } catch (IOException ex) {
            System.out.println("Getting adb devices failed");
            ex.printStackTrace();
        }
        getInstance().adbDevicesList = deviceList;

        if (deviceList.isEmpty()) {
            setDefaultAdbDevice(-1);
            System.out.println("No device connected, default device unset");
            GraphicOutput.showMacNotification("No device connected, default device unset");
        } else {
            if (getDefaultAdbDevice() == (-1)) {
                setDefaultAdbDevice(0);
            }
        }
    }

    static boolean isThisMac() {
        return getInstance().mac;
    }

    static void setDefaultAdbDevice(int deviceIndex) {
        getInstance().defaultAdbDevice = deviceIndex;
    }

    static int getDefaultAdbDevice() {
        return getInstance().defaultAdbDevice;
    }

}
