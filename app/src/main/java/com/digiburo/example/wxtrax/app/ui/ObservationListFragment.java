package com.digiburo.example.wxtrax.app.ui;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.digiburo.example.wxtrax.app.R;
import com.digiburo.example.wxtrax.lib.content.DataBaseFacade;
import com.digiburo.example.wxtrax.lib.content.DataBaseTableIf;
import com.digiburo.example.wxtrax.lib.content.ObservationTable;
import com.digiburo.example.wxtrax.lib.content.StationModel;
import com.digiburo.example.wxtrax.lib.content.StationTable;
import com.digiburo.example.wxtrax.lib.utility.LogFacade;

/**
 * Display scrolling list of weather observations 
 */
public class ObservationListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>  {

  /**
   * Define station
   * @param rowId
   */
  public void setStationRowId(Long rowId) {
    stationRowId = rowId;
  }
 
  /**
   * LoaderCallback
   */
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    LogFacade.entry(LOG_TAG, "onCreateLoader:" + id);

    DataBaseTableIf table = new ObservationTable();
    
    String[] projection = table.getDefaultProjection();
    String orderBy = table.getDefaultSortOrder();

    //select for specified station
    String selection = ObservationTable.Columns.IDENTIFIER + "=?";
    String[] selectionArgs = new String[] {stationIdentifier};
 
    CursorLoader loader = new CursorLoader(getActivity(), ObservationTable.CONTENT_URI, projection, selection, selectionArgs, orderBy);
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
   * mandatory empty ctor
   */
  public ObservationListFragment() {
    //empty
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    LogFacade.entry(LOG_TAG, "onAttach");

    DataBaseFacade dbf = new DataBaseFacade(activity);
    StationModel model = (StationModel) dbf.selectModel(stationRowId, new StationTable());
    stationIdentifier = model.getIdentifier();
    LogFacade.debug(LOG_TAG, "set identifier:" + stationIdentifier);
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
    return(inflater.inflate(R.layout.fragment_observation_list, container, false));
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    LogFacade.entry(LOG_TAG, "onActivityCreated");
    
    //
    registerForContextMenu(getListView());

    //
    setListAdapter(new ObservationCursorAdapter(getActivity()));
    
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
  private String stationIdentifier = "bogus";
  private Long stationRowId = 0L;

  //
  public static final int LOADER_ID = 314156;

  // context menu
  public static final int CONTEXT_ITEM_1 = Menu.FIRST;
  public static final int CONTEXT_ITEM_2 = Menu.FIRST + 1;

  //
  public static final String LOG_TAG = ObservationListFragment.class.getName();
}

/*
 * Copyright 2013 Digital Burro, INC
 * Created on Jan 5, 2013 by gsc
 */
