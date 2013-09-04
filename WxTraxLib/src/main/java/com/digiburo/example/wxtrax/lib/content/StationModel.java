package com.digiburo.example.wxtrax.lib.content;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.digiburo.example.wxtrax.lib.utility.Constants;
import com.digiburo.example.wxtrax.lib.utility.Utility;

/**
 * station container
 *
 * @author gsc
 */
public class StationModel implements DataBaseModelIf {

  /* (non-Javadoc)
   * @see com.digiburo.example.wxtrax.content.DataBaseModelIf#setDefault(android.content.Context)
   */
  @Override
  public void setDefault(Context appContext) {
    identifier = Constants.DB_DEFAULT_IDENTIFIER;
    location = Constants.DB_DEFAULT_LOCATION;
    active = true;   
  }

  /* (non-Javadoc)
   * @see com.digiburo.example.wxtrax.content.DataBaseModelIf#toContentValues(android.content.Context)
   */
  @Override
  public ContentValues toContentValues(Context appContext) {
    ContentValues cv = new ContentValues();
    
    cv.put(StationTable.Columns.IDENTIFIER, Utility.eclecticString(identifier, Constants.DB_DEFAULT_IDENTIFIER));
    cv.put(StationTable.Columns.LOCATION, Utility.eclecticString(location, Constants.DB_DEFAULT_LOCATION));
    
    cv.put(StationTable.Columns.LATITUDE, Double.toString(latitude));
    cv.put(StationTable.Columns.LONGITUDE, Double.toString(longitude));
    
    if (active) {
      cv.put(StationTable.Columns.ACTIVE, Constants.SQL_TRUE);
    } else {
      cv.put(StationTable.Columns.ACTIVE, Constants.SQL_FALSE);
    }
    
    return(cv);
  }

  /* (non-Javadoc)
   * @see com.digiburo.example.wxtrax.content.DataBaseModelIf#fromCursor(android.database.Cursor)
   */
  @Override
  public void fromCursor(Cursor cursor) {
    id = cursor.getLong(cursor.getColumnIndex(StationTable.Columns._ID));
    identifier = cursor.getString(cursor.getColumnIndex(StationTable.Columns.IDENTIFIER));
    location = cursor.getString(cursor.getColumnIndex(StationTable.Columns.LOCATION));
    latitude = cursor.getDouble(cursor.getColumnIndex(StationTable.Columns.LATITUDE));
    longitude = cursor.getDouble(cursor.getColumnIndex(StationTable.Columns.LONGITUDE));
    setActive(cursor.getInt(cursor.getColumnIndex(StationTable.Columns.ACTIVE)));
  }

  /* (non-Javadoc)
   * @see com.digiburo.example.wxtrax.content.DataBaseModelIf#getTableName()
   */
  @Override
  public String getTableName() {
    return(StationTable.TABLE_NAME);
  }
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getIdentifier() {
    return identifier;
  }
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }
  public String getLocation() {
    return location;
  }
  public void setLocation(String location) {
    this.location = location;
  }
  public double getLatitude() {
    return latitude;
  }
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }
  public double getLongitude() {
    return longitude;
  }
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }
  public boolean isActive() {
    return active;
  }
  public void setActive(boolean active) {
    this.active = active;
  }
  public void setActive(int arg) {
    if (arg == Constants.SQL_TRUE) {
      active = true;
    } else {
      active = false;
    }
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (active ? 1231 : 1237);
    result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
    long temp;
    temp = Double.doubleToLongBits(latitude);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(longitude);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((location == null) ? 0 : location.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    StationModel other = (StationModel) obj;
    if (active != other.active)
      return false;
    if (identifier == null) {
      if (other.identifier != null)
        return false;
    } else if (!identifier.equals(other.identifier))
      return false;
    if (Double.doubleToLongBits(latitude) != Double
        .doubleToLongBits(other.latitude))
      return false;
    if (Double.doubleToLongBits(longitude) != Double
        .doubleToLongBits(other.longitude))
      return false;
    if (location == null) {
      if (other.location != null)
        return false;
    } else if (!location.equals(other.location))
      return false;
    return true;
  }

  private Long id;
  private String identifier;
  private String location;
  private double latitude;
  private double longitude;
  private boolean active;
}

/*
 * Copyright 2011 Digital Burro, INC
 * Created on Jul 17, 2011 by gsc
 */