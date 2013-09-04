package com.digiburo.example.wxtrax.lib.content;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.digiburo.example.wxtrax.lib.utility.LogFacade;

/**
 * database helper
 *
 * @author gsc
 */
public class DataBaseHelper extends SQLiteOpenHelper {
  
  //
  public DataBaseHelper(Context context) {
    super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
  }

  /* (non-Javadoc)
   * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
   */
  @Override
  public void onCreate(SQLiteDatabase db) {
    LogFacade.entry(LOG_TAG, "onCreate");
    db.execSQL(StationTable.CREATE_TABLE);
    db.execSQL(ObservationTable.CREATE_TABLE);
  }

  /* (non-Javadoc)
   * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
   */
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    LogFacade.entry(LOG_TAG, "onUpgrade");
  }
  
  public static final String DATABASE_FILE_NAME = "wxtrax.db";
  public static final int DATABASE_VERSION = 1;
  
  //
  public final String LOG_TAG = getClass().getName();
}

/*
 * Copyright 2010 Digital Burro, INC
 * Created on Apr 15, 2010 by gsc
 */