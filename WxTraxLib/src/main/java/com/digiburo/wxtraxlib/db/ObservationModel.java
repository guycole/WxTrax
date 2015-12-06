package com.digiburo.wxtraxlib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.text.format.Time;
import com.digiburo.wxtraxlib.Constant;
import com.digiburo.wxtraxlib.utility.Utility;

/**
 * observation container
 */
public class ObservationModel implements DataBaseModel {

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

  @Override
  public void setDefault() {
    Time timeNow = Utility.timeNow();
    timeStampMs = timeNow.toMillis(Constant.IGNORE_DST);
    timeStamp = Constant.DB_DEFAULT_TIMESTAMP;

    identifier = Constant.DB_DEFAULT_IDENTIFIER;
    pressure = Constant.DB_DEFAULT_PRESSURE;
    temperature = Constant.DB_DEFAULT_TEMPERATURE;
    visibility = Constant.DB_DEFAULT_VISIBILITY;
    weather = Constant.DB_DEFAULT_WEATHER;
    wind = Constant.DB_DEFAULT_WIND;

    location = Constant.DB_DEFAULT_LOCATION;
  }

  @Override
  public ContentValues toContentValues() {
    ContentValues cv = new ContentValues();

    cv.put(ObservationTable.Columns.IDENTIFIER, Utility.eclecticString(identifier, Constant.DB_DEFAULT_IDENTIFIER));
    cv.put(ObservationTable.Columns.PRESSURE, Utility.eclecticString(pressure, Constant.DB_DEFAULT_PRESSURE));
    cv.put(ObservationTable.Columns.TEMPERATURE, Utility.eclecticString(temperature, Constant.DB_DEFAULT_TEMPERATURE));
    cv.put(ObservationTable.Columns.TIMESTAMP, Utility.eclecticString(timeStamp, Constant.DB_DEFAULT_TIMESTAMP));
    cv.put(ObservationTable.Columns.VISIBILITY, Utility.eclecticString(visibility, Constant.DB_DEFAULT_VISIBILITY));
    cv.put(ObservationTable.Columns.WEATHER, Utility.eclecticString(weather, Constant.DB_DEFAULT_WEATHER));
    cv.put(ObservationTable.Columns.WIND, Utility.eclecticString(wind, Constant.DB_DEFAULT_WIND));
    cv.put(ObservationTable.Columns.TIMESTAMP_MS, Long.toString(timeStampMs));

    return cv;
  }
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

  @Override
  public String getTableName() {
    return ObservationTable.TABLE_NAME;
  }

  @Override
  public Uri getTableUri() {
    return ObservationTable.CONTENT_URI;
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ObservationModel that = (ObservationModel) o;

    if (Double.compare(that.latitude, latitude) != 0) return false;
    if (Double.compare(that.longitude, longitude) != 0) return false;
    if (timeStampMs != that.timeStampMs) return false;
    if (!identifier.equals(that.identifier)) return false;
    if (!location.equals(that.location)) return false;
    if (!pressure.equals(that.pressure)) return false;
    if (!temperature.equals(that.temperature)) return false;
    if (!timeStamp.equals(that.timeStamp)) return false;
    if (!visibility.equals(that.visibility)) return false;
    if (!weather.equals(that.weather)) return false;
    if (!wind.equals(that.wind)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = identifier.hashCode();
    result = 31 * result + pressure.hashCode();
    result = 31 * result + temperature.hashCode();
    result = 31 * result + timeStamp.hashCode();
    result = 31 * result + (int) (timeStampMs ^ (timeStampMs >>> 32));
    result = 31 * result + visibility.hashCode();
    result = 31 * result + weather.hashCode();
    result = 31 * result + wind.hashCode();
    result = 31 * result + location.hashCode();
    temp = Double.doubleToLongBits(latitude);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(longitude);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }
}
