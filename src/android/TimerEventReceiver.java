package com.skycom.cordova.bgt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

public class TimerEventReceiver extends BroadcastReceiver {
    private static final String TAG = "BGTimer";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        PluginSettings.initialize(preferences);
        Log.i(TAG, "TIMER TICK");

        if (PluginSettings.isStopOnTerminate() || !PluginSettings.isEnabled()) return;

        EventBus bus = EventBus.getDefault();
        if (bus.hasSubscriberForEvent(TimerEvent.class)) {
            bus.post(new TimerEvent());
        } else {
            Log.i(TAG, "no sibscribers for event");
            ActivityHelper.forceActivityStart(context);
        }
    }
}

