package com.digiburo.wxtraxlib.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.Time;
import android.util.Log;
import com.digiburo.wxtraxlib.Constant;
import com.digiburo.wxtraxlib.db.ObservationModel;
/*
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
*/
import java.util.Set;
import java.util.concurrent.Executors;

public class WxXmlCollection {
  public final String LOG_TAG = getClass().getName();

  /**
   * Collect a NWS XML observation and return it as key/value pairs
   * @param target weather station identifier
   * @return extracted datum
   */
  public ObservationHashMap getRawObservation(String target) {
    try {
      WxCallable wxCallable = new WxCallable(target);
/*
      ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(1));
      final ListenableFuture<ObservationHashMap> listenableFuture = executorService.submit(wxCallable);

      Futures.addCallback(listenableFuture, new FutureCallback<ObservationHashMap>() {
        public void onSuccess(ObservationHashMap result) {
          Log.d(LOG_TAG, "getRawObservation success:" + result.size());

          Set<String> keys = result.keySet();
          for (String key : keys) {
            Log.i(LOG_TAG, key + ":" + result.get(key));
          }
        }

        public void onFailure(Throwable throwable) {
          Log.d(LOG_TAG, "getRawObservation failure");
        }
      });

      // work work work

      ObservationHashMap observationHashMap = listenableFuture.get();

      executorService.shutdown();

      return observationHashMap;
      */
        return null;
    } catch(Exception exception) {
      exception.printStackTrace();
    }

    // empty hashmap as default
    return new ObservationHashMap();
  }

  /**
   *
   * @param hm
   * @return
   */
  public ObservationModel hashMapToModel(ObservationHashMap hm) {
    ObservationModel model = new ObservationModel();
    model.setDefault();

    Time timeStamp = Utility.stringToTime(hm.get(Constant.KEY_TIMESTAMP));
    if (timeStamp == null) {
      return(null);
    }

    model.setIdentifier(hm.get(Constant.KEY_STATION));
    model.setPressure(hm.get(Constant.KEY_PRESSURE));
    model.setTemperature(hm.get(Constant.KEY_TEMPERATURE));
    model.setTimeStamp(hm.get(Constant.KEY_TIMESTAMP));
    model.setTimeStampMs(timeStamp.toMillis(Constant.IGNORE_DST));
    model.setVisibility(hm.get(Constant.KEY_VISIBILITY));
    model.setWeather(hm.get(Constant.KEY_WEATHER));
    model.setWind(hm.get(Constant.KEY_WIND));

    model.setLocation(hm.get(Constant.KEY_LOCATION));

    try {
      model.setLatitude(Double.parseDouble(hm.get(Constant.KEY_LATITUDE)));
      model.setLongitude(Double.parseDouble(hm.get(Constant.KEY_LONGITUDE)));
    } catch(Exception exception) {
      exception.printStackTrace();
    }

    return model;
  }

  /**
   * collect a weather observation
   * @param context
   * @param target weather station ID
   * @return extracted datum
   */
  public ObservationModel performCollection(Context context, String target) {
    ObservationHashMap hm = getRawObservation(target.toUpperCase());
    return hashMapToModel(hm);
  }

  /**
   * Test for active network connection
   *
   * @param context context
   * @return true, there is a network connection
   */
  public boolean isNetworkConnection(Context context) {
    boolean flag = false;

    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo[] ni = cm.getAllNetworkInfo();
    for (int ii = 0; ii < ni.length; ii++) {
      //LogFacade.debug(LOG_TAG, "===network===>" + ni[ii].toString());
      if (ni[ii].getState() == NetworkInfo.State.CONNECTED) {
        flag = true;
      }
    }

    return flag;
  }
}