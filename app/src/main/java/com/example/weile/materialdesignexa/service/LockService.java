package com.example.weile.materialdesignexa.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.weile.materialdesignexa.activity.LockActivity;

/**
 * Created by weile on 2016/12/2.
 */
public class LockService extends Service {
    private ScreenOnReceiver mScreenOnReceiver;
    private ScreenOffReceiver mScreenOffReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        mScreenOnReceiver=new ScreenOnReceiver();
        IntentFilter screenonFilter=new IntentFilter();
        screenonFilter.addAction("android.intent.action.SCREEN_ON");
        registerReceiver(mScreenOnReceiver,screenonFilter);

        mScreenOffReceiver=new ScreenOffReceiver();
        IntentFilter screenOffinfilter=new IntentFilter();
        screenOffinfilter.addAction("android.intent.action.SCREEN_OFF");
        registerReceiver(mScreenOffReceiver,screenOffinfilter);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    private class ScreenOnReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e("ee","ee");
        }
    }
    private class ScreenOffReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String content=intent.getAction();
            if(content.equals("android.intent.action.SCREEN_OFF")){
                /*KeyguardManager  keyguardManager = (KeyguardManager)context.getSystemService(context.KEYGUARD_SERVICE);
                KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("");
                keyguardLock.disableKeyguard();//解锁系统锁屏*/
                Intent lockintent=new Intent(context, LockActivity.class);
                lockintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(lockintent);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mScreenOffReceiver);
        unregisterReceiver(mScreenOnReceiver);
    }
}
