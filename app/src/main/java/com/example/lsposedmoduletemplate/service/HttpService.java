package com.example.lsposedmoduletemplate.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.lsposedmoduletemplate.WebServerManager;
import com.example.lsposedmoduletemplate.utils.IntentUtil;
import com.example.lsposedmoduletemplate.utils.LogUtil;

public class HttpService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return START_STICKY;
        }

        String host = intent.getStringExtra("extra_host");
        int port = intent.getIntExtra("extra_port", 2080);

        try {
            WebServerManager.startServer(this, host, port);
        } catch (Throwable e) {
            LogUtil.error(e);
            sendBroadcast(IntentUtil.errorIntent(e.getMessage()));
            return START_STICKY;
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        try {
            WebServerManager.stopServer();
        } catch (Exception e) {
            LogUtil.error(e);
            sendBroadcast(IntentUtil.errorIntent(e.getMessage()));
            super.onDestroy();
            return;
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
