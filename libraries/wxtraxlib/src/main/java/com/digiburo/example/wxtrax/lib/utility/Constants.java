package com.digiburo.example.wxtrax.lib.utility;

/**
 * application constants
 *
 * @author gsc
 */
public class Constants {
  
  //flag development or production
  public static final boolean DEBUG_APPLICATION_MODE = false;
  
  //
  public static final String AUTHORITY = "com.digiburo.example.wxtrax.content.DataBaseProvider";
  
  //
  public static final String FRESH_OBSERVATION = "com.digiburo.example.wxtrax.fresh_observation";
  
  //time option
  public static final boolean IGNORE_DST = false;
  
  //
  public static final String BAD_VALUE = "BAD_VALUE";
  
  // model defaults
  public static final String DB_DEFAULT_IDENTIFIER = "IDENTIFIER";
  public static final String DB_DEFAULT_LOCATION = "LOCATION";
  public static final String DB_DEFAULT_PRESSURE = "PRESSURE";
  public static final String DB_DEFAULT_TEMPERATURE = "TEMPERATURE";
  public static final String DB_DEFAULT_TIMESTAMP = "TIMESTAMP";
  public static final String DB_DEFAULT_VISIBILITY = "VISIBILITY";
  public static final String DB_DEFAULT_WEATHER = "WEATHER";
  public static final String DB_DEFAULT_WIND = "WIND";
  
  // user preference defaults
  public static final long DEFAULT_POLL = 30L;
  public static final long DEFAULT_STATION_ID = -1;
  
  // national weather service URL
  public static final String NWS_BASE_URL = "http://www.weather.gov";
  public static final String NWS_OBSERVATION_URL = NWS_BASE_URL + "/xml/current_obs/";
  
  //weather observation keys
  public static final String KEY_DEWPOINT_C = "dewpoint_c";
  public static final String KEY_DEWPOINT_F = "dewpoint_f";
  public static final String KEY_DEWPOINT = "dewpoint_string";
  public static final String KEY_HUMID = "relative_humidity";
  public static final String KEY_LOCATION = "location";
  public static final String KEY_LATITUDE = "latitude";
  public static final String KEY_LONGITUDE = "longitude";
  public static final String KEY_PRESSURE_IN = "pressure_in";
  public static final String KEY_PRESSURE_MB = "pressure_mb";
  public static final String KEY_PRESSURE = "pressure_string";
  public static final String KEY_STATION = "station_id";
  public static final String KEY_TEMP_F = "temp_f";
  public static final String KEY_TEMP_C = "temp_c";
  public static final String KEY_TEMPERATURE = "temperature_string";
  public static final String KEY_TIME_OBS = "observation_time";
  public static final String KEY_TIMESTAMP = "observation_time_rfc822";
  public static final String KEY_VISIBILITY = "visibility_mi";
  public static final String KEY_WEATHER = "weather";
  public static final String KEY_WIND_DIR = "wind_dir";
  public static final String KEY_WIND_KT = "wind_kt";
  public static final String KEY_WIND_MPH = "wind_mph";
  public static final String KEY_WIND = "wind_string";
  public static final String KEY_WIND_DEG = "wind_degrees";
  public static final String KEY_WINDCHILL_C = "windchill_c";
  public static final String KEY_WINDCHILL_F = "windchill_f";
  public static final String KEY_WINDCHILL = "windchill_string";

  //
  public static final int SQL_TRUE = 1;
  public static final int SQL_FALSE = 0;
  
  //
  public static final String INTENT_KEY_CALLSIGN = "callsign";
}

/*
 * Copyright 2010 Digital Burro, INC
 * Created on Nov 17, 2010 by gsc
 */