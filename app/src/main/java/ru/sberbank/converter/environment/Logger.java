package ru.sberbank.converter.environment;

import android.util.Log;

import ru.sberbank.converter.BuildConfig;

// TODO
public class Logger {
    public static void d(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(getCallerClassName(), message);
        }
    }

    public static void e(String message) {
        if (BuildConfig.DEBUG) {
            Log.e(getCallerClassName(), message);
        }
    }

    public static void i(String message) {
        if (BuildConfig.DEBUG) {
            Log.i(getCallerClassName(), message);
        }
    }

    public static void printStackTrace(Exception e) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace();
        }
    }

    private static String getCallerClassName() {
        return Thread.currentThread().getStackTrace()[1].getClassName();
    }
}