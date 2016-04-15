package com.skycom.cordova.bgt;

import android.content.SharedPreferences;

import org.json.JSONObject;

// singletog class for storing settings
public class PluginSettings {
    static SharedPreferences mPreferences;
    private static final int DEFAULT_TIMER_INTERVAL = 10*1000;
    private static final boolean DEFAULT_START_ON_BOOT = false;
    private static final boolean DEFAULT_STOP_ON_TERMINATE = true;
    private static final boolean DEFAULT_IS_FOREGROUND = true;
    private static final String DEFAULT_NOTIFICATION_TITLE = "";
    private static final String DEFAULT_NOTIFICATION_TEXT = "";

    private static boolean startOnBoot;
    private static boolean stopOnTerminate;
    private static boolean enabled;
    private static int timerInterval;
    private static boolean isActivityStarted;
    private static boolean isForeground;
    private static String notificationTitle;
    private static String notificationText;

    protected static void initialize(SharedPreferences preferences) {
        if(preferences == null){
            return;
        }
        mPreferences = preferences;

        boolean startOnBoot = preferences.getBoolean("startOnBoot", DEFAULT_START_ON_BOOT);
        boolean stopOnTerminate = preferences.getBoolean("stopOnTerminate", DEFAULT_STOP_ON_TERMINATE);
        int timerInterval = preferences.getInt("timerInterval", DEFAULT_TIMER_INTERVAL);
        boolean isForeground = preferences.getBoolean("isForeground", DEFAULT_IS_FOREGROUND);
        String notificationTitle = preferences.getString("notificationTitle", DEFAULT_NOTIFICATION_TITLE);
        String notificationText = preferences.getString("notificationText", DEFAULT_NOTIFICATION_TEXT);

        boolean enabled = preferences.getBoolean("enabled", false);
        boolean isActivityStarted = preferences.getBoolean("isActivityStarted", false);

        setStartOnBoot(startOnBoot);
        setStopOnTerminate(stopOnTerminate);
        setTimerInterval(timerInterval);
        setEnabled(enabled);
        setActivityStarted(isActivityStarted);
        setIsForeground(isForeground);
        setNotificationTitle(notificationTitle);
        setNotificationText(notificationText);
    }

    protected static void setConfig(JSONObject config) {
        if(config == null || mPreferences == null){
            return;
        }

        boolean startOnBoot = config.optBoolean("startOnBoot", DEFAULT_START_ON_BOOT);
        boolean stopOnTerminate = config.optBoolean("stopOnTerminate", DEFAULT_STOP_ON_TERMINATE);
        int timerInterval = config.optInt("timerInterval", DEFAULT_TIMER_INTERVAL);
        boolean isForeground = config.optBoolean("isForeground", DEFAULT_IS_FOREGROUND);
        String notificationTitle = config.optString("notificationTitle", DEFAULT_NOTIFICATION_TITLE);
        String notificationText = config.optString("notificationText", DEFAULT_NOTIFICATION_TEXT);

        setStartOnBoot(startOnBoot);
        setStopOnTerminate(stopOnTerminate);
        setTimerInterval(timerInterval);
        setIsForeground(isForeground);
        setNotificationTitle(notificationTitle);
        setNotificationText(notificationText);
    }

    private static void saveBoolean(String name, boolean value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    private static void saveInt(String name, int value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(name, value);
        editor.apply();
    }

    private static void saveString(String name, String value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static boolean isStartOnBoot() {
        return startOnBoot;
    }

    public static void setStartOnBoot(boolean value) {
        startOnBoot = value;
        saveBoolean("startOnBoot", value);
    }

    public static boolean isStopOnTerminate() {
        return stopOnTerminate;
    }

    public static void setStopOnTerminate(boolean value) {
        stopOnTerminate = value;
        saveBoolean("stopOnTerminate", value);
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;
        saveBoolean("enabled", value);
    }

    public static boolean isActivityStarted() {
        return isActivityStarted;
    }

    public static void setActivityStarted(boolean value) {
        isActivityStarted = value;
        saveBoolean("isActivityStarted", value);
    }

    public static int getTimerInterval() {
        return timerInterval;
    }

    public static void setTimerInterval(int value) {
        timerInterval = value;
        saveInt("timerInterval", value);
    }

    public static boolean isForeground() {
        return isForeground;
    }

    public static void setIsForeground(boolean value) {
        isForeground = value;
        saveBoolean("isForeground", value);
    }

    public static String getNotificationTitle() {
        return notificationTitle;
    }

    public static void setNotificationTitle(String value) {
        notificationTitle = value;
        saveString("notificationTitle", value);
    }

    public static String getNotificationText() {
        return notificationText;
    }

    public static void setNotificationText(String value) {
        notificationText = value;
        saveString("notificationText", value);
    }
}
