package com.digiburo.example.wxtrax2.ui;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.digiburo.example.wxtrax.lib.content.DataBaseTableIf;
import com.digiburo.example.wxtrax.lib.content.StationTable;
import com.digiburo.example.wxtrax.lib.utility.LogFacade;
import com.digiburo.example.wxtrax.lib.utility.UserPreferenceHelper;
import com.digiburo.example.wxtrax2.R;

/**
 * Display scrolling list of weather stations
 * Supports favorite station and deletion
 * Selects station to view observations
 */
public class StationListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>  {

  /**
   * LoaderCallback
   */
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    LogFacade.entry(LOG_TAG, "onCreateLoader:" + id);

    DataBaseTableIf table = new StationTable();

    String[] projection = table.getDefaultProjection();
    String orderBy = table.getDefaultSortOrder();

    String selection = null;
    String[] selectionArgs = null;

    CursorLoader loader = new CursorLoader(getActivity(), StationTable.CONTENT_URI, projection, selection, selectionArgs, orderBy);
    return(loader);
  }

  /**
   * LoaderCallback
   */
  public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    LogFacade.entry(LOG_TAG, "onLoadFinished:" + loader.toString());
    
    CursorAdapter cursorAdapter = (CursorAdapter) getListAdapter();
    cursorAdapter.changeCursor(cursor);
    cursorAdapter.notifyDataSetChanged();
    
    if (cursor == null) {
      LogFacade.debug(LOG_TAG, "load finished w/null cursor");
    } else {
      LogFacade.debug(LOG_TAG, "load finished w/cursor size:" + cursor.getCount() + ":column size:" + cursor.getColumnCount());
    }
  }

  /**
   * LoaderCallback
   */
  public void onLoaderReset(Loader<Cursor> loader) {
    LogFacade.entry(LOG_TAG, "onLoaderReset");
    
    CursorAdapter cursorAdapter = (CursorAdapter) getListAdapter();
    if (cursorAdapter != null) {
      cursorAdapter.changeCursor(null);
    }
  }
 
  /**
   * row selection
   */
  @Override
  public void onListItemClick(ListView listView, View view, int position, long id) {
    LogFacade.debug(LOG_TAG, "click:" + position + ":" + id);
    stationListener.onStationSelect(id, TabHelper.TAG_STATION_LIST);
  }

  /**
   * create context menu
   */
  @Override
  public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
    menu.add(0, CONTEXT_ITEM_1, 0, R.string.station_menu_favorite);
    menu.add(0, CONTEXT_ITEM_2, 0, R.string.station_menu_delete);
  }

  /**
   * context menu selection
   */
  @Override
  public boolean onContextItemSelected(MenuItem item) {
    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    LogFacade.entry(LOG_TAG, "on context item select:" + item + ":" + item.getItemId() + ":" + info.id);
    
    UserPreferenceHelper uph = new UserPreferenceHelper();

    switch(item.getItemId()) {
    case CONTEXT_ITEM_1:
      LogFacade.debug(LOG_TAG, "set fave");
      uph.setFaveStation(getActivity(), info.id);
      break;
    case CONTEXT_ITEM_2:
      LogFacade.debug(LOG_TAG, "delete");
      stationListener.displayStationDeleteDialog(info.id);
      break;
    }

    return super.onContextItemSelected(item);
  }

  /**
   * mandatory empty ctor
   */
  public StationListFragment() {
    //empty
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    LogFacade.entry(LOG_TAG, "onAttach");
    stationListener = (StationListener) activity;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LogFacade.entry(LOG_TAG, "onCreate");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    LogFacade.entry(LOG_TAG, "onCreateView");
    return(inflater.inflate(R.layout.fragment_station_list, container, false));
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    LogFacade.entry(LOG_TAG, "onActivityCreated");
    
    //
    registerForContextMenu(getListView());

    //
    setListAdapter(new StationCursorAdapter(getActivity()));
    
    //
    getLoaderManager().initLoader(LOADER_ID,  null, this);
  }

  @Override
  public void onStart() {
    super.onStart();
    LogFacade.entry(LOG_TAG, "onStart");
  }

  @Override
  public void onResume() {
    super.onResume();
    LogFacade.entry(LOG_TAG, "onResume");
  }

  @Override
  public void onPause() {
    super.onPause();
    LogFacade.entry(LOG_TAG, "onPause");
  }

  @Override
  public void onStop() {
    super.onStop();
    LogFacade.entry(LOG_TAG, "onStop");
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    LogFacade.entry(LOG_TAG, "onDestroyView");
    setListAdapter(null);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    LogFacade.entry(LOG_TAG, "onDestroy");
  }

  @Override
  public void onDetach() {
    super.onDetach();
    LogFacade.entry(LOG_TAG, "onDetach");
  }

  //
  private StationListener stationListener;

  //
  public static final int LOADER_ID = 271828;

  // context menu
  public static final int CONTEXT_ITEM_1 = Menu.FIRST;
  public static final int CONTEXT_ITEM_2 = Menu.FIRST + 1;

  //
  public static final String LOG_TAG = StationListFragment.class.getName();
}

/*
 * Copyright 2013 Digital Burro, INC
 * Created on Jan 5, 2013 by gsc
 */
