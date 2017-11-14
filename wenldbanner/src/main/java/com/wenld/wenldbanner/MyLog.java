package com.wenld.wenldbanner;

import android.util.Log;

/**
 * Created by wenld on 2017/11/14.
 */

public class MyLog {
    static boolean logSwitch=true;
    public static int v(String tag, String msg) {
        if(!logSwitch)return 0;
        return Log.e(tag, msg);
    }

    public static int v(String tag, String msg, Throwable tr) {
        if(!logSwitch)return 0;
        return Log.v(tag, msg);
    }
    public static int d(String tag, String msg) {
        if(!logSwitch)return 0;
        return Log.d(tag, msg);
    }

    public static int i(String tag, String msg) {
        if(!logSwitch)return 0;
        return Log.i(tag, msg);
    }

    public static int e(String tag, String msg) {
        if(!logSwitch)return 0;
        return Log.e(tag, msg);
    }

    public static int w(String tag, String msg) {
        if(!logSwitch)return 0;
        return Log.w(tag, msg);
    }
    public static int w(String tag, String msg, Throwable tr) {
        if(!logSwitch)return 0;
        return Log.w(tag, msg);
    }
}
