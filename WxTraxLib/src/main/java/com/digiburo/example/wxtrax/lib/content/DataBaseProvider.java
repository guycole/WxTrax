package com.digiburo.example.wxtrax.lib.content;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.digiburo.example.wxtrax.lib.utility.Constants;
import com.digiburo.example.wxtrax.lib.utility.LogFacade;

/**
 * database provider
 *
 * @author gsc
 */
public class DataBaseProvider extends ContentProvider {

  /* (non-Javadoc)
   * @see android.content.ContentProvider#getType(android.net.Uri)
   */
  @Override
  public String getType(Uri uri) {
    LogFacade.entry(LOG_TAG, "getType:" + uri);
    
    switch (uriMatcher.match(uri)) {
    case URI_MATCH_STATIONS:
      return(StationTable.CONTENT_TYPE);
    case URI_MATCH_STATION_ID:
      return(StationTable.CONTENT_ITEM_TYPE);
    case URI_MATCH_OBSERVATIONS:
      return(ObservationTable.CONTENT_TYPE);
    case URI_MATCH_OBSERVATION_ID:
      return(ObservationTable.CONTENT_ITEM_TYPE);
    default:
      throw new IllegalArgumentException("Unknown URI " + uri);
    }
  }

  /* (non-Javadoc)
   * @see android.content.ContentProvider#delete(android.net.Uri, java.lang.String, java.lang.String[])
   */
  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    LogFacade.entry(LOG_TAG, "delete:" + uri);    
    
    SQLiteDatabase db = _db_helper.getWritableDatabase();
    int count = 0;
    String id = "";

    switch (uriMatcher.match(uri)) {
    case URI_MATCH_STATIONS:
      count = db.delete(StationTable.TABLE_NAME, selection, selectionArgs);
      break;
    case URI_MATCH_STATION_ID:
      id = uri.getPathSegments().get(1);
      count = db.delete(StationTable.TABLE_NAME, StationTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs); 
      break;
    case URI_MATCH_OBSERVATIONS:
      count = db.delete(ObservationTable.TABLE_NAME, selection, selectionArgs);
      break;
    case URI_MATCH_OBSERVATION_ID:
      id = uri.getPathSegments().get(1);
      count = db.delete(ObservationTable.TABLE_NAME, ObservationTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs); 
      break;
    default:
      throw new IllegalArgumentException("Unknown URI " + uri);
    }
    
    getContext().getContentResolver().notifyChange(uri, null);
    return(count);
  }


  /* (non-Javadoc)
   * @see android.content.ContentProvider#insert(android.net.Uri, android.content.ContentValues)
   */
  @Override
  public Uri insert(Uri uri, ContentValues values) {
    LogFacade.entry(LOG_TAG, "insert:" + uri);
    
    SQLiteDatabase db = _db_helper.getWritableDatabase();
    long rowId = 0;
  
    switch (uriMatcher.match(uri)) {
    case URI_MATCH_STATIONS:
      rowId = db.insert(StationTable.TABLE_NAME, null, values);
      if (rowId > 0) {
        Uri result = ContentUris.withAppendedId(StationTable.CONTENT_URI, rowId);
        getContext().getContentResolver().notifyChange(StationTable.CONTENT_URI, null);
        return(result);
      }
      break;
    case URI_MATCH_OBSERVATIONS:
      rowId = db.insert(ObservationTable.TABLE_NAME, null, values);
      if (rowId > 0) {
        Uri result = ContentUris.withAppendedId(ObservationTable.CONTENT_URI, rowId);
        getContext().getContentResolver().notifyChange(ObservationTable.CONTENT_URI, null);
        return(result);
      }
      break;
    default:
      throw new IllegalArgumentException("Unknown URI " + uri);
    }
      
    throw new SQLException("insert failure:" + uri);
  }

  /* (non-Javadoc)
   * @see android.content.ContentProvider#onCreate()
   */
  @Override
  public boolean onCreate() {
    LogFacade.entry(LOG_TAG, "onCreate");
    _db_helper = new DataBaseHelper(getContext());
    return(true);
  }

  /* (non-Javadoc)
   * @see android.content.ContentProvider#query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String)
   */
  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    LogFacade.entry(LOG_TAG, "query:" + uri);
    LogFacade.debug(LOG_TAG, "projection:" + projection);
    
    if (projection != null) {
      LogFacade.error(LOG_TAG, "query w/non-null projection");
    }
    
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    String orderBy = sortOrder;
    
    switch (uriMatcher.match(uri)) {
    case URI_MATCH_STATIONS:
      LogFacade.debug(LOG_TAG, "match all stations");
      qb.setTables(StationTable.TABLE_NAME);
      qb.setProjectionMap(StationTable.PROJECTION_MAP);
      if (sortOrder == null) {
        orderBy = StationTable.DEFAULT_SORT_ORDER;
      }
      break;
    case URI_MATCH_STATION_ID:
      LogFacade.debug(LOG_TAG, "match singular station");
      qb.setTables(StationTable.TABLE_NAME);
      qb.setProjectionMap(StationTable.PROJECTION_MAP);
      qb.appendWhere(StationTable.Columns._ID + "=" + uri.getPathSegments().get(1));
      if (sortOrder == null) {
        orderBy = StationTable.DEFAULT_SORT_ORDER;
      }
      break;  
    case URI_MATCH_OBSERVATIONS:
      LogFacade.debug(LOG_TAG, "match all observations");
      qb.setTables(ObservationTable.TABLE_NAME);
      qb.setProjectionMap(ObservationTable.PROJECTION_MAP);
      if (sortOrder == null) {
        orderBy = ObservationTable.DEFAULT_SORT_ORDER;
      }
      break;
    case URI_MATCH_OBSERVATION_ID:
      LogFacade.debug(LOG_TAG, "match singular observation");
      qb.setTables(ObservationTable.TABLE_NAME);
      qb.setProjectionMap(ObservationTable.PROJECTION_MAP);
      qb.appendWhere(ObservationTable.Columns._ID + "=" + uri.getPathSegments().get(1));
      if (sortOrder == null) {
        orderBy = ObservationTable.DEFAULT_SORT_ORDER;
      }
      break;
    default:
      throw new IllegalArgumentException("Unknown URI " + uri);
    }
    
    // Get the database and run the query
    SQLiteDatabase db = _db_helper.getReadableDatabase();
    Cursor cc = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
    LogFacade.debug(LOG_TAG, "query result size:" + cc.getCount() + ":column size:" + cc.getColumnCount());
  
    // Tell the cursor what uri to watch, so it knows when its source data changes
    cc.setNotificationUri(getContext().getContentResolver(), uri);
    return(cc);
  }

  /* (non-Javadoc)
   * @see android.content.ContentProvider#update(android.net.Uri, android.content.ContentValues, java.lang.String, java.lang.String[])
   */
  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    LogFacade.entry(LOG_TAG, "update:" + uri);
    
    SQLiteDatabase db = _db_helper.getWritableDatabase();
    int count = 0;
    String id = "";

    switch (uriMatcher.match(uri)) {
    case URI_MATCH_STATIONS:
      count = db.update(StationTable.TABLE_NAME, values, selection, selectionArgs);
      break;
    case URI_MATCH_STATION_ID:
      id = uri.getPathSegments().get(1);
      count = db.update(StationTable.TABLE_NAME, values, StationTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);     
      break;
    case URI_MATCH_OBSERVATIONS:
      count = db.update(ObservationTable.TABLE_NAME, values, selection, selectionArgs);
      break;
    case URI_MATCH_OBSERVATION_ID:
      id = uri.getPathSegments().get(1);
      count = db.update(ObservationTable.TABLE_NAME, values, ObservationTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);     
      break;
    default:
      throw new IllegalArgumentException("Unknown URI " + uri);
    }

    getContext().getContentResolver().notifyChange(uri, null);
    return(count);
  }
  
  //
  private static final UriMatcher uriMatcher;
  
  //URI Matcher Targets
  private static final int URI_MATCH_STATIONS = 10;
  private static final int URI_MATCH_STATION_ID = 11;
  private static final int URI_MATCH_OBSERVATIONS = 20;
  private static final int URI_MATCH_OBSERVATION_ID = 21;
  
  static {
    uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    uriMatcher.addURI(Constants.AUTHORITY, StationTable.TABLE_NAME, URI_MATCH_STATIONS);
    uriMatcher.addURI(Constants.AUTHORITY, StationTable.TABLE_NAME + "/#", URI_MATCH_STATION_ID);

    uriMatcher.addURI(Constants.AUTHORITY, ObservationTable.TABLE_NAME, URI_MATCH_OBSERVATIONS);
    uriMatcher.addURI(Constants.AUTHORITY, ObservationTable.TABLE_NAME + "/#", URI_MATCH_OBSERVATION_ID);
  }
  
  //
  private DataBaseHelper _db_helper;
  
  //
  public final String LOG_TAG = getClass().getName();
}

/*
 * Copyright 2011 Digital Burro, INC
 * Created on Jun 17, 2011 by gsc
 */