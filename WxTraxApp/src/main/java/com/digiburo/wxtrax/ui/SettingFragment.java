package com.digiburo.wxtrax.ui;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.digiburo.wxtrax.R;

/**
 * user preferences
 */
public class SettingFragment extends PreferenceFragment {
  public final String LOG_TAG = getClass().getName();

  /**
   * mandatory empty ctor
   */
  public SettingFragment() {
    //empty
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(LOG_TAG, "onCreate");
    addPreferencesFromResource(R.xml.menu_setting);
  }
}
