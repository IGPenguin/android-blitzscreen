package com.intergalacticpenguin;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Runtime rt = Runtime.getRuntime();
        String[] commands = {getAndroidHome() + "/platform-tools/adb", "devices"};
        Process process = rt.exec(commands);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String s;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        // read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
    }

    private static String getAndroidHome() {
        String path;
        if (System.getenv("ANDROID_HOME")!=null) {
            path=System.getenv("ANDROID_HOME");
        }
        else{
            throw new RuntimeException("Enviroment variable \"ANDROID_HOME\" not set, adb not found");
        }
        return path;
    }
}
