/*
 * Created by huongnd2 on 3/23/23 7:14 PM
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 3/23/23 7:14 PM
 */

package com.android.ipchecker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.ipchecker.core.App;

import java.util.Set;
import java.util.concurrent.LinkedTransferQueue;

public class Settings {
    private static final String TAG = "Settings";

    private final static String KEY_PREFERENCES = "com.android.ipchecker_preferences";


    public static final String KEY_ENTRY_APP_COUNT = "entry_app_count";
    public static final String KEY_RATE_US = "rate_us_already";

    public static final String KEY_COUNTRY = "country";
    public static final String KEY_COUNTRY_CODE = "country_code";

    public static void setSettings(String key, Object value) {
        if (value instanceof Integer) {
            setSettings(key, (int) value);
        } else if (value instanceof Long) {
            setSettings(key, (long) value);
        } else if (value instanceof String) {
            setSettings(key, (String) value);
        } else if (value instanceof Boolean) {
            setSettings(key, (Boolean) value);
        }
    }


    public static void setSettings(String key, int value) {
        SharedPreferences pref = App.self().getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = pref.edit();
        e.putString(key, String.valueOf(value));
        e.apply();
    }

    public static void setSettings(String key, long value) {
        SharedPreferences pref = App.self().getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = pref.edit();
        e.putLong(key, value);
        e.apply();
    }

    public static void setSettings(String key, Set<String> value) {
        SharedPreferences pref = App.self().getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = pref.edit();
        e.putStringSet(key, value);
        e.apply();
    }

    public static void setSettings(String key, String text) {
        SharedPreferences pref = App.self().getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = pref.edit();
        e.putString(key, text);
        e.apply();
    }

    public static void setSettings(String key, boolean bool) {
        SharedPreferences pref = App.self().getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = pref.edit();
        e.putBoolean(key, bool);
        e.apply();
    }

    public static Set<String> getListSettings(String key) {
        SharedPreferences pref = App.self().getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        return pref.getStringSet(key, null);
    }

    public static boolean getBooleanSettings(String key) {
        SharedPreferences pref = App.self().getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }

    public static boolean getBooleanSettings(String key, boolean defaultValue) {
        SharedPreferences pref = App.self().getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        return pref.getBoolean(key, defaultValue);
    }

    public static String getStringSettings(String key) {
        SharedPreferences pref = App.self().getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        return pref.getString(key, "");
    }

    public static String getStringSettings(String key, String defaultValue) {
        SharedPreferences pref = App.self().getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        return pref.getString(key, defaultValue);
    }

    public static int getIntSettings(String key, int defaultValue) {
        SharedPreferences pref = App.self().getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        return Integer.valueOf(pref.getString(key, String.valueOf(defaultValue)));
    }

    public static long getLongSettings(String key, long defaultValue) {
        SharedPreferences pref = App.self().getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        return pref.getLong(key, defaultValue);
    }


    public static void removeSettings(String key) {
        try {
            SharedPreferences pref = App.self().getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor e = pref.edit();
            e.remove(key);
            e.apply();
        } catch (Exception e) {}
    }
}

