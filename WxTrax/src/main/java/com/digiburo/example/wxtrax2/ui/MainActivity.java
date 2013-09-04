package com.digiburo.example.wxtrax2.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.digiburo.example.wxtrax.lib.content.DataBaseFacade;
import com.digiburo.example.wxtrax.lib.content.ObservationModel;
import com.digiburo.example.wxtrax.lib.utility.LegalOptionMenuType;
import com.digiburo.example.wxtrax.lib.utility.LogFacade;
import com.digiburo.example.wxtrax.lib.utility.WxXmlCollection;
import com.digiburo.example.wxtrax2.R;

/**
 *
 */
public class MainActivity extends Activity implements StationListener {

  /**
   * Display observation list fragment
   * @param rowId station rowId
   * @param tabTag parent tab
   */
  public void onStationSelect(Long rowId, String tabTag) {
    LogFacade.entry(LOG_TAG, "onStationSelect:" + rowId + ":" + tabTag);
    selectedStationId = rowId;
    tabHelper.onStationSelect(rowId, tabTag);
    LogFacade.exit(LOG_TAG, "onStationSelect");
  }
  
  /**
   * reset the current station
   * necessary for successful device orientation changes
   */
  public void clearCurrentStation() {
    selectedStationId = -1L;
  }

  /**
   * display delete dialog
   * @param rowId within stationTable
   */
  public void displayStationDeleteDialog(long rowId) {
    LogFacade.entry(LOG_TAG, "createStationDeleteDialog");
    
    deleteStationId = rowId;
    DeleteDialogFragment ddf = DeleteDialogFragment.newInstance(R.string.dialog_delete_station_title, R.string.dialog_delete_station_message);
    ddf.show(getFragmentManager(), "deleteDialog");

    LogFacade.exit(LOG_TAG, "createStationDeleteDialog");
  }
  
  public void onStationDeleteYes() {
    LogFacade.entry(LOG_TAG, "onStationDeleteYes");

    DataBaseFacade dbf = new DataBaseFacade(this);
    dbf.deleteStationById(deleteStationId);
    deleteStationId = -1L;

    LogFacade.exit(LOG_TAG, "onStationDeleteYes");
  }

  /**
   * cancel delete
   */
  public void onStationDeleteNo() {
    LogFacade.entry(LOG_TAG, "onStationDeleteNo");
    deleteStationId = -1L;
    LogFacade.exit(LOG_TAG, "onStationDeleteNo");
  }

  /**
   * add new station
   */
  public void onStationAddYes(String target) {
    LogFacade.entry(LOG_TAG, "onStationAddYes");

    if ((target != null) && (target.length() > 0)) {
      WxXmlCollection wxc = new WxXmlCollection();
      ObservationModel rawObservation = wxc.performCollection(this, target);

      if (rawObservation == null) {
        Toast.makeText(this, getString(R.string.error_unknown_station), Toast.LENGTH_SHORT).show();
        return;
      }

      DataBaseFacade dbf = new DataBaseFacade(this);
      if (!dbf.newStation(rawObservation)) {
        Toast.makeText(this, getString(R.string.error_duplicate_station), Toast.LENGTH_SHORT).show();
        return;
      }

      Toast.makeText(this, getString(R.string.toast_new_station_success), Toast.LENGTH_SHORT).show();

      dbf.newObservation(rawObservation);
    }

    LogFacade.exit(LOG_TAG, "onStationAddYes");
  }

  /**
   * cancel new station add
   */
  public void onStationAddNo() {
    LogFacade.entry(LOG_TAG, "onStationAddNo");
    LogFacade.exit(LOG_TAG, "onStationAddNo");
  }

  private void displayAddStationDialog() {
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    Fragment oldFragment = getFragmentManager().findFragmentByTag(ADD_STATION_DIALOG);
    if (oldFragment != null) {
      fragmentTransaction.remove(oldFragment);
    }
    AddStationDialogFragment asdf = AddStationDialogFragment.newInstance();
    asdf.show(fragmentTransaction, ADD_STATION_DIALOG);
  }

  @Override
  protected void onStart() {
    super.onStart();
    LogFacade.entry(LOG_TAG, "onStart");
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    LogFacade.entry(LOG_TAG, "onRestart");
  }

  @Override
  protected void onResume() {
    super.onResume();
    LogFacade.entry(LOG_TAG, "onResume");
  }

  @Override
  protected void onPause() {
    super.onPause();
    LogFacade.entry(LOG_TAG, "onPause");
  }

  @Override
  protected void onStop() {
    super.onStop();
    LogFacade.entry(LOG_TAG, "onStop");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    LogFacade.entry(LOG_TAG, "onDestroy");
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    LogFacade.entry(LOG_TAG, "onCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ActionBar actionBar = getActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

    tabHelper = new TabHelper(this);
    tabHelper.initialize();
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    LogFacade.entry(LOG_TAG, "onRestoreInstanceState:" + savedInstanceState.size());
//    Set<String> keys = savedInstanceState.keySet();
//    for (String key:keys) {
//      System.out.println(key);
//    }
    
    int tabNdx = savedInstanceState.getInt(SELECTED_TAB_NDX, -1);
    long stationRowId = savedInstanceState.getLong(SELECTED_STATION_ID, -1);
    long deleteRowId = savedInstanceState.getLong(DELETE_STATION_ID, -1);
 
    if (tabNdx != -1) {
      // must select a tab
      getActionBar().setSelectedNavigationItem(tabNdx);
    }
    
    if (stationRowId > 0) {
      // must display station observations
      ActionBar.Tab target = tabHelper.tagToTab(TabHelper.TAG_STATION_LIST);
      target.select();
      
      onStationSelect(stationRowId, TabHelper.TAG_STATION_LIST);
    }
    
    if (deleteRowId > 0) {
      // must display station observations
      ActionBar.Tab target = tabHelper.tagToTab(TabHelper.TAG_STATION_LIST);
      target.select();
      
      displayStationDeleteDialog(deleteRowId);
    }
  }

  /**
   * invoked during device orientation change
   * @param outState
   */
  @Override
  public void onSaveInstanceState(Bundle outState) {
    LogFacade.entry(LOG_TAG, "onSaveInstanceState");
    outState.putInt(SELECTED_TAB_NDX, getActionBar().getSelectedNavigationIndex());
    outState.putLong(SELECTED_STATION_ID, selectedStationId);
    outState.putLong(DELETE_STATION_ID, deleteStationId);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    LogFacade.entry(LOG_TAG, "onOptionsItemSelected:" + item.getItemId());

    Intent intent = new Intent(this, MenuActivity.class);

    switch (item.getItemId()) {
      case R.id.menu_about:
        intent.setAction(LegalOptionMenuType.ABOUT.getName());
        break;
      case R.id.menu_add_station:
        displayAddStationDialog();
        break;
      case R.id.menu_preference:
        intent.setAction(LegalOptionMenuType.PREFERENCE.getName());
        break;
      default:
        return super.onOptionsItemSelected(item);
    }

    startActivity(intent);
    return(true);
  }

  //
  private TabHelper tabHelper;
  
  //
  private Long deleteStationId = -1L;
  private Long selectedStationId = -1L;
 
  //
  public static final String SELECTED_TAB_NDX = "SELECTED_TAB_NDX";
  public static final String SELECTED_STATION_ID = "SELECTED_STATION_ID";
  public static final String DELETE_STATION_ID = "DELETE_STATION_ID";

  //
  public static final String ADD_STATION_DIALOG = "ADD_STATION_DIALOG";

  //
  public static final String LOG_TAG = MainActivity.class.getName();
}
/*
 * Copyright 2013 Digital Burro, INC
 * Created on Jan 5, 2013 by gsc
 */
