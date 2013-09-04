package com.digiburo.example.wxtrax.lib.service;

import java.util.ArrayList;

import android.app.IntentService;
import android.content.Intent;

import com.digiburo.example.wxtrax.lib.content.DataBaseFacade;
import com.digiburo.example.wxtrax.lib.content.ObservationModel;
import com.digiburo.example.wxtrax.lib.content.StationModel;
import com.digiburo.example.wxtrax.lib.utility.Constants;
import com.digiburo.example.wxtrax.lib.utility.LogFacade;
import com.digiburo.example.wxtrax.lib.utility.WxXmlCollection;

/**
 * //type comment//
 *
 * @author gsc
 */
public class CollectionService extends IntentService {
  
  public CollectionService() {
    super("collectionService");
  }

  @Override
  public void onCreate() {
    super.onCreate();
    LogFacade.entry(LOG_TAG, "onCreate");
  }
  
  @Override
  public void onDestroy() {
    super.onDestroy();
    LogFacade.entry(LOG_TAG, "onDestroy");
  }

  /* (non-Javadoc)
   * @see android.app.IntentService#onHandleIntent(android.content.Intent)
   */
  @Override
  protected void onHandleIntent(Intent intent) {
    LogFacade.entry(LOG_TAG, "onHandleIntent");

    String targetCallSign = intent.getStringExtra(Constants.INTENT_KEY_CALLSIGN);
    if (targetCallSign == null) {
      collectAllStations();
    } else {
      collectSingleStation(targetCallSign);
    }
  }
  
  private void collectAllStations() {
    DataBaseFacade dbf = new DataBaseFacade(getBaseContext());
    
    ArrayList<String> stations = dbf.getActiveStations();
    StationModel favoriteStation = dbf.getFavoriteStation();

    collection(stations, favoriteStation, dbf);
  }

  private void collectSingleStation(String target) {
    DataBaseFacade dbf = new DataBaseFacade(getBaseContext());

    ArrayList<String> stations = new ArrayList<String>();
    stations.add(target);

    collection(stations, null, dbf);
  }
  
  /**
   * collect each station and save results
   * if favorite station, generate broadcast intent
   * @param stations
   * @param favoriteStation
   * @param dbf
   */
  private void collection(ArrayList<String> stations, StationModel favoriteStation, DataBaseFacade dbf) {
    LogFacade.debug(LOG_TAG, "collection station population:" + stations.size());
   
    try {
      WxXmlCollection wxc = new WxXmlCollection();

      for (String station:stations) {
        ObservationModel model = wxc.performCollection(getBaseContext(), station);
        if (model == null) {
          LogFacade.debug(LOG_TAG, "null model noted");
        } else {
          dbf.newStation(model);

          if (!dbf.newObservation(model)) {
            LogFacade.debug(LOG_TAG, "insert failure:" + model.getIdentifier());
          } else {
            LogFacade.debug(LOG_TAG, "insert success:" + model.getIdentifier());
          }

          if ((favoriteStation != null) && (favoriteStation.getIdentifier().equals(model.getIdentifier()))) {
            LogFacade.debug(LOG_TAG, "faveStation match:" + favoriteStation.getIdentifier());
            
            Intent observationIntent = new Intent(Constants.FRESH_OBSERVATION);
            sendBroadcast(observationIntent);
          }
        }
      }
    } catch(Exception exception) {
      LogFacade.error(LOG_TAG, exception);
    }
  }
  
  //
  public final String LOG_TAG = getClass().getName();
}

/*
 * Copyright 2011 Digital Burro, INC
 * Created on Jun 17, 2011 by gsc
 */