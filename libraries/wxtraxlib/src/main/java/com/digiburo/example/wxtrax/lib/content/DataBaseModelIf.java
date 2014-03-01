package com.digiburo.example.wxtrax.lib.content;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Models act as containers, bridge between database and code
 * 
 * Children should implement equals()/hashCode() without comparing row ID
 *
 * @author gsc
 */
public interface DataBaseModelIf {

  /**
   * set reasonable model defaults
   * 
   * @param appContext needed for I18N/L10N string conversion
   */
  public void setDefault(Context appContext);
  
  /**
   * load content from model
   * 
   * @param appContext needed for I18N/L10N string conversion
   * @return populated values
   */
  public ContentValues toContentValues(Context appContext);
  
  /**
   * convert from cursor to model
   * @param cursor points to model datum
   */
  public void fromCursor(Cursor cursor);

  /**
   * return associated table name
   * @return associated table name
   */
  public String getTableName();
  
  //for BaseColumns
  public Long getId();
  public void setId(Long id);
}

/*
 * Copyright 2011 Digital Burro, INC
 * Created on Sep 22, 2011 by gsc
 */