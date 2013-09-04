package com.digiburo.example.wxtrax.lib.content;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.format.Time;

import com.digiburo.example.wxtrax.lib.utility.Constants;
import com.digiburo.example.wxtrax.lib.utility.Utility;

/**
 * observation container
 *
 * @author gsc
 */
public class ObservationModel implements DataBaseModelIf {

  /* (non-Javadoc)
   * @see com.digiburo.example.wxtrax.content.DataBaseModelIf#setDefault(android.content.Context)
   */
  @Override
  public void setDefault(Context appContext) {
    Time timeNow = Utility.timeNow();
    timeStampMs = timeNow.toMillis(Constants.IGNORE_DST);
    timeStamp = Constants.DB_DEFAULT_TIMESTAMP;

    identifier = Constants.DB_DEFAULT_IDENTIFIER;
    pressure = Constants.DB_DEFAULT_PRESSURE;
    temperature = Constants.DB_DEFAULT_TEMPERATURE;
    visibility = Constants.DB_DEFAULT_VISIBILITY;
    weather = Constants.DB_DEFAULT_WEATHER;
    wind = Constants.DB_DEFAULT_WIND;
    
    location = Constants.DB_DEFAULT_LOCATION;
  }

  /* (non-Javadoc)
   * @see com.digiburo.example.wxtrax.content.DataBaseModelIf#toContentValues(android.content.Context)
   */
  @Override
  public ContentValues toContentValues(Context appContext) {
    ContentValues cv = new ContentValues();
    
    cv.put(ObservationTable.Columns.IDENTIFIER, Utility.eclecticString(identifier, Constants.DB_DEFAULT_IDENTIFIER));
    cv.put(ObservationTable.Columns.PRESSURE, Utility.eclecticString(pressure, Constants.DB_DEFAULT_PRESSURE));
    cv.put(ObservationTable.Columns.TEMPERATURE, Utility.eclecticString(temperature, Constants.DB_DEFAULT_TEMPERATURE));
    cv.put(ObservationTable.Columns.TIMESTAMP, Utility.eclecticString(timeStamp, Constants.DB_DEFAULT_TIMESTAMP));
    cv.put(ObservationTable.Columns.VISIBILITY, Utility.eclecticString(visibility, Constants.DB_DEFAULT_VISIBILITY));
    cv.put(ObservationTable.Columns.WEATHER, Utility.eclecticString(weather, Constants.DB_DEFAULT_WEATHER));
    cv.put(ObservationTable.Columns.WIND, Utility.eclecticString(wind, Constants.DB_DEFAULT_WIND));
    cv.put(ObservationTable.Columns.TIMESTAMP_MS, Long.toString(timeStampMs));

    return(cv);
  }

  /* (non-Javadoc)
   * @see com.digiburo.example.wxtrax.content.DataBaseModelIf#fromCursor(android.database.Cursor)
   */
  @Override
  public void fromCursor(Cursor cursor) {
    id = cursor.getLong(cursor.getColumnIndex(ObservationTable.Columns._ID));
    identifier = cursor.getString(cursor.getColumnIndex(ObservationTable.Columns.IDENTIFIER));
    pressure = cursor.getString(cursor.getColumnIndex(ObservationTable.Columns.PRESSURE));
    temperature = cursor.getString(cursor.getColumnIndex(ObservationTable.Columns.TEMPERATURE));
    timeStamp = cursor.getString(cursor.getColumnIndex(ObservationTable.Columns.TIMESTAMP));
    visibility = cursor.getString(cursor.getColumnIndex(ObservationTable.Columns.VISIBILITY));
    weather = cursor.getString(cursor.getColumnIndex(ObservationTable.Columns.WEATHER));
    wind = cursor.getString(cursor.getColumnIndex(ObservationTable.Columns.WIND));
    timeStampMs = cursor.getLong(cursor.getColumnIndex(ObservationTable.Columns.TIMESTAMP_MS));
  }

  /* (non-Javadoc)
   * @see com.digiburo.example.wxtrax.content.DataBaseModelIf#getTableName()
   */
  @Override
  public String getTableName() {
    return(ObservationTable.TABLE_NAME);
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
  public String getPressure() {
    return pressure;
  }
  public void setPressure(String pressure) {
    this.pressure = pressure;
  }
  public String getTemperature() {
    return temperature;
  }
  public void setTemperature(String temperature) {
    this.temperature = temperature;
  }
  public String getTimeStamp() {
    return timeStamp;
  }
  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }
  public long getTimeStampMs() {
    return timeStampMs;
  }
  public void setTimeStampMs(long timeStampMs) {
    this.timeStampMs = timeStampMs;
  }
  public String getVisibility() {
    return visibility;
  }
  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }
  public String getWeather() {
    return weather;
  }
  public void setWeather(String weather) {
    this.weather = weather;
  }
  public String getWind() {
    return wind;
  }
  public void setWind(String wind) {
    this.wind = wind;
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
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
    result = prime * result + ((pressure == null) ? 0 : pressure.hashCode());
    result = prime * result + ((temperature == null) ? 0 : temperature.hashCode());
    result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
    result = prime * result + (int) (timeStampMs ^ (timeStampMs >>> 32));
    result = prime * result + ((visibility == null) ? 0 : visibility.hashCode());
    result = prime * result + ((weather == null) ? 0 : weather.hashCode());
    result = prime * result + ((wind == null) ? 0 : wind.hashCode());
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
    ObservationModel other = (ObservationModel) obj;
    if (identifier == null) {
      if (other.identifier != null)
        return false;
    } else if (!identifier.equals(other.identifier))
      return false;
    if (pressure == null) {
      if (other.pressure != null)
        return false;
    } else if (!pressure.equals(other.pressure))
      return false;
    if (temperature == null) {
      if (other.temperature != null)
        return false;
    } else if (!temperature.equals(other.temperature))
      return false;
    if (timeStamp == null) {
      if (other.timeStamp != null)
        return false;
    } else if (!timeStamp.equals(other.timeStamp))
      return false;
    if (timeStampMs != other.timeStampMs)
      return false;
    if (visibility == null) {
      if (other.visibility != null)
        return false;
    } else if (!visibility.equals(other.visibility))
      return false;
    if (weather == null) {
      if (other.weather != null)
        return false;
    } else if (!weather.equals(other.weather))
      return false;
    if (wind == null) {
      if (other.wind != null)
        return false;
    } else if (!wind.equals(other.wind))
      return false;
    return true;
  }

  private Long id;
  private String identifier;
  private String pressure;
  private String temperature;
  private String timeStamp;
  private long timeStampMs;
  private String visibility;
  private String weather;
  private String wind;
  
  //not stored
  private String location;
  private double latitude;
  private double longitude;
}

/*
 * Copyright 2011 Digital Burro, INC
 * Created on Jul 17, 2011 by gsc
 */