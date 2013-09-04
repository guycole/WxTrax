package com.digiburo.example.wxtrax.lib.utility;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;

import com.digiburo.example.wxtrax.lib.service.AlarmReceiver;
import com.digiburo.example.wxtrax.lib.service.CollectionService;

/**
 * WxTrax application
 *
 * @author gsc
 */
public class WxTraxApplication extends Application {
  
  /**
   * perform application startup chores
   */
  @Override
  public void onCreate() {
    if (Constants.DEBUG_APPLICATION_MODE) {
      Log.i(LOG_TAG, "----application start w/debug mode true:" + _startTime.toString());
    } else {
      Log.i(LOG_TAG, "----application start w/debug mode false:" + _startTime.toString());
    } 
  
    //ensure at least default user preference database
    if (_uph.isEmptyPreferences(this)) {
      _uph.writeDefaults(this);
    }
    
    //test
//    Intent intent = new Intent(this, CollectionService.class);
//    intent.putExtra(Constants.INTENT_KEY_CALLSIGN, "KRDD");
//    startService(intent);
    
    //check for weather updates
    //startService(new Intent(this, CollectionService.class));
    
    //schedule next weather update
    setAlarm();
  }

  @Override
  public void onLowMemory() {
    LogFacade.info(LOG_TAG, "low memory");
  }
  
  @Override
  public void onTerminate() {
    LogFacade.info(LOG_TAG, "terminate");  
  }
  
  // define next collection alarm
  public void setAlarm() {
    long delay = _uph.getPollInterval(this);
    LogFacade.debug(LOG_TAG, "setAlarm delay:" + delay);

    Time timeNow = Utility.timeNow();    
    Time timeAlarm = new Time();
    timeAlarm.set(timeNow.toMillis(Constants.IGNORE_DST) + delay);
    
    Intent ii = new Intent(this, AlarmReceiver.class);
    PendingIntent pi = PendingIntent.getBroadcast(this, 0, ii, 0);
    
    AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    am.set(AlarmManager.RTC, timeAlarm.toMillis(Constants.IGNORE_DST), pi);
    
    LogFacade.debug(LOG_TAG, "next alarm scheduled:" + timeAlarm);
  }

  //
  private Time _startTime = Utility.timeNow();

  //
  private UserPreferenceHelper _uph = new UserPreferenceHelper();
  
  //
  public final String LOG_TAG = getClass().getName();
}

/*
 * Copyright 2011 Digital Burro, INC
 * Created on Jun 12, 2011 by gsc
 */