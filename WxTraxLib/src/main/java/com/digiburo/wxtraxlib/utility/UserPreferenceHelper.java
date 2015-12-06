package com.digiburo.wxtraxlib.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Map;

public class UserPreferenceHelper {

  //interval in milliseconds between network activity
  public static final String USER_PREF_KEY_POLL_INTERVAL = "pollInterval";

  //favorite weather station
  public static final String USER_PREF_KEY_FAVE_STATION = "faveStation";

  //maximum database row population
  public static final String USER_PREF_KEY_MAX_ROW_POP = "maxRowPop";

  //enable audio cue
  public static final String USER_PREF_KEY_AUDIO_ENABLE = "audioCheckBox";

  //
  public static final String DEFAULT_POLL = "30";
  public static final long DEFAULT_STATION_ID = -1;

  /**
   *
   * @param context
   */
  public void writeDefaults(Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sp.edit();

    editor.putString(USER_PREF_KEY_POLL_INTERVAL, DEFAULT_POLL);
    editor.putLong(USER_PREF_KEY_FAVE_STATION, DEFAULT_STATION_ID);
    editor.putBoolean(USER_PREF_KEY_AUDIO_ENABLE, true);

    editor.commit();
  }

  /**
   * Could only be true on a fresh install
   * @param context
   * @return true if user preferences are empty
   */
  public boolean isEmptyPreferences(Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    Map<String, ?> map = sp.getAll();
    return map.isEmpty();
  }

  /**
   * return poll time
   * @param context
   * @return poll time in minutes
   */
  public int getPollInterval(Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

    //store as string to play nice w/PreferenceFragment
    return Integer.parseInt(sp.getString(USER_PREF_KEY_POLL_INTERVAL, DEFAULT_POLL));
  }

  /**
   * define poll time
   * @param context
   * @param arg next poll time in minutes
   */
  public void setPollInterval(Context context, int arg) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

    //store as string to play nice w/PreferenceFragment
    SharedPreferences.Editor editor = sp.edit();
    editor.putString(USER_PREF_KEY_POLL_INTERVAL, Integer.toString(arg));
    editor.commit();
  }

  /**
   * return favorite station
   * @param context
   * @return favorite station as row key
   */
  public long getFaveStation(Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    return sp.getLong(USER_PREF_KEY_FAVE_STATION, DEFAULT_STATION_ID);
  }

  /**
   * define favorite station
   * @param context
   * @param arg favorite station row key
   */
  public void setFaveStation(Context context, long arg) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sp.edit();
    editor.putLong(USER_PREF_KEY_FAVE_STATION, arg);
    editor.commit();
  }

  /**
   *
   * @param context
   * @return
   */
  public boolean isAudioCue(Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    return sp.getBoolean(USER_PREF_KEY_AUDIO_ENABLE, true);
  }
}
