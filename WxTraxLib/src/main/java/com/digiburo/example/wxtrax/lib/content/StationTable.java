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
public class StationTable implements DataBaseTableIf {

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
    Set<String> keySet = StationTable.PROJECTION_MAP.keySet();
    String[] result = (String[]) keySet.toArray(new String[keySet.size()]);
    return(result);
  }
  
  //
  public static final class Columns implements BaseColumns {
 
    // column names
    public static final String IDENTIFIER = "identifier";
    public static final String LOCATION = "location";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String ACTIVE = "active";
  }
  
  //
  public static final String TABLE_NAME = "station";
  
  // select all stations
  public static final Uri CONTENT_URI = Uri.parse("content://" + Constants.AUTHORITY + "/" + TABLE_NAME);
  
  //
  public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.digiburo." + TABLE_NAME;
  
  //
  public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.digiburo." + TABLE_NAME;
  
  //
  public static final String DEFAULT_SORT_ORDER = Columns.LOCATION + " ASC";
  
  //
  public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
    + Columns._ID + " INTEGER PRIMARY KEY,"
    + Columns.IDENTIFIER + " TEXT NOT NULL,"
    + Columns.LOCATION + " TEXT NOT NULL,"
    + Columns.LATITUDE + " REAL NOT NULL,"
    + Columns.LONGITUDE + " REAL NOT NULL,"
    + Columns.ACTIVE + " INTEGER NOT NULL"
    + ");";
  
  //
  public static HashMap<String, String> PROJECTION_MAP = new HashMap<String, String>();
  
  static {
    PROJECTION_MAP.put(StationTable.Columns._ID, StationTable.Columns._ID);
    PROJECTION_MAP.put(StationTable.Columns.IDENTIFIER, StationTable.Columns.IDENTIFIER);
    PROJECTION_MAP.put(StationTable.Columns.LOCATION, StationTable.Columns.LOCATION);
    PROJECTION_MAP.put(StationTable.Columns.LATITUDE, StationTable.Columns.LATITUDE);
    PROJECTION_MAP.put(StationTable.Columns.LONGITUDE, StationTable.Columns.LONGITUDE);
    PROJECTION_MAP.put(StationTable.Columns.ACTIVE, StationTable.Columns.ACTIVE);
  }
}

/*
 * Copyright 2011 Digital Burro, INC
 * Created on Jun 17, 2011 by gsc
 */