package com.digiburo.example.wxtrax.lib.utility;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * convenience routines to manage user preference database
 *
 * @author gsc
 */
public class UserPreferenceHelper {

  /**
   * 
   * @param context
   */
  public void writeDefaults(Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sp.edit();
    
    editor.putLong(USER_PREF_KEY_POLL_INTERVAL, Constants.DEFAULT_POLL);
    editor.putLong(USER_PREF_KEY_FAVE_STATION, Constants.DEFAULT_STATION_ID);
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
    return(map.isEmpty());
  }

  /**
   * return poll time
   * @param context
   * @return poll time in milliseconds
   */
  public long getPollInterval(Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    return(sp.getLong(USER_PREF_KEY_POLL_INTERVAL, Constants.DEFAULT_POLL));
  }
  
  /**
   * define poll time
   * @param context
   * @param arg next poll time in milliseconds
   */
  public void setPollInterval(Context context, long arg) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sp.edit();
    editor.putLong(USER_PREF_KEY_POLL_INTERVAL, arg);
    editor.commit();
  }

  /**
   * return favorite station
   * @param context
   * @return favorite station as row key
   */
  public long getFaveStation(Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    return(sp.getLong(USER_PREF_KEY_FAVE_STATION, Constants.DEFAULT_STATION_ID));
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
  public boolean getAudioCue(Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    return(sp.getBoolean(USER_PREF_KEY_AUDIO_ENABLE, true));
  }
  
  //interval in milliseconds between network activity
  public static final String USER_PREF_KEY_POLL_INTERVAL = "pollInterval";
  
  //favorite weather station
  public static final String USER_PREF_KEY_FAVE_STATION = "faveStation";
  
  //maximum database row population
  public static final String USER_PREF_KEY_MAX_ROW_POP = "maxRowPop";
  
  //enable audio cue
  public static final String USER_PREF_KEY_AUDIO_ENABLE = "audioCheckBox";
}

/*
 * Copyright 2011 Digital Burro, INC
 * Created on Apr 11, 2011 by gsc
 */