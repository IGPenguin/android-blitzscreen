package com.intergalacticpenguin.androidblitzscreen.code;

import com.intergalacticpenguin.androidblitzscreen.ui.window.specific.SettingsWindow;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public static final String VERSION = "1.2";
    private static DataManager dataManager = null;
    private String adbLocation;
    private List<String> adbDevicesList;
    private int defaultAdbDeviceIndex;
    private boolean mac;
    private boolean recordingInProgress;
    private SettingsWindow settingsInstance;

    private DataManager() {
    }

    private static DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
            loadAdb();
            updateAdbDeviceList();
            getInstance().defaultAdbDeviceIndex = -1;
            getInstance().recordingInProgress = false;
            getInstance().settingsInstance = null;
            if (System.getProperty("os.name").toLowerCase().contains("mac") || System.getProperty("os.name").toLowerCase().contains("os x")) {
                getInstance().mac = true;
            }
        }
        return dataManager;
    }

    static String getOutputPath(String fileName) {
        return System.getenv("HOME") + "/Desktop/" + fileName;
    }

    private static void loadAdb() {
        String path;
        if (System.getenv("ANDROID_HOME") != null) {
            path = System.getenv("ANDROID_HOME") + "platform-tools/";
        } else {
            throw new RuntimeException("Environment variable \"ANDROID_HOME\" not set, adb not found");
        }
        File adbFile = new File(path + "adb");
        if (!adbFile.isFile()) {
            throw new RuntimeException("Adb not found in \"" + path + "\"");
        }
        getInstance().adbLocation = path;
    }

    static String getAdbLocation() {
        return getInstance().adbLocation;
    }

    static List<String> getAdbDeviceList() {
        return getInstance().adbDevicesList;
    }

    static void updateAdbDeviceList() {
        List<String> deviceList = new ArrayList<String>();
        try {
            Process process = Runtime.getRuntime().exec(getInstance().adbLocation + "adb" + " devices");
            String line;

            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (line.endsWith("device")) {
                    deviceList.add(line.split("\\t")[0]);
                }
            }
        } catch (IOException ex) {
            Reporter.report("Getting adb devices failed");
            ex.printStackTrace();
        }
        getInstance().adbDevicesList = deviceList;

        if (deviceList.isEmpty()) {
            setDefaultAdbDeviceIndex(-1);
            Reporter.report("No device connected, default device unset");
        } else {
            if (getDefaultAdbDeviceIndex() == (-1)) {
                setDefaultAdbDeviceIndex(0);
            }
        }
    }

    private static void setDefaultAdbDeviceIndex(int deviceIndex) {
        getInstance().defaultAdbDeviceIndex = deviceIndex;
    }

    static void cycleDefaultAdbDevice() {
        updateAdbDeviceList();
        if (getDefaultAdbDeviceIndex() != (-1)) {
            int deviceCount = getAdbDeviceList().size();
            if (getDefaultAdbDeviceIndex() < (deviceCount - 1)) {
                setDefaultAdbDeviceIndex(getDefaultAdbDeviceIndex() + 1);
            } else {
                setDefaultAdbDeviceIndex(0);
            }
            Reporter.report("Device " + getAdbDeviceList().get(getDefaultAdbDeviceIndex()) + " set as default");
        }
    }

    static int getDefaultAdbDeviceIndex() {
        return getInstance().defaultAdbDeviceIndex;
    }

    static String getAdbDeviceId(int deviceIndex) {
        return getAdbDeviceList().get(deviceIndex);
    }

    static boolean isThisMac() {
        return getInstance().mac;
    }

    static boolean isRecordingInProgress() {
        return getInstance().recordingInProgress;
    }

    static void setRecordingState(boolean recording) {
        getInstance().recordingInProgress = recording;
    }

    public static SettingsWindow getSettingsInstance() {
        return getInstance().settingsInstance;
    }

    public static void setSettingsInstance(SettingsWindow settingsWindow) {
        getInstance().settingsInstance = settingsWindow;
    }

}
