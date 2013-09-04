package com.digiburo.example.wxtrax2.ui;

/**
 * bridge between MainActivity and StationListFragment
 */
public interface StationListener {

  /**
   * Display observation list fragment
   * @param rowId within StationTable
   */
  void onStationSelect(Long rowId, String tabTag);

  /**
   * display delete dialog
   * @param rowId within stationTable
   */
  void displayStationDeleteDialog(long rowId);

  /**
   * perform delete
   */
  void onStationDeleteYes();

  /**
   * cancel delete
   */
  void onStationDeleteNo();
}
/*
 * Copyright 2013 Digital Burro, INC
 * Created on Jan 5, 2013 by gsc
 */