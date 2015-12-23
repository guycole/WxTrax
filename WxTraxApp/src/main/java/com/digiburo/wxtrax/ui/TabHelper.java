package com.digiburo.wxtrax.ui;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.digiburo.wxtrax.R;
import com.digiburo.wxtrax.audio.AudioFacade;

/**
 *
 */
public class TabHelper implements ActionBar.TabListener, FragmentManager.OnBackStackChangedListener {

  //
  private boolean ignoreMe = false;

  //
  private MainActivity mainActivity;

  //
  private ActionBar.Tab splashTab;
  private ActionBar.Tab stationListTab;

  //
  private ObservationListFragment observationListFragment;
  private SplashFragment splashFragment;
  private StationListFragment stationListFragment;

  public static final AudioFacade audioFacade = new AudioFacade();

  //
  public static final String TAG_OBSERVATION_LIST = "TAG_OBSERVATION_LIST";
  public static final String TAG_SPLASH = "TAG_SPLASH";
  public static final String TAG_STATION_LIST = "TAG_STATION_LIST";

  public TabHelper(MainActivity activity) {
    mainActivity = activity;
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
    if (ignoreMe) {
      //spurious event caused by changing navigation mode within onStateDeselect()
      ignoreMe = false;
      return;
    }

    if (tab.getTag().equals(TAG_SPLASH)) {
      fragmentTransaction.replace(R.id.layoutFragment, splashFragment, TAG_SPLASH);
    } else if (tab.getTag().equals(TAG_STATION_LIST)) {
      fragmentTransaction.replace(R.id.layoutFragment, stationListFragment, TAG_STATION_LIST);
    } else {
      throw new IllegalArgumentException("unknown tab:" + tab.getTag());
    }

    audioFacade.playTransitionCue(mainActivity);
  }

  /**
   * ActionBar.TabListener
   * @param tab
   * @param fragmentTransaction
   */
  @Override
  public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    //empty
  }

  /**
   * ActionBar.TabListener
   * @param tab
   * @param fragmentTransaction
   */
  @Override
  public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    //empty
  }

  /**
   * FragmentManager.OnBackStackChangedListener
   */
  public void onBackStackChanged() {
    FragmentManager fragmentManager = mainActivity.getFragmentManager();
    int backStackCount = fragmentManager.getBackStackEntryCount();

    ActionBar actionBar = mainActivity.getActionBar();

    /*
    if (backStackCount == 0) {
      // restore tabs
      ignoreMe = true;
      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
      mainActivity.clearCurrentStation();
    } else {
      // hide tabs
      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }
    */
  }

  /**
   * populate ActionBar
   */
  public void initialize() {
    FragmentManager fragmentManager = mainActivity.getFragmentManager();
    fragmentManager.addOnBackStackChangedListener(this);

    ActionBar actionBar = mainActivity.getActionBar();

    splashTab = actionBar.newTab();
    stationListTab = actionBar.newTab();

    splashTab.setTabListener(this);
    stationListTab.setTabListener(this);

    splashTab.setTag(TAG_SPLASH);
    stationListTab.setTag(TAG_STATION_LIST);

    splashTab.setText(R.string.menu_application_bar_splash);
    stationListTab.setText(R.string.menu_application_bar_station_list);

    actionBar.addTab(splashTab);
    actionBar.addTab(stationListTab);
  }

  /**
   * Map a tag to ActionBar.Tab
   * @param arg
   * @return related tab
   */
  public ActionBar.Tab tagToTab(String arg) {
    if (arg.equals(TAG_SPLASH)) {
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
    }

    fragmentTransaction.add(R.id.layoutFragment, observationListFragment, TAG_OBSERVATION_LIST);

    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }
}
