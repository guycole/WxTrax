package com.digiburo.wxtrax.appwidget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.digiburo.wxtrax.R;
import com.digiburo.wxtrax.ui.MainActivity;
import com.digiburo.wxtraxlib.db.DataBaseFacade;
import com.digiburo.wxtraxlib.db.ObservationModel;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 */
public class TemperatureUpdateService extends Service implements Runnable {
  private AppWidgetManager awm;
  private Queue<Integer> appWidgetQueue = new LinkedList<Integer>();

  @Override
  public void onCreate() {
    super.onCreate();
    awm = AppWidgetManager.getInstance(this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  @Override
  public void onStart(Intent intent, int startId) {
    super.onStart(intent, startId);

    int[] appWidgetIds = awm.getAppWidgetIds(new ComponentName(this, TemperatureWidgetProvider.class));
    for (int appWidgetId:appWidgetIds) {
      appWidgetQueue.add(appWidgetId);
    }

    new Thread(this).start();

    stopSelf();
  }

  @Override
  public void run() {
    DataBaseFacade dbf = new DataBaseFacade(this);
    ObservationModel observation = dbf.getLatestFaveObservation();
    CharSequence temperatureString = parseTemperature(observation);

    while (!appWidgetQueue.isEmpty()) {
      int widgetId = appWidgetQueue.poll();

      Intent intent = new Intent(this, MainActivity.class);
      PendingIntent pending = PendingIntent.getActivity(this, 0, intent, 0);

      RemoteViews remoteView = new RemoteViews(this.getPackageName(), R.layout.widget_temperature);
      remoteView.setTextViewText(R.id.temperature, temperatureString);
      remoteView.setOnClickPendingIntent(R.id.widget_temperature, pending);

      awm.updateAppWidget(widgetId, remoteView);
    }
  }

  /**
   * @return extracted temperature value
   */
  private String parseTemperature(ObservationModel obs) {
    String result = "??";

    if (obs != null) {
      String[] temp1 = obs.getTemperature().split("\\.");
      result = temp1[0].trim();
    }

    return(result);
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
