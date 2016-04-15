package com.skycom.cordova.bgt;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class BackgroundTimer extends CordovaPlugin {
    private static final String TAG = "BGTimer";
    private static final String ACTION_START = "start";
    private static final String ACTION_STOP = "stop";

    private Intent mBackgroundServiceIntent;
    private SharedPreferences mSettings;
    private CallbackContext mTimerCallbackContext;

    @Override
    protected void pluginInitialize() {
        Activity activity = this.cordova.getActivity();
        mBackgroundServiceIntent = new Intent(activity, BackgroundTimerService.class);
        mSettings = this.cordova.getActivity().getSharedPreferences(TAG, 0);
        PluginSettings.initialize(mSettings);

        Intent launchIntent = activity.getIntent();
        if (launchIntent != null && launchIntent.hasExtra("forceReload")) {
            activity.moveTaskToBack(true);
        }

        EventBus.getDefault().register(this);

        Log.i(TAG, "pluginInitialize");
    }

    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        Log.d(TAG, "execute : " + action);

        if (ACTION_START.equalsIgnoreCase(action)) {
            this.start(data);

            this.mTimerCallbackContext = callbackContext;
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
            pluginResult.setKeepCallback(true);
            callbackContext.sendPluginResult(pluginResult);
        }else if (ACTION_STOP.equalsIgnoreCase(action)) {
            this.stop();

            callbackContext.success();
        }

        return true;
    }

    private void start(JSONArray data) {
        this.configure(data);
        cordova.getActivity().startService(mBackgroundServiceIntent);
    }

    private void configure(JSONArray data) {
        PluginSettings.setEnabled(true);
        PluginSettings.setActivityStarted(true);

        JSONObject config = null;
        try {
            config = data.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(config != null) {
            PluginSettings.setConfig(config);
        }
    }

    private void stop() {
        setEnabled(false);
        cordova.getActivity().stopService(mBackgroundServiceIntent);
    }

    private void setEnabled(boolean enabled) {
        PluginSettings.setEnabled(enabled);
    }

    @Subscribe
    public void onEventMainThread(TimerEvent event) {
        Log.i(TAG, "timer event has happened");

        if (mTimerCallbackContext != null) {
            PluginResult result = new PluginResult(PluginResult.Status.OK);
            result.setKeepCallback(true);
            mTimerCallbackContext.sendPluginResult(result);
        }
    }

    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        PluginSettings.setActivityStarted(false);
        Log.i(TAG, "isEnabled: " + PluginSettings.isEnabled());
        Log.i(TAG, "isStopOnTerminate: " + PluginSettings.isStopOnTerminate());
        if(PluginSettings.isEnabled() && PluginSettings.isStopOnTerminate()) {
            this.cordova.getActivity().stopService(mBackgroundServiceIntent);
        }

        EventBus.getDefault().unregister(this);
    }
}
