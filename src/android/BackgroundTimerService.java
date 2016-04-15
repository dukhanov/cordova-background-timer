package com.skycom.cordova.bgt;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.session.MediaController;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;

public class BackgroundTimerService extends Service {
    private static final String TAG = "BGTimer";
    private static final int NOTIFICATION_ID = -344455967;

    private SharedPreferences mSettings;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean timerStarted = false;

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i(TAG, "onStartCommand");

        mSettings = getApplicationContext().getSharedPreferences(TAG, 0);
        PluginSettings.initialize(mSettings);

        if(PluginSettings.isEnabled()) {
            Log.i(TAG, "startTimer");
            if(intent != null && intent.getExtras() != null && intent.getExtras().getBoolean("startOnBoot", false)){
                this.forceActivityStart();
            }
            this.startTimer(PluginSettings.getTimerInterval());
        }else{
            Log.i(TAG, "stopTimer");
            this.stopTimer();
        }

        return PluginSettings.isStopOnTerminate() ? START_NOT_STICKY :START_STICKY;
    }

    private void startTimer(int timerInterval) {
        if(this.timerStarted){
            this.stopTimer();
        }

        if(PluginSettings.isForeground()) {
            startForeground(NOTIFICATION_ID, this.getNotification());
        }

        this.mTimer = new Timer(TAG);
        this.mTimerTask = getTimerTask();
        this.mTimer.schedule(this.mTimerTask, timerInterval, timerInterval);

        this.timerStarted = true;
    }

    private void stopTimer() {
        if(mTimer != null) {
            try {
                mTimer.cancel();
                mTimer = null;
            } catch (Exception ex) {
                Log.i(TAG, "exception has occurred - " + ex.getMessage());
            }
        }

        if(mTimerTask != null) {
            try {
                mTimerTask.cancel();
                mTimerTask = null;
            } catch (Exception ex) {
                Log.i(TAG, "exception has occurred - " + ex.getMessage());
            }
        }

        if(PluginSettings.isForeground()) {
            stopForeground(true);
        }

        this.timerStarted = false;
    }

    private TimerTask getTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                forceActivityStart();
                EventBus bus = EventBus.getDefault();
                if(bus.hasSubscriberForEvent(TimerEvent.class)) {
                    bus.post(new TimerEvent());
                } else {
                    Log.i(TAG, "no sibscribers for event");
                }
            }
        };
    }

    private void forceActivityStart() {
        Log.i(TAG, "forceActivityStart");
        if(PluginSettings.isActivityStarted()){
            Log.i(TAG, "activity already started");
            return;
        }

        Intent intent = getPackageManager().getLaunchIntentForPackage(getApplicationContext().getPackageName());
        // hide an app after reloading
        intent.putExtra("forceReload", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_USER_ACTION | Intent.FLAG_FROM_BACKGROUND);
        startActivity(intent);
    }

    private Notification getNotification() {
        Context context = getApplicationContext();
        PackageManager pm = getPackageManager();
        ApplicationInfo info = context.getApplicationInfo();

        Intent launchIntent = pm.getLaunchIntentForPackage(context.getPackageName());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent,  PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                                                .setContentIntent(pendingIntent)
                                                .setContentTitle(PluginSettings.getNotificationTitle())
                                                .setContentText(PluginSettings.getNotificationText())
                                                .setSmallIcon(info.icon);

        return builder.build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        cleanup();
    }

    private void cleanup() {
        Log.i(TAG, "cleanup");
        this.stopTimer();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public boolean onUnbind(Intent intent){
        Log.i(TAG, "onUnbind");
        return super.onUnbind(intent);
    }
}
