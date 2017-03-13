# Android Blitzscreen

##What it does
This tool is for capturing screenshots and recording videos from Android devices using ADB straight to your desktop. Capturing is triggered by hotkeys listed below, which are implemented with [jnativehook](https://github.com/kwhat/jnativehook) library.

##Requirements
1. Linux or macOS with Java and Android Debug Bridge installed
    - On macOS, you are getting notifications about triggered actions ![notification](https://github.com/eidamsvoboda/android-blitzscreen/raw/master/images/notification.png)
2. Environment variable ANDROID_HOME set properly (it should point to your Android SDK directory)

##How to run
1. Download [android-blitzscreen.jar](https://github.com/eidamsvoboda/android-blitzscreen/raw/master/build/Android-Blitzscreen.jar) from build folder
2. Using terminal navigate to directory where you saved it and launch `java -jar android-blitzscreen.jar`
3. You may be prompted by system to change your accessibility settings, you must have enabled terminal in system accessibility settings to use this tool - so it can listen for hotkeys from anywhere

##Hotkeys
* Shift + Alt + A - take screenshot of all connected devices
* Shift + Alt + P - take screenshot of default device
* Shift + Alt + R - start/stop recording on default device
* Shift + Alt + D - change default device
