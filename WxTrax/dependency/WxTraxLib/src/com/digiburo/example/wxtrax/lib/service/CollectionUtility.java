package com.digiburo.example.wxtrax.lib.service;

import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.os.AsyncTask;

import com.digiburo.example.wxtrax.lib.content.ObservationModel;
import com.digiburo.example.wxtrax.lib.utility.LogFacade;
import com.digiburo.example.wxtrax.lib.utility.WxXmlCollection;

/**
 * Collect weather report for a single station
 * 
 * @author gsc
 */
public class CollectionUtility {

  /**
   * ctor
   * @param context
   * @param callSign
   */
  public CollectionUtility(Context context, String callSign) {
    _callSign = callSign;
    _context = context;
  }

  /**
   * Collect weather report for a single station
   * @return current weather or null if not found
   */
  public ObservationModel execute() {
    AsyncCollectionTask act = new AsyncCollectionTask();
    act.execute();

    try {
      //block for response
      return(act.get());
    } catch(ExecutionException exception) {
      LogFacade.error(LOG_TAG, exception);
    } catch(InterruptedException exception) {
      LogFacade.error(LOG_TAG, exception);
    }

    return(null);
  }

  /**
   * threaded weather collection
   */
  private class AsyncCollectionTask extends AsyncTask<Void, Void, ObservationModel> {

    /**
     * threaded weather collection
     */
    protected ObservationModel doInBackground(Void... parameters) {
      LogFacade.debug(LOG_TAG, "doInBackground:" + _callSign);

      try {
        WxXmlCollection wxc = new WxXmlCollection();
        ObservationModel model = wxc.performCollection(_context, _callSign);
        return(model);
      } catch(Exception exception) {
        LogFacade.error(LOG_TAG, exception);
      }

      return(null);
    }
  }

  //
  private String _callSign = null;
  
  //
  private Context _context = null;

  //
  private final String LOG_TAG = getClass().getName();
}

/*
 * Copyright 2010 Digital Burro, INC
 * Created on May 8, 2010 by gsc
 */