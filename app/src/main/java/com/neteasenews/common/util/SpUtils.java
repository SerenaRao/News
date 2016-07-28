package com.neteasenews.common.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.neteasenews.common.config.App;


public class SpUtils {

    public static String getString(String key, final String defaultValue) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        return sp.getString(key, defaultValue);
    }

    public static void putString(final String key, final String value) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        sp.edit().putString(key, value).apply();
    }

    public static boolean getBoolean(final String key,
                                     final boolean defaultValue) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        return sp.getBoolean(key, defaultValue);
    }

    public static boolean hasKey(final String key) {
        return PreferenceManager.getDefaultSharedPreferences(App.getAppContext()).contains(key);
    }

    public static void putBoolean(final String key, final boolean value) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        sp.edit().putBoolean(key, value).apply();
    }

    public static void putInt(final String key, final int value) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        sp.edit().putInt(key, value).apply();
    }

    public static int getInt(final String key, final int defaultValue) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        return sp.getInt(key, defaultValue);
    }

    public static void putFloat(final String key, final float value) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        sp.edit().putFloat(key, value).apply();
    }

    public static float getFloat(final String key, final float defaultValue) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        return sp.getFloat(key, defaultValue);
    }

    public static void putLong(final String key, final long value) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        sp.edit().putLong(key, value).apply();
    }

    public static long getLong(final String key, final long defaultValue) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        return sp.getLong(key, defaultValue);
    }

    public static void clearAll(final SharedPreferences p) {
        final SharedPreferences.Editor editor = p.edit();
        editor.clear();
        editor.apply();
    }
}
