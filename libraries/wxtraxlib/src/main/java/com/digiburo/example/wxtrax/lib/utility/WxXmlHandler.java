package com.digiburo.example.wxtrax.lib.utility;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX handler for NWS datum
 *
 * @author gsc
 */
public class WxXmlHandler extends DefaultHandler {
  
  /**
   * Return collected key/value pairs
   * @return collected key/value pairs, might be empty but never null
   */
  public ObservationHashMap getTokens() {
    return(_tokenz);
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    //LogFacade.entry(LOG_TAG, "startElement");
    
    _flag = true;
    _token = Constants.BAD_VALUE;
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    /*
    LogFacade.entry(LOG_TAG, "endElement");
    LogFacade.debug(LOG_TAG, "uri:" + uri);
    LogFacade.debug(LOG_TAG, "local:" + localName);
    LogFacade.debug(LOG_TAG, "name:" + qName);
    */
    
    /*
    if (localName.equals(Constants.KEY_DEWPOINT)) {
    } else if (localName.equals(Constants.KEY_DEWPOINT_C)) {
    } else if (localName.equals(Constants.KEY_DEWPOINT_F)) {
    } else if (localName.equals(Constants.KEY_HUMID)) {
    } else if (localName.equals(Constants.KEY_LATITUDE)) {
    } else if (localName.equals(Constants.KEY_LOCATION)) {
    } else if (localName.equals(Constants.KEY_LONGITUDE)) {
    } else if (localName.equals(Constants.KEY_PRESSURE)) {
    } else if (localName.equals(Constants.KEY_PRESSURE_IN)) {
    } else if (localName.equals(Constants.KEY_PRESSURE_MB)) {
    } else if (localName.equals(Constants.KEY_STATION)) {
    } else if (localName.equals(Constants.KEY_TEMPERATURE)) {
    } else if (localName.equals(Constants.KEY_TEMP_C)) {
    } else if (localName.equals(Constants.KEY_TEMP_F)) {
    } else if (localName.equals(Constants.KEY_TIME_OBS)) {
    } else if (localName.equals(Constants.KEY_TIMESTAMP)) {
    } else if (localName.equals(Constants.KEY_VISIBILITY)) {
    } else if (localName.equals(Constants.KEY_WEATHER)) {
    } else if (localName.equals(Constants.KEY_WIND)) {
    } else if (localName.equals(Constants.KEY_WIND_DEG)) {
    } else if (localName.equals(Constants.KEY_WIND_DIR)) {
    } else if (localName.equals(Constants.KEY_WIND_KT)) {
    } else if (localName.equals(Constants.KEY_WIND_MPH)) {
    } else if (localName.equals(Constants.KEY_WINDCHILL)) {
    } else if (localName.equals(Constants.KEY_WINDCHILL_C)) {
    } else if (localName.equals(Constants.KEY_WINDCHILL_F)) {
    } else {
      LogFacade.debug(LOG_TAG, "missing key:" + localName + ":" + _token);
    }
    */
    
    //LogFacade.debug(LOG_TAG, localName + ":" + _token);
    
    _tokenz.put(localName, _token);
    
    _flag = false;
  }
  
  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    //LogFacade.entry(LOG_TAG, "characters");
    
    if (_flag) {
      _token = new String(ch, start, length);
      //LogFacade.debug(LOG_TAG, "--->" + _token);
    }
  }
  
  //
  private boolean _flag = false;
  
  //
  private String _token;
  
  //
  private ObservationHashMap _tokenz = new ObservationHashMap();
  
  //
  //private final String LOG_TAG = getClass().getName();
}

/*
 * Copyright 2011 Digital Burro, INC
 * Created on Jun 17, 2011 by gsc
 */