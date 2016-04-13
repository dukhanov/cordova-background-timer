package com.skycom.cordova.bgt;

import android.content.SharedPreferences;

// singletog class for storing settings
public class PluginSettings {
    static SharedPreferences mPreferences;
    private static final int DEFAULT_TIMER_INTERVAL = 10*1000;
    private static final boolean DEFAULT_START_ON_BOOT = false;
    private static final boolean DEFAULT_STOP_ON_TERMINATE = true;

    private static boolean startOnBoot;
    private static boolean stopOnTerminate;
    private static boolean enabled;
    private static int timerInterval;
    private static boolean isActivityStarted;

    protected static void initialize(SharedPreferences preferences) {
        if(preferences == null){
            return;
        }
        mPreferences = preferences;

        boolean startOnBoot = preferences.getBoolean("startOnBoot", DEFAULT_START_ON_BOOT);
        boolean stopOnTerminate = preferences.getBoolean("stopOnTerminate", DEFAULT_STOP_ON_TERMINATE);
        int timerInterval = preferences.getInt("timerInterval", DEFAULT_TIMER_INTERVAL);
        boolean enabled = preferences.getBoolean("enabled", false);
        boolean isActivityStarted = preferences.getBoolean("isActivityStarted", false);

        setStartOnBoot(startOnBoot);
        setStopOnTerminate(stopOnTerminate);
        setTimerInterval(timerInterval);
        setEnabled(enabled);
        setActivityStarted(isActivityStarted);
    }

    public static boolean isStartOnBoot() {
        return startOnBoot;
    }

    public static void setStartOnBoot(boolean value) {
        startOnBoot = value;

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("startOnBoot", value);
        editor.apply();
    }

    public static boolean isStopOnTerminate() {
        return stopOnTerminate;
    }

    public static void setStopOnTerminate(boolean value) {
        stopOnTerminate = value;

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("stopOnTerminate", value);
        editor.apply();
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("enabled", value);
        editor.apply();
    }

    public static boolean isActivityStarted() {
        return isActivityStarted;
    }

    public static void setActivityStarted(boolean value) {
        isActivityStarted = value;

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("isActivityStarted", value);
        editor.apply();
    }

    public static int getTimerInterval() {
        return timerInterval;
    }

    public static void setTimerInterval(int value) {
        timerInterval = value;

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt("timerInterval", value);
        editor.apply();
    }
}
