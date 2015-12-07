package com.digiburo.wxtrax.appwidget;

import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 *
 */
public class TemperatureWidgetProvider extends AppWidgetProvider {

  @Override
  public void onReceive(Context context, Intent intent) {
    super.onReceive(context, intent);
    context.startService(new Intent(context, TemperatureUpdateService.class));
  }
}