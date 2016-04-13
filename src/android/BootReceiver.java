package com.skycom.cordova.bgt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.transistorsoft.locationmanager.BackgroundGeolocationService;

public class BootReceiver extends BroadcastReceiver {   
    private static final String TAG = "BGTimer";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = context.getSharedPreferences(TAG, 0);
        PluginSettings.initialize(preferences);

        if (!PluginSettings.isStartOnBoot() || !PluginSettings.isEnabled()) return;

        Intent launchIntent = new Intent(context, BackgroundTimerService.class);
        Bundle event = new Bundle();
        event.putBoolean("startOnBoot", PluginSettings.isStartOnBoot());
        launchIntent.putExtras(event);

        Log.i(TAG, "booting service after startup");
        context.startService(launchIntent);
    }
}

