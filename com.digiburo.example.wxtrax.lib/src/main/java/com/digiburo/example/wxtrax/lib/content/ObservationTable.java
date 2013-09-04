package com.digiburo.example.wxtrax.lib.content;

import java.util.HashMap;
import java.util.Set;

import android.net.Uri;
import android.provider.BaseColumns;

import com.digiburo.example.wxtrax.lib.utility.Constants;

/**
 * weather station table definitions
 *
 * @author gsc
 */
public class ObservationTable implements DataBaseTableIf {

  /* (non-Javadoc)
   * @see com.digiburo.example.wxtrax.content.DataBaseTableIf#getTableName()
   */
  @Override
  public String getTableName() {
    return(TABLE_NAME);
  }

  /* (non-Javadoc)
   * @see com.digiburo.example.wxtrax.content.DataBaseTableIf#getDefaultSortOrder()
   */
  @Override
  public String getDefaultSortOrder() {
    return(DEFAULT_SORT_ORDER);
  }

  /* (non-Javadoc)
   * @see com.digiburo.example.wxtrax.content.DataBaseTableIf#getDefaultProjection()
   */
  @Override
  public String[] getDefaultProjection() {
    Set<String> keySet = ObservationTable.PROJECTION_MAP.keySet();
    String[] result = (String[]) keySet.toArray(new String[keySet.size()]);
    return(result);
  }
  
  //
  public static final class Columns implements BaseColumns {
 
    // column names
    public static final String TIMESTAMP_MS = "timeStampMillis";
    public static final String IDENTIFIER = Constants.KEY_STATION;
    public static final String PRESSURE = Constants.KEY_PRESSURE;
    public static final String TEMPERATURE = Constants.KEY_TEMPERATURE;
    public static final String TIMESTAMP = Constants.KEY_TIMESTAMP;
    public static final String VISIBILITY = Constants.KEY_VISIBILITY;
    public static final String WEATHER = Constants.KEY_WEATHER;
    public static final String WIND = Constants.KEY_WIND;
  }
  
  //
  public static final String TABLE_NAME = "observation";
  
  // select all observations
  public static final Uri CONTENT_URI = Uri.parse("content://" + Constants.AUTHORITY + "/" + TABLE_NAME);
  
  //
  public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.digiburo." + TABLE_NAME;
  
  //
  public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.digiburo." + TABLE_NAME;
  
  //
  public static final String DEFAULT_SORT_ORDER = Columns.TIMESTAMP_MS + " DESC";
  
  //
  public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
    + Columns._ID + " INTEGER PRIMARY KEY,"
    + Columns.IDENTIFIER   + " TEXT NOT NULL,"
    + Columns.PRESSURE     + " TEXT NOT NULL,"
    + Columns.TEMPERATURE  + " TEXT NOT NULL,"
    + Columns.TIMESTAMP    + " TEXT NOT NULL,"
    + Columns.TIMESTAMP_MS + " INTEGER NOT NULL,"
    + Columns.VISIBILITY   + " TEXT NOT NULL,"
    + Columns.WEATHER      + " TEXT NOT NULL,"
    + Columns.WIND         + " TEXT NOT NULL"
    + ");";

  //
  public static HashMap<String, String> PROJECTION_MAP = new HashMap<String, String>();

  static {
    PROJECTION_MAP.put(ObservationTable.Columns._ID, ObservationTable.Columns._ID);
    PROJECTION_MAP.put(ObservationTable.Columns.IDENTIFIER, ObservationTable.Columns.IDENTIFIER);
    PROJECTION_MAP.put(ObservationTable.Columns.PRESSURE, ObservationTable.Columns.PRESSURE);
    PROJECTION_MAP.put(ObservationTable.Columns.TEMPERATURE, ObservationTable.Columns.TEMPERATURE);
    PROJECTION_MAP.put(ObservationTable.Columns.TIMESTAMP, ObservationTable.Columns.TIMESTAMP);
    PROJECTION_MAP.put(ObservationTable.Columns.TIMESTAMP_MS, ObservationTable.Columns.TIMESTAMP_MS);
    PROJECTION_MAP.put(ObservationTable.Columns.VISIBILITY, ObservationTable.Columns.VISIBILITY);
    PROJECTION_MAP.put(ObservationTable.Columns.WEATHER, ObservationTable.Columns.WEATHER);
    PROJECTION_MAP.put(ObservationTable.Columns.WIND, ObservationTable.Columns.WIND);
  }
}

/*
 * Copyright 2011 Digital Burro, INC
 * Created on Jun 17, 2011 by gsc
 */