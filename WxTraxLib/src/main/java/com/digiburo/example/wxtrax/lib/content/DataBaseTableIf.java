package com.digiburo.example.wxtrax.lib.content;

/**
 * Common parent for tables
 *
 * @author gsc
 */
public interface DataBaseTableIf {
  
  /**
   * return associated table name
   * @return associated table name
   */
  public String getTableName();
  
  /**
   * return default sort order
   * @return default sort order
   */
  public String getDefaultSortOrder();
  
  /**
   * return default projection (column names)
   * @return return default projection (column names)
   */
  public String[] getDefaultProjection();
}

/*
 * Copyright 2011 Digital Burro, INC
 * Created on Sep 22, 2011 by gsc
 */