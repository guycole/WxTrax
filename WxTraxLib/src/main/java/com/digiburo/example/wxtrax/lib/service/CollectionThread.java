package com.digiburo.example.wxtrax.lib.service;

import com.digiburo.example.wxtrax.lib.utility.LogFacade;
import com.digiburo.example.wxtrax.lib.utility.ObservationHashMap;
import com.digiburo.example.wxtrax.lib.utility.WxXmlCollection;

/**
 * Collect and parse station observation.
 * Must be threaded for Honeycomb.
 *
 * @author gsc
 */
public class CollectionThread extends Thread {
  
  /**
   * ctor
   * @param target, i.e. KRDD
   */
  public CollectionThread(String target) {
    _target = target;
  }
  
  /**
   * Return true if collection complete
   * @return true if collection complete
   */
  public boolean isComplete() {
    return(_complete);
  }
  
  /**
   * Return parsed observation results
   * @return paresed observation results, might be null
   */
  public ObservationHashMap getRawObservation() {
    return(_rawObservation);
  }

  /**
   * perform collection
   */
  public void run() {
    LogFacade.debug(LOG_TAG, "xxx collection thread run:" + _target);
    
    WxXmlCollection wxc = new WxXmlCollection();
    _rawObservation = wxc.getRawObservation(_target);

    _complete = true;
  }
  
  // weather station callsign
  private final String _target;
  
  // true, collection complete
  private boolean _complete = false;

  // collected observation results
  private ObservationHashMap _rawObservation;

  //
  public final String LOG_TAG = getClass().getName();
}
