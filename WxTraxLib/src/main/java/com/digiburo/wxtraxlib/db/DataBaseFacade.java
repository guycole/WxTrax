package com.digiburo.wxtraxlib.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.digiburo.wxtraxlib.Constant;
import com.digiburo.wxtraxlib.utility.UserPreferenceHelper;

import java.util.ArrayList;

public class DataBaseFacade {
    private Context context;
    private DataBaseHelper dbh;

    /**
     *
     * @param appContext
     */
    public DataBaseFacade(Context appContext) {
        context = appContext;
        dbh = new DataBaseHelper(appContext);
    }

    /**
     * Delete the specified row from table
     *
     * @param rowId
     * @param tableName
     */
    public void deleteModel(Long rowId, String tableName) {
        String selection = "_id=?";
        String selectionArgs[] = new String[] {rowId.toString()};

        SQLiteDatabase sqlDb = dbh.getWritableDatabase();
        int count = sqlDb.delete(tableName, selection, selectionArgs);
        sqlDb.close();
    }

    /**
     * insert/update a database table from model
     *
     * @param arg populated model
     * @return true is success
     */
    public boolean updateModel(DataBaseModel arg) {
        boolean flag = false;

        SQLiteDatabase sqlDb = dbh.getWritableDatabase();

        if ((arg.getId() == null) || (arg.getId() < 1)) {
            //insert
            long result = sqlDb.insert(arg.getTableName(), null, arg.toContentValues());
            if (result > 0) {
                arg.setId(result);
                flag = true;
            }
        } else {
            //update
            String selection = "_id=?";
            String[] selectionArgs = new String[] {arg.getId().toString()};
            int result = sqlDb.update(arg.getTableName(), arg.toContentValues(), selection, selectionArgs);
            if (result > 0) {
                flag = true;
            }
        }

        sqlDb.close();

        return flag;
    }

    /**
     * Select a database row, return as model
     *
     * @param rowId target row ID
     * @param dbTable target table
     * @return
     */
    public DataBaseModel selectModel(Long rowId, DataBaseTable dbTable) {
        DataBaseModel result = null;

        String tableName = dbTable.getTableName();
        String[] projection = dbTable.getDefaultProjection();

        String selection = "_id=?";
        String[] selectionArgs = new String[] {rowId.toString()};

        SQLiteDatabase sqlDb = dbh.getReadableDatabase();
        Cursor cursor = sqlDb.query(tableName, projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            result = cursorToModel(cursor, tableName);
        }

        cursor.close();
        sqlDb.close();

        return result;
    }

    /**
     * Map cursor to model
     *
     * @param cursor
     * @param tableName
     * @return populated model
     */
    private DataBaseModel cursorToModel(Cursor cursor, String tableName) {
        DataBaseModel result = null;

        if (tableName.equals(ObservationTable.TABLE_NAME)) {
            result = new ObservationModel();
        } else if (tableName.equals(StationTable.TABLE_NAME)) {
            result = new StationModel();
        } else {
            throw new IllegalArgumentException("unknown table:" + tableName);
        }

        result.fromCursor(cursor);

        return result;
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

        SQLiteDatabase sqlDb = dbh.getReadableDatabase();
        Cursor cursor = sqlDb.query(StationTable.TABLE_NAME, table.getDefaultProjection(), selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            result = new StationModel();
            result.fromCursor(cursor);
        }

        cursor.close();
        sqlDb.close();

        return result;
    }

    /**
     * Insert a new station
     *
     * @param fresh observation
     * @return true is success
     */
    public boolean newStation(ObservationModel observation) {
        if ((observation.getIdentifier() == null) || (observation.getIdentifier().equals(Constant.DB_DEFAULT_IDENTIFIER))) {
            return false;
        }

        if ((observation.getLocation() == null) || (observation.getLocation().equals(Constant.DB_DEFAULT_LOCATION))) {
            return false;
        }

        StationModel sm = getStationByIdentifier(observation.getIdentifier());
        if (sm != null) {
            return false;
        }

        sm = new StationModel();
        sm.setDefault();
        sm.setIdentifier(observation.getIdentifier());
        sm.setLocation(observation.getLocation());
        sm.setLatitude(observation.getLatitude());
        sm.setLongitude(observation.getLongitude());
        sm.setActive(true);

        boolean flag = updateModel(sm);
        if (flag) {
            context.getContentResolver().notifyChange(StationTable.CONTENT_URI, null);
        }

        return flag;
    }

    /**
     * load a new observation into the table
     * @param model populated model
     * @return true if success
     */
    public boolean newObservation(ObservationModel model) {
        boolean flag = false;

        if (model.getTimeStampMs() < 1) {
            return flag;
        }

        //test for duplicate row
        String selection = ObservationTable.Columns.IDENTIFIER + "=? and " + ObservationTable.Columns.TIMESTAMP_MS + "=?";
        String[] selectionArgs = new String[] {model.getIdentifier(), Long.toString(model.getTimeStampMs())};

        ObservationTable table = new ObservationTable();

        SQLiteDatabase sqlDb = dbh.getReadableDatabase();
        Cursor cursor = sqlDb.query(ObservationTable.TABLE_NAME, table.getDefaultProjection(), selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            flag = false;
        } else {
            flag = updateModel(model);
            if (flag) {
                context.getContentResolver().notifyChange(ObservationTable.CONTENT_URI, null);
            }
        }

        cursor.close();
        sqlDb.close();

        return flag;
    }

    /**
     * return a list of all active stations
     * @return a list of all active stations
     */
    public ArrayList<String> getActiveStations() {
        ArrayList<String> result = new ArrayList<String>();

        DataBaseTable table = new StationTable();

        String[] projection = table.getDefaultProjection();
        String orderBy = table.getDefaultSortOrder();

        String selection = StationTable.Columns.ACTIVE + "=?";
        String[] selectionArgs = {Integer.toString(Constant.SQL_TRUE)};

        SQLiteDatabase sqlDb = dbh.getReadableDatabase();
        Cursor cursor = sqlDb.query(table.getTableName(), projection, selection, selectionArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(cursor.getColumnIndex(StationTable.Columns.IDENTIFIER)));
            } while(cursor.moveToNext());
        }

        cursor.close();
        sqlDb.close();

        return result;
    }

    /**
     *
     * @return
     */
    public ArrayList<StationModel> getActiveStationModels() {
        ArrayList<StationModel> result = new ArrayList<StationModel>();

        DataBaseTable table = new StationTable();

        String[] projection = table.getDefaultProjection();
        String orderBy = table.getDefaultSortOrder();

        String selection = StationTable.Columns.ACTIVE + "=?";
        String[] selectionArgs = {Integer.toString(Constant.SQL_TRUE)};

        SQLiteDatabase sqlDb = dbh.getReadableDatabase();
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

        return result;
    }

    /**
     * return favorite station if defined
     * @return favorite station or null
     */
    public StationModel getFavoriteStation() {
        StationModel result = null;

        UserPreferenceHelper uph = new UserPreferenceHelper();
        long target = uph.getFaveStation(context);
        if (target != Constant.DEFAULT_STATION_ID) {
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
        DataBaseTable table = new ObservationTable();

        String[] projection = table.getDefaultProjection();
        String orderBy = table.getDefaultSortOrder();

        String selection = ObservationTable.Columns.IDENTIFIER + "=?";
        String[] selectionArgs = {callsign};

        SQLiteDatabase sqlDb = dbh.getReadableDatabase();
        Cursor cursor = sqlDb.query(table.getTableName(), projection, selection, selectionArgs, null, null, orderBy);

        ObservationModel result = null;

        if (cursor.moveToFirst()) {
            result = new ObservationModel();
            result.fromCursor(cursor);
        }

        cursor.close();
        sqlDb.close();

        return result;
    }

    /**
     * return latest observation for favorite station
     *
     * @return latest observation
     */
    public ObservationModel getLatestFaveObservation() {
        ObservationModel obs = null;

        UserPreferenceHelper uph = new UserPreferenceHelper();
        long target = uph.getFaveStation(context);
        if (target == Constant.DEFAULT_STATION_ID) {
            obs = new ObservationModel();
            obs.setDefault();
        } else {
            StationModel station = (StationModel) selectModel(target, new StationTable());
            obs = getLatestObservation(station.getIdentifier());
        }

        return obs;
    }

    /**
     * delete a station and observations
     * if target is the fave station, set fave to zero
     */
    public void deleteStationById(long target) {
        StationModel sm = (StationModel) selectModel(target, new StationTable());
        if (sm == null) {
            return;
        }

        String selection = ObservationTable.Columns.IDENTIFIER + "=?";
        String[] selectionArgs = new String[] {sm.getIdentifier()};

        SQLiteDatabase sqlDb = dbh.getWritableDatabase();
        int count = sqlDb.delete(ObservationTable.TABLE_NAME, selection, selectionArgs);
        sqlDb.close();

        deleteModel(sm.getId(), StationTable.TABLE_NAME);
        context.getContentResolver().notifyChange(StationTable.CONTENT_URI, null);

        UserPreferenceHelper uph = new UserPreferenceHelper();
        long fave = uph.getFaveStation(context);
        if (fave == sm.getId()) {
            uph.setFaveStation(context, 0);
        }
    }
}
