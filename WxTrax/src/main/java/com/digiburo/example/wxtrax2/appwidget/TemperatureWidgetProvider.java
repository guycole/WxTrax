package com.digiburo.example.wxtrax2.appwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import com.digiburo.example.wxtrax.lib.utility.LogFacade;

/**
 * display the current temperature of fave station
 *
 * @author gsc
 */
public class TemperatureWidgetProvider extends AppWidgetProvider {
  
  @Override
  public void onDeleted(Context context, int[] appWidgetIds) {
    super.onDeleted(context, appWidgetIds);
    LogFacade.entry(LOG_TAG, "onDeleted");
  }
  
  @Override
  public void onDisabled(Context context) {
    super.onDisabled(context);
    LogFacade.entry(LOG_TAG, "onDisabled");
  }
  
  @Override 
  public void onEnabled(Context context) {
    super.onEnabled(context);
    LogFacade.entry(LOG_TAG, "onEnabled");
  }
  
  @Override
  public void onReceive(Context context, Intent intent) {
    super.onReceive(context, intent);
    LogFacade.entry(LOG_TAG, "onReceive");
    context.startService(new Intent(context, TemperatureUpdateService.class));
  }
  
  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    super.onUpdate(context, appWidgetManager, appWidgetIds);
    LogFacade.entry(LOG_TAG, "onUpdate:" + appWidgetIds.length);
  }
  
  //
  public final String LOG_TAG = getClass().getName();
}

/*
 * Copyright 2011 Digital Burro, INC
 * Created on Sep 23, 2011 by gsc
 */