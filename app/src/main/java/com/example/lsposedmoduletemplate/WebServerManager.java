package com.example.lsposedmoduletemplate;

import android.content.Context;

import com.example.lsposedmoduletemplate.utils.IntentUtil;
import com.example.lsposedmoduletemplate.utils.LogUtil;
import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class WebServerManager {
    private static Server server;
    public static final String ACTION_SERVER_START_SUCCESS = "WEBSERVER_START_SUCCESS";
    public static final String ACTION_SERVER_STOP_SUCCESS = "WEBSERVER_STOP_SUCCESS";

    public static void initServer(Context context, String host, int port) throws UnknownHostException {
        server = AndServer.webServer(context)
                .inetAddress(InetAddress.getByName(host))
                .port(port)
                .listener(new Server.ServerListener() {
                    @Override
                    public void onStarted() {
                        LogUtil.info(String.format(
                                "Started HttpServer(%s:%s)",
                                host,
                                port
                        ));
                        context.sendBroadcast(IntentUtil.successIntent(WebServerManager.ACTION_SERVER_START_SUCCESS));
                    }

                    @Override
                    public void onStopped() {
                        LogUtil.info(String.format(
                                "Stopped HttpServer(%s:%s)",
                                host,
                                port
                        ));
                        context.sendBroadcast(IntentUtil.successIntent(WebServerManager.ACTION_SERVER_STOP_SUCCESS));
                    }

                    @Override
                    public void onException(Exception e) {
                        LogUtil.error(e);
                        context.sendBroadcast(IntentUtil.errorIntent(e.getMessage()));
                    }
                })
                .build();
    }

    public static void startServer(Context context, String host, int port) throws UnknownHostException {
        initServer(context, host, port);
        startServer();
    }

    public static void startServer() {
        if (server == null) {
            RuntimeException serverObjectIsNull = new RuntimeException("Server Object is null");
            LogUtil.error(serverObjectIsNull);
            throw serverObjectIsNull;
        }

        server.startup();
    }

    public static void stopServer() {
        if (server == null) {
            RuntimeException serverObjectIsNull = new RuntimeException("Server Object is null");
            LogUtil.error(serverObjectIsNull);
            throw serverObjectIsNull;
        }

        if (isRunning()) {
            server.shutdown();
        }
    }

    public static boolean isRunning() {
        if (server == null) {
            return false;
        }

        return server.isRunning();
    }
}
