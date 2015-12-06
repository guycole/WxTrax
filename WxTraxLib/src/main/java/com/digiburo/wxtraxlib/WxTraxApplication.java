package com.digiburo.wxtraxlib;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.digiburo.wxtraxlib.service.CollectionService;
import com.digiburo.wxtraxlib.utility.UserPreferenceHelper;

/**
 *
 */
public class WxTraxApplication extends Application {
    public final String LOG_TAG = getClass().getName();

    private UserPreferenceHelper uph = new UserPreferenceHelper();

    /**
     * perform application startup chores
     */
    @Override
    public void onCreate() {
        if (uph.isEmptyPreferences(this)) {
            uph.writeDefaults(this);
        }

        startService(new Intent(this, CollectionService.class));
    }

    @Override
    public void onLowMemory() {
        Log.i(LOG_TAG, "low memory");
    }

    @Override
    public void onTerminate() {
        Log.i(LOG_TAG, "terminate");
    }
}