package com.digiburo.wxtraxlib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.digiburo.wxtraxlib.Constant;
import com.digiburo.wxtraxlib.utility.Utility;

/**
 * station container
 */
public class StationModel implements DataBaseModel {
    private Long id;
    private String identifier;
    private String location;
    private double latitude;
    private double longitude;
    private boolean active;

    @Override
    public void setDefault() {
        identifier = Constant.DB_DEFAULT_IDENTIFIER;
        location = Constant.DB_DEFAULT_LOCATION;
        active = true;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(StationTable.Columns.IDENTIFIER, Utility.eclecticString(identifier, Constant.DB_DEFAULT_IDENTIFIER));
        cv.put(StationTable.Columns.LOCATION, Utility.eclecticString(location, Constant.DB_DEFAULT_LOCATION));

        cv.put(StationTable.Columns.LATITUDE, Double.toString(latitude));
        cv.put(StationTable.Columns.LONGITUDE, Double.toString(longitude));

        if (active) {
            cv.put(StationTable.Columns.ACTIVE, Constant.SQL_TRUE);
        } else {
            cv.put(StationTable.Columns.ACTIVE, Constant.SQL_FALSE);
        }

        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(StationTable.Columns._ID));
        identifier = cursor.getString(cursor.getColumnIndex(StationTable.Columns.IDENTIFIER));
        location = cursor.getString(cursor.getColumnIndex(StationTable.Columns.LOCATION));
        latitude = cursor.getDouble(cursor.getColumnIndex(StationTable.Columns.LATITUDE));
        longitude = cursor.getDouble(cursor.getColumnIndex(StationTable.Columns.LONGITUDE));
        setActive(cursor.getInt(cursor.getColumnIndex(StationTable.Columns.ACTIVE)));
    }

    @Override
    public String getTableName() {
        return StationTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return StationTable.CONTENT_URI;
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
        if (arg == Constant.SQL_TRUE) {
            active = true;
        } else {
            active = false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StationModel that = (StationModel) o;

        if (active != that.active) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (!identifier.equals(that.identifier)) return false;
        if (!location.equals(that.location)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = identifier.hashCode();
        result = 31 * result + location.hashCode();
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (active ? 1 : 0);
        return result;
    }
}
