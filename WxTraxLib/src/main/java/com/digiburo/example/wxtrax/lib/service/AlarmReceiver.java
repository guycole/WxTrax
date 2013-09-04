package com.digiburo.example.wxtrax.lib.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.digiburo.example.wxtrax.lib.utility.LogFacade;
import com.digiburo.example.wxtrax.lib.utility.WxTraxApplication;

/**
 * Service alarm to start weather collection
 *
 * @author gsc
 */
public class AlarmReceiver extends BroadcastReceiver {

  /* (non-Javadoc)
   * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
   */
  @Override
  public void onReceive(Context context, Intent intent) {
    LogFacade.entry(LOG_TAG, "onReceive");
    
    //start collection
    context.startService(new Intent(context, CollectionService.class));
    
    //set next alarm
    WxTraxApplication wta = (WxTraxApplication) context.getApplicationContext();
    wta.setAlarm();
  }
  
  //
  public final String LOG_TAG = getClass().getName();
}

/*
 * Copyright 2011 Digital Burro, INC
 * Created on Jun 17, 2011 by gsc
 */