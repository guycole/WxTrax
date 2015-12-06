package com.digiburo.wxtraxlib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * database helper
 */
public class DataBaseHelper extends SQLiteOpenHelper {
  public static final String DATABASE_FILE_NAME = "wxtrax.db";
  public static final int DATABASE_VERSION = 1;

  public DataBaseHelper(Context context) {
    super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
  }

  /* (non-Javadoc)
   * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
   */
  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(StationTable.CREATE_TABLE);
    db.execSQL(ObservationTable.CREATE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //empty
  }
}
