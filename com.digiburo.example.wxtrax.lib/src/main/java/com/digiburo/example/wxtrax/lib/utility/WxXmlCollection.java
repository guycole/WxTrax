package com.digiburo.example.wxtrax.lib.utility;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.Time;

import com.digiburo.example.wxtrax.lib.content.ObservationModel;

/**
 * Collect NWS datum
 *
 * @author gsc
 */
public class WxXmlCollection {
  
  /**
   * Collect a NWS XML observation and return it as key/value pairs
   * @param target weather station identifier
   * @return extracted datum
   */
  public ObservationHashMap getRawObservation(String target) {
    LogFacade.entry(LOG_TAG, "getRawObservation:" + target);

    ObservationHashMap result = null;

    try {
      NetworkRunnable networkRunnable = new NetworkRunnable(target);
      Thread thread = new Thread(networkRunnable);
      thread.start();
      thread.join();
      result = networkRunnable.getTokens();
    } catch(Exception exception) {
      LogFacade.error(LOG_TAG, exception);
    }

    return(result);
  }
  
  /**
   * Android 3.x and later will not permit network operations on main thread
   * New stations are tested from the UI, so need threaded network request
   */
  class NetworkRunnable implements Runnable {

    public NetworkRunnable(String target) {
      this.target = target;
    }

    public ObservationHashMap getTokens() {
      return(handler.getTokens());
    }

    @Override
    public void run() {
      SAXParserFactory spf = SAXParserFactory.newInstance();

      try {
        SAXParser sp = spf.newSAXParser();
        XMLReader xr = sp.getXMLReader();
        xr.setContentHandler(handler);

        URL url = new URL(Constants.NWS_OBSERVATION_URL + target + ".xml");
        xr.parse(new InputSource(url.openStream()));
      } catch(Exception exception) {
        LogFacade.error(LOG_TAG, exception);
      }
    }

    private final WxXmlHandler handler = new WxXmlHandler();

    private final String target;
  }
  
  /**
   * 
   * @param hm
   * @return
   */
  public ObservationModel hashMapToModel(ObservationHashMap hm) {
    ObservationModel model = new ObservationModel();
    model.setDefault(null);
    
    Time timeStamp = Utility.stringToTime(hm.get(Constants.KEY_TIMESTAMP));
    if (timeStamp == null) {
      LogFacade.debug(LOG_TAG, "timestamp parse failure:" + hm.get(Constants.KEY_TIMESTAMP));
      return(null);
    }
    
    model.setIdentifier(hm.get(Constants.KEY_STATION));
    model.setPressure(hm.get(Constants.KEY_PRESSURE));
    model.setTemperature(hm.get(Constants.KEY_TEMPERATURE));
    model.setTimeStamp(hm.get(Constants.KEY_TIMESTAMP));
    model.setTimeStampMs(timeStamp.toMillis(Constants.IGNORE_DST));
    model.setVisibility(hm.get(Constants.KEY_VISIBILITY));
    model.setWeather(hm.get(Constants.KEY_WEATHER));
    model.setWind(hm.get(Constants.KEY_WIND));
    
    model.setLocation(hm.get(Constants.KEY_LOCATION));
    
    try {
      model.setLatitude(Double.parseDouble(hm.get(Constants.KEY_LATITUDE)));
      model.setLongitude(Double.parseDouble(hm.get(Constants.KEY_LONGITUDE)));
    } catch(Exception exception) {
      LogFacade.error(LOG_TAG, exception);
    }
    
    return(model);
  }

  /**
   * collect a weather observation
   * @param context
   * @param target weather station ID
   * @return extracted datum
   */
  public ObservationModel performCollection(Context context, String target) {
    LogFacade.entry(LOG_TAG, "performCollection:" + target);
    
    if (isNetworkConnection(context)) {
      LogFacade.debug(LOG_TAG, "network available");
    } else {
      LogFacade.debug(LOG_TAG, "network unavailable");
      return(null);
    }
    
    ObservationHashMap hm = getRawObservation(target.toUpperCase());
    
    return(hashMapToModel(hm));
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
  
    return(flag);
  }

  //
  public final String LOG_TAG = getClass().getName();
}

/*
 * Copyright 2011 Digital Burro, INC
 * Created on Feb 20, 2011 by gsc
 */