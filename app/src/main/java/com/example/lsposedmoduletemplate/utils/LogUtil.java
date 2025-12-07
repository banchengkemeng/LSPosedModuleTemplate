package com.example.lsposedmoduletemplate.utils;

import android.util.Log;

import de.robv.android.xposed.XposedBridge;

/**
 * 日志工具类
 */

public class LogUtil {

    private final String logTag;

    public LogUtil(String prefix) {
        this.logTag = prefix + "_LSP_TAG";
    }

    public void debug(String tag, String msg) {
        Log.d(tag, msg);
        XposedBridge.log(String.format("%s %s", tag, msg));
    }

    public void debug(String msg) {
        debug(logTag, msg);
    }

    public void info(String tag, String msg) {
        Log.i(tag, msg);
        XposedBridge.log(String.format("%s %s", tag, msg));
    }

    public void info(String msg) {
        info(logTag, msg);
    }

    public void error(String tag, String msg) {
        Log.e(tag, msg);
        XposedBridge.log(String.format("[%s] %s %s", "ERROR", tag, msg));
    }

    public void error(String msg) {
        error(logTag, msg);
    }

    public void error(String tag, String msg, Throwable e) {
        Log.e(tag, msg, e);
        XposedBridge.log(String.format("[%s] %s %s", "ERROR", tag, msg));
        XposedBridge.log(e);
    }

    public void error(String msg, Throwable e) {
        error(logTag, msg, e);
    }
}
