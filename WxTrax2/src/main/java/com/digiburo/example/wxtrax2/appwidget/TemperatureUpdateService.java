package com.digiburo.example.wxtrax2.appwidget;

import java.util.LinkedList;
import java.util.Queue;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.digiburo.example.wxtrax.lib.content.DataBaseFacade;
import com.digiburo.example.wxtrax.lib.content.ObservationModel;
import com.digiburo.example.wxtrax.lib.utility.LogFacade;
import com.digiburo.example.wxtrax2.R;
import com.digiburo.example.wxtrax2.ui.MainActivity;

/**
 * update temperature appwidget
 *
 * @author gsc
 */
public class TemperatureUpdateService extends Service implements Runnable {

  @Override
  public void onCreate() {
    super.onCreate();
    LogFacade.entry(LOG_TAG, "onCreate");
    
    _awm = AppWidgetManager.getInstance(this);
  }
  
  @Override
  public void onDestroy() {
    super.onDestroy();
    LogFacade.entry(LOG_TAG, "onDestroy");
  }

  @Override
  public void onStart(Intent intent, int startId) {
    super.onStart(intent, startId);
    
    if (intent != null) {
      String action = intent.getAction();
      LogFacade.entry(LOG_TAG, "onStart:" + action);
    } else {
      LogFacade.entry(LOG_TAG, "onStart:null intent");
    }

    int[] appWidgetIds = _awm.getAppWidgetIds(new ComponentName(this, TemperatureWidgetProvider.class));
    for (int appWidgetId:appWidgetIds) {
      _appWidgetQueue.add(appWidgetId);
    }
    
    new Thread(this).start();
    
    stopSelf();
  }
 
  
  /* (non-Javadoc)
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run() {
    LogFacade.entry(LOG_TAG, "xxx xxx thread run xxx xxx");
    LogFacade.debug(LOG_TAG, "appWidgetQueue:" + _appWidgetQueue.size());

    DataBaseFacade dbf = new DataBaseFacade(this);
    ObservationModel observation = dbf.getLatestFaveObservation();
    CharSequence temperatureString = parseTemperature(observation);
    LogFacade.debug(LOG_TAG, "widget update:" + observation.getIdentifier() + ":" + observation.getTemperature());

    while (!_appWidgetQueue.isEmpty()) {
      int widgetId = _appWidgetQueue.poll();
      
      Intent intent = new Intent(this, MainActivity.class);
      PendingIntent pending = PendingIntent.getActivity(this, 0, intent, 0);
      
      RemoteViews remoteView = new RemoteViews(this.getPackageName(), R.layout.temperature_widget);
      remoteView.setTextViewText(R.id.widget_temperature01, temperatureString);
      remoteView.setOnClickPendingIntent(R.id.temperature_widget, pending);
      
      LogFacade.debug(LOG_TAG, "updateWidget:" + widgetId);
      
      _awm.updateAppWidget(widgetId, remoteView);
    }
  }

  /**
   * @return extracted temperature value
   */
  private String parseTemperature(ObservationModel obs) {
    String result = "??";
    
    if (obs != null) {
      LogFacade.debug(LOG_TAG, "==>" + obs.getTemperature() + "<==");
      String[] temp1 = obs.getTemperature().split("\\.");
      LogFacade.debug(LOG_TAG, "==>" + temp1.length + "<==");
      result = temp1[0].trim();
    }
    
    return(result);
  }

  /* (non-Javadoc)
   * @see android.app.Service#onBind(android.content.Intent)
   */
  @Override
  public IBinder onBind(Intent intent) {
    // TODO Auto-generated method stub
    return null;
  }
  
  //
  private AppWidgetManager _awm;
 
  //
  private Queue<Integer> _appWidgetQueue = new LinkedList<Integer>();
  
  //
  public final String LOG_TAG = getClass().getName();
}

/*
 * Copyright 2011 Digital Burro, INC
 * Created on Sep 23, 2011 by gsc
 */