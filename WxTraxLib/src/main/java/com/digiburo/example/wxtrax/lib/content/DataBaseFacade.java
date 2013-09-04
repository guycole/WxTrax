package com.digiburo.example.wxtrax.lib.content;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.digiburo.example.wxtrax.lib.utility.Constants;
import com.digiburo.example.wxtrax.lib.utility.LogFacade;
import com.digiburo.example.wxtrax.lib.utility.UserPreferenceHelper;

/**
 * data base facade
 *
 * @author gsc
 */
public class DataBaseFacade {
  
  /**
   * ctor
   * 
   * @param appContext application context
   */
  public DataBaseFacade(Context appContext) {
    _context = appContext;
    _dbh = new DataBaseHelper(appContext);
  }
  
  /**
   * Delete the specified row from table
   * 
   * @param rowId
   * @param tableName
   */
  public void deleteModel(Long rowId, String tableName) {
    LogFacade.entry(LOG_TAG, "deleteModel:" + rowId + ":" + tableName);
    
    String selection = "_id=?";
    String selectionArgs[] = new String[] {rowId.toString()};
    
    SQLiteDatabase sqlDb = _dbh.getWritableDatabase();
    int count = sqlDb.delete(tableName, selection, selectionArgs);
    sqlDb.close();
    
    LogFacade.exit(LOG_TAG, "deleteModel count:" + count);
  }
  
  /**
   * insert/update a database table from model
   * 
   * @param arg populated model
   * @return true is success
   */
  public boolean updateModel(DataBaseModelIf arg) {
    LogFacade.entry(LOG_TAG, "updateModel:" + arg);
    
    boolean flag = false;
    
    SQLiteDatabase sqlDb = _dbh.getWritableDatabase();
    
    if ((arg.getId() == null) || (arg.getId() < 1)) {
      //insert
      long result = sqlDb.insert(arg.getTableName(), null, arg.toContentValues(_context));
      LogFacade.debug(LOG_TAG, "updateModel/insert:" + result);
      if (result > 0) {
        arg.setId(result);
        flag = true;
      }
    } else {
      //update
      String selection = "_id=?";
      String[] selectionArgs = new String[] {arg.getId().toString()};
      int result = sqlDb.update(arg.getTableName(), arg.toContentValues(_context), selection, selectionArgs);
      if (result > 0) {
        flag = true;
      }
    }
    
    sqlDb.close();
    
    return(flag);
  }
  
  /**
   * Select a database row, return as model
   * 
   * @param rowId target row ID
   * @param dbTable target table
   * @return
   */
  public DataBaseModelIf selectModel(Long rowId, DataBaseTableIf dbTable) {
    LogFacade.entry(LOG_TAG, "selectModel:" + rowId + ":" + dbTable.getTableName());
    
    DataBaseModelIf result = null;
    
    String tableName = dbTable.getTableName();
    String[] projection = dbTable.getDefaultProjection();
    
    String selection = "_id=?";
    String[] selectionArgs = new String[] {rowId.toString()};

    SQLiteDatabase sqlDb = _dbh.getReadableDatabase();
    Cursor cursor = sqlDb.query(tableName, projection, selection, selectionArgs, null, null, null);
    LogFacade.debug(LOG_TAG, "selectModel:" + rowId + ":" + tableName + ":rows:" + cursor.getCount() + ":" + cursor.getColumnCount());
    
    if (cursor.moveToFirst()) {
      result = cursorToModel(cursor, tableName);
    }
   
    cursor.close();
    sqlDb.close();

    return(result);
  }
  
  /**
   * Map cursor to model
   * 
   * @param cursor
   * @param tableName
   * @return populated model
   */
  private DataBaseModelIf cursorToModel(Cursor cursor, String tableName) {
    DataBaseModelIf result = null;
    
    if (tableName.equals(ObservationTable.TABLE_NAME)) {
      result = new ObservationModel();
    } else if (tableName.equals(StationTable.TABLE_NAME)) {
      result = new StationModel();
    } else {
      throw new IllegalArgumentException("unknown table:" + tableName);
    }
    
    result.fromCursor(cursor);
    
    return(result);
  }
  
  /**
   * Return a station by callsign/identifier
   * @param callsign
   * @return
   */
  public StationModel getStationByIdentifier(String identifier) {
    StationModel result = null;

    String selection = StationTable.Columns.IDENTIFIER + "=?";
    String[] selectionArgs = new String[] {identifier};
    
    StationTable table = new StationTable();
    
    SQLiteDatabase sqlDb = _dbh.getReadableDatabase();
    Cursor cursor = sqlDb.query(StationTable.TABLE_NAME, table.getDefaultProjection(), selection, selectionArgs, null, null, null);

    LogFacade.debug(LOG_TAG, "selectStation:" + identifier + ":rows:" + cursor.getCount() + ":" + cursor.getColumnCount());
    
    if (cursor.moveToFirst()) {
      result = new StationModel();
      result.fromCursor(cursor);
    }
    
    cursor.close();
    sqlDb.close();
    
    return(result);
  }
  
  /**
   * Insert a new station
   * 
   * @param fresh observation
   * @return true is success
   */
  public boolean newStation(ObservationModel observation) {
    if ((observation.getIdentifier() == null) || (observation.getIdentifier().equals(Constants.DB_DEFAULT_IDENTIFIER))) {
      LogFacade.debug(LOG_TAG, "bad identifier");
      return(false);
    }

    if ((observation.getLocation() == null) || (observation.getLocation().equals(Constants.DB_DEFAULT_LOCATION))) {
      LogFacade.debug(LOG_TAG, "bad location");
      return(false);
    }
       
    StationModel sm = getStationByIdentifier(observation.getIdentifier());
    if (sm == null) {
      LogFacade.debug(LOG_TAG, "unique station");
    } else {
      LogFacade.debug(LOG_TAG, "duplicate station");
      return(false);
    }
    
    sm = new StationModel();
    sm.setDefault(_context);
    sm.setIdentifier(observation.getIdentifier());
    sm.setLocation(observation.getLocation());
    sm.setLatitude(observation.getLatitude());
    sm.setLongitude(observation.getLongitude());
    sm.setActive(true);
    
    boolean flag = updateModel(sm);
    if (flag) {
      _context.getContentResolver().notifyChange(StationTable.CONTENT_URI, null);
    }
 
    return(flag);
  }
  
  /**
   * load a new observation into the table
   * @param model populated model
   * @return true if success
   */
  public boolean newObservation(ObservationModel model) {
    boolean flag = false;
    
    if (model.getTimeStampMs() < 1) {
      LogFacade.debug(LOG_TAG, "bad timestamp");
      return(flag);
    }
    
    //test for duplicate row
    String selection = ObservationTable.Columns.IDENTIFIER + "=? and " + ObservationTable.Columns.TIMESTAMP_MS + "=?";
    String[] selectionArgs = new String[] {model.getIdentifier(), Long.toString(model.getTimeStampMs())};
    
    ObservationTable table = new ObservationTable();
    
    SQLiteDatabase sqlDb = _dbh.getReadableDatabase();
    Cursor cursor = sqlDb.query(ObservationTable.TABLE_NAME, table.getDefaultProjection(), selection, selectionArgs, null, null, null);

    if (cursor.moveToFirst()) {
      LogFacade.debug(LOG_TAG, "duplicate observation:" + model.getIdentifier() + ":" + model.getTimeStamp() + ":" + model.getTimeStampMs());
      flag = false;
    } else {
      flag = updateModel(model);
      if (flag) {
        _context.getContentResolver().notifyChange(ObservationTable.CONTENT_URI, null);
      }
    }

    cursor.close();
    sqlDb.close();
    
    return(flag);
  }
  
  /**
   * return a list of all active stations
   * @return a list of all active stations
   */
  public ArrayList<String> getActiveStations() {
    ArrayList<String> result = new ArrayList<String>();
    
    DataBaseTableIf table = new StationTable();
    
    String[] projection = table.getDefaultProjection();
    String orderBy = table.getDefaultSortOrder();
    
    String selection = StationTable.Columns.ACTIVE + "=?";
    String[] selectionArgs = {Integer.toString(Constants.SQL_TRUE)};
    
    SQLiteDatabase sqlDb = _dbh.getReadableDatabase();
    Cursor cursor = sqlDb.query(table.getTableName(), projection, selection, selectionArgs, null, null, orderBy);  

    if (cursor.moveToFirst()) {
      do {
        result.add(cursor.getString(cursor.getColumnIndex(StationTable.Columns.IDENTIFIER)));
      } while(cursor.moveToNext());
    }
    
    cursor.close();
    sqlDb.close();

    return(result);
  }
  
  /**
   * 
   * @return
   */
  public ArrayList<StationModel> getActiveStationModels() {
    ArrayList<StationModel> result = new ArrayList<StationModel>();
    
    DataBaseTableIf table = new StationTable();
    
    String[] projection = table.getDefaultProjection();
    String orderBy = table.getDefaultSortOrder();
    
    String selection = StationTable.Columns.ACTIVE + "=?";
    String[] selectionArgs = {Integer.toString(Constants.SQL_TRUE)};
    
    SQLiteDatabase sqlDb = _dbh.getReadableDatabase();
    Cursor cursor = sqlDb.query(table.getTableName(), projection, selection, selectionArgs, null, null, orderBy);  

    if (cursor.moveToFirst()) {
      do {
        StationModel stationModel = new StationModel();
        stationModel.fromCursor(cursor);
        result.add(stationModel);
      } while(cursor.moveToNext());
    }
    
    cursor.close();
    sqlDb.close();

    return(result);
  }

  /**
   * return favorite station if defined
   * @return favorite station or null
   */
  public StationModel getFavoriteStation() {
    StationModel result = null;

    UserPreferenceHelper uph = new UserPreferenceHelper(); 
    long target = uph.getFaveStation(_context);
    if (target == Constants.DEFAULT_STATION_ID) {
      LogFacade.debug(LOG_TAG, "no favorite station noted");
    } else {
      result = (StationModel) selectModel(target, new StationTable());
    }
  
    return(result);
  }

  /**
   * return latest observation for a station
   * @param callsign
   * @return
   */
  public ObservationModel getLatestObservation(String callsign) {  
    DataBaseTableIf table = new ObservationTable();
    
    String[] projection = table.getDefaultProjection();
    String orderBy = table.getDefaultSortOrder();
    
    String selection = ObservationTable.Columns.IDENTIFIER + "=?";
    String[] selectionArgs = {callsign};
    
    SQLiteDatabase sqlDb = _dbh.getReadableDatabase();
    Cursor cursor = sqlDb.query(table.getTableName(), projection, selection, selectionArgs, null, null, orderBy);

    ObservationModel result = null;

    if (cursor.moveToFirst()) {
     result = new ObservationModel();
     result.fromCursor(cursor);
    }
    
    cursor.close();
    sqlDb.close();

    return(result);
  }
  
  /**
   * return latest observation for favorite station
   * 
   * @return latest observation
   */
  public ObservationModel getLatestFaveObservation() {
    ObservationModel obs = null;
    
    UserPreferenceHelper uph = new UserPreferenceHelper();
    long target = uph.getFaveStation(_context);
    if (target == Constants.DEFAULT_STATION_ID) {
      LogFacade.debug(LOG_TAG, "no favorite station noted");
      
      obs = new ObservationModel();
      obs.setDefault(_context);
    } else {
      LogFacade.debug(LOG_TAG, "fave station:" + target);
      
      StationModel station = (StationModel) selectModel(target, new StationTable());
      LogFacade.debug(LOG_TAG, "fave station:" + station.getIdentifier());
      
      obs = getLatestObservation(station.getIdentifier());
    }
    
    return(obs);
  }
  
  /**
   * delete a station and observations
   * if target is the fave station, set fave to zero
   */
  public void deleteStationById(long target) {
    LogFacade.entry(LOG_TAG, "delete station:" + target);
    
    StationModel sm = (StationModel) selectModel(target, new StationTable());
    if (sm == null) {
      LogFacade.debug(LOG_TAG, "delete/select failure:" + target);
      return;
    }

    String selection = ObservationTable.Columns.IDENTIFIER + "=?";
    String[] selectionArgs = new String[] {sm.getIdentifier()};
   
    SQLiteDatabase sqlDb = _dbh.getWritableDatabase();
    int count = sqlDb.delete(ObservationTable.TABLE_NAME, selection, selectionArgs);
    sqlDb.close();
    
    LogFacade.exit(LOG_TAG, "delete observation count:" + count);
    
    deleteModel(sm.getId(), StationTable.TABLE_NAME);
    _context.getContentResolver().notifyChange(StationTable.CONTENT_URI, null);
   
    UserPreferenceHelper uph = new UserPreferenceHelper();
    long fave = uph.getFaveStation(_context);
    if (fave == sm.getId()) {
      uph.setFaveStation(_context, 0);
    }
  }
 
  //
  private Context _context;
  private DataBaseHelper _dbh;

  //
  public final String LOG_TAG = getClass().getName();
}

/*
 * Copyright 2011 Digital Burro, INC
 * Created on Jun 17, 2011 by gsc
 */