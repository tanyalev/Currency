package ru.sberbank.converter.environment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

public class PreferencesManager {
    private static final String PREFERENCES_NAME = "converter";

    private Context context;
    private static SharedPreferences sharedPreferences;

    public PreferencesManager(Context context) {
        this.context = context;
    }

    public void putString(String key, String value) {
        getSharedPreferences().edit().putString(key, value).apply();
    }

    @Nullable
    public String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    private SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }
}