package com.digiburo.example.wxtrax2.ui;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.digiburo.example.wxtrax.lib.utility.LogFacade;
import com.digiburo.example.wxtrax2.R;

/**
 * React to ActionBar tab events
 */
public class TabHelper implements ActionBar.TabListener, FragmentManager.OnBackStackChangedListener {

  public TabHelper(MainActivity activity) {
    mainActivity = activity;

    chartFragment = (ChartFragment) Fragment.instantiate(mainActivity, ChartFragment.class.getName());
    splashFragment = (SplashFragment) Fragment.instantiate(mainActivity, SplashFragment.class.getName());
    stationListFragment = (StationListFragment) Fragment.instantiate(mainActivity, StationListFragment.class.getName());
  }

  /**
   * ActionBar.TabListener
   * @param tab
   * @param fragmentTransaction
   */
  @Override
  public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    LogFacade.entry(LOG_TAG, "onTabSelected:" + tab.getTag() + ":" + tab.getPosition());

    if (ignoreMe) {
      //spurious event caused by changing navigation mode within onStateDeselect()
      ignoreMe = false;
      return;
    }

    if (tab.getTag().equals(TAG_CHART)) {
      fragmentTransaction.add(R.id.layoutFragment01, chartFragment, TAG_CHART);
    } else if (tab.getTag().equals(TAG_SPLASH)) {
      fragmentTransaction.add(R.id.layoutFragment01, splashFragment, TAG_SPLASH);
    } else if (tab.getTag().equals(TAG_STATION_LIST)) {
      fragmentTransaction.add(R.id.layoutFragment01, stationListFragment, TAG_STATION_LIST);
    } else {
      throw new IllegalArgumentException("unknown tab:" + tab.getTag());
    }

    LogFacade.exit(LOG_TAG, "onTabSelected:" + tab.getTag());
  }

  /**
   * ActionBar.TabListener
   * @param tab
   * @param fragmentTransaction
   */
  @Override
  public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    LogFacade.entry(LOG_TAG, "onTabUnselected:" + tab.getTag());

    if (tab.getTag().equals(TAG_CHART)) {
      fragmentTransaction.remove(chartFragment);
    } else  if (tab.getTag().equals(TAG_SPLASH)) {
      fragmentTransaction.remove(splashFragment);
    } else if (tab.getTag().equals(TAG_STATION_LIST)) {
      fragmentTransaction.remove(stationListFragment);
    } else {
      throw new IllegalArgumentException("unknown tab:" + tab.getTag());
    }

    LogFacade.exit(LOG_TAG, "onTabUnselected:" + tab.getTag());
  }

  /**
   * ActionBar.TabListener
   * @param tab
   * @param fragmentTransaction
   */
  @Override
  public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    LogFacade.entry(LOG_TAG, "onTabReselected:" + tab.getTag());

    if (tab.getTag().equals(TAG_CHART)) {
      //empty
    } else if (tab.getTag().equals(TAG_SPLASH)) {
      //empty
    } else if (tab.getTag().equals(TAG_STATION_LIST)) {
      //empty
    } else {
      throw new IllegalArgumentException("unknown tab:" + tab.getTag());
    }

    LogFacade.exit(LOG_TAG, "onTabReselected:" + tab.getTag());
  }

  /**
   * FragmentManager.OnBackStackChangedListener
   */
  public void onBackStackChanged() {
    LogFacade.entry(LOG_TAG, "onBackStackChanged");
    
    FragmentManager fragmentManager = mainActivity.getFragmentManager();
    int backStackCount = fragmentManager.getBackStackEntryCount();
    LogFacade.debug(LOG_TAG,  "back stack count:" + backStackCount);
    
    ActionBar actionBar = mainActivity.getActionBar();
    
    if (backStackCount == 0) {
      // restore tabs
      ignoreMe = true;
      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
      mainActivity.clearCurrentStation();
    } else {
      // hide tabs
      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }
    
    LogFacade.exit(LOG_TAG, "onBackStackChanged");
  }

  /**
   * populate ActionBar
   */
  public void initialize() {
    LogFacade.entry(LOG_TAG, "initialize");

    FragmentManager fragmentManager = mainActivity.getFragmentManager();
    fragmentManager.addOnBackStackChangedListener(this);

    ActionBar actionBar = mainActivity.getActionBar();

    chartTab = actionBar.newTab();
    splashTab = actionBar.newTab();
    stationListTab = actionBar.newTab();

    chartTab.setTabListener(this);
    splashTab.setTabListener(this);
    stationListTab.setTabListener(this);

    chartTab.setTag(TAG_CHART);
    splashTab.setTag(TAG_SPLASH);
    stationListTab.setTag(TAG_STATION_LIST);

    chartTab.setText(R.string.menu_application_bar_map);
    splashTab.setText(R.string.menu_application_bar_splash);
    stationListTab.setText(R.string.menu_application_bar_station_list);

    actionBar.addTab(splashTab);
    actionBar.addTab(stationListTab);
    actionBar.addTab(chartTab);

    LogFacade.exit(LOG_TAG, "initialize");
  }

  /**
   * Map a tag to ActionBar.Tab
   * @param arg
   * @return related tab
   */
  public ActionBar.Tab tagToTab(String arg) {
    if (arg.equals(TAG_CHART)) {
      return(chartTab);
    } else if (arg.equals(TAG_SPLASH)) {
      return(splashTab);
    } else if (arg.equals(TAG_STATION_LIST)) {
      return(stationListTab);
    }

    throw new IllegalArgumentException("unsupported tag:" + arg);
  }

  /**
   * Station has been selected, hide tabs and switch to observation list
   * @param rowId within station table
   * @param tabTag
   */
  public void onStationSelect(Long rowId, String tabTag) {
    observationListFragment = new ObservationListFragment();
    observationListFragment.setStationRowId(rowId);

    FragmentManager fragmentManager = mainActivity.getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    if (tabTag.equals(TAG_SPLASH)) {
      fragmentTransaction.remove(splashFragment);
    } else if (tabTag.equals(TAG_STATION_LIST)) {
      fragmentTransaction.remove(stationListFragment);
    } else if (tabTag.equals(TAG_CHART)) {
      fragmentTransaction.remove(chartFragment);    
    }

    fragmentTransaction.add(R.id.layoutFragment01, observationListFragment, TAG_OBSERVATION_LIST);

    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }
  
  //
  private boolean ignoreMe = false;

  //
  private MainActivity mainActivity;

  //
  private ActionBar.Tab chartTab;
  private ActionBar.Tab splashTab;
  private ActionBar.Tab stationListTab;

  //
  private ChartFragment chartFragment;
  private ObservationListFragment observationListFragment;
  private SplashFragment splashFragment;
  private StationListFragment stationListFragment;

  //
  public static final String TAG_CHART = "TAG_CHART";
  public static final String TAG_OBSERVATION_LIST = "TAG_OBSERVATION_LIST";
  public static final String TAG_SPLASH = "TAG_SPLASH";
  public static final String TAG_STATION_LIST = "TAG_STATION_LIST";

  //
  public static final String LOG_TAG = TabHelper.class.getName();
}

/*
 * Copyright 2013 Digital Burro, INC
 * Created on Jan 5, 2013 by gsc
 */
