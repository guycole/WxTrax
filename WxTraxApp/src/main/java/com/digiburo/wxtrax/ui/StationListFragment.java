package com.digiburo.wxtrax.ui;

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

import com.digiburo.wxtraxlib.db.DataBaseTable;
import com.digiburo.wxtraxlib.db.StationTable;


/**
 * Display scrolling list of weather stations
 * Supports favorite station and deletion
 * Selects station to view observations
 */
public class StationListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
  public static final int LOADER_ID = 271828;

  // context main
  public static final int CONTEXT_ITEM_1 = Menu.FIRST;
  public static final int CONTEXT_ITEM_2 = Menu.FIRST + 1;

  private StationListener stationListener;

  /**
   * LoaderCallback
   */
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    DataBaseTable table = new StationTable();

    String[] projection = table.getDefaultProjection();
    String orderBy = table.getDefaultSortOrder();

    String selection = null;
    String[] selectionArgs = null;

    CursorLoader loader = new CursorLoader(getActivity(), StationTable.CONTENT_URI, projection, selection, selectionArgs, orderBy);
    return loader;
  }

  /**
   * LoaderCallback
   */
  public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    CursorAdapter cursorAdapter = (CursorAdapter) getListAdapter();
    cursorAdapter.changeCursor(cursor);
    cursorAdapter.notifyDataSetChanged();
  }

  /**
   * LoaderCallback
   */
  public void onLoaderReset(Loader<Cursor> loader) {
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
    stationListener.onStationSelect(id, TabHelper.TAG_STATION_LIST);
  }

  /**
   * create context main
   */
  @Override
  public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
    menu.add(0, CONTEXT_ITEM_1, 0, R.string.station_menu_favorite);
    menu.add(0, CONTEXT_ITEM_2, 0, R.string.station_menu_delete);
  }

  /**
   * context main selection
   */
  @Override
  public boolean onContextItemSelected(MenuItem item) {
    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

    UserPreferenceHelper uph = new UserPreferenceHelper();

    switch(item.getItemId()) {
      case CONTEXT_ITEM_1:
        uph.setFaveStation(getActivity(), info.id);
        break;
      case CONTEXT_ITEM_2:
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
    stationListener = (StationListener) activity;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    return inflater.inflate(R.layout.fragment_station_list, container, false);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    registerForContextMenu(getListView());
    setListAdapter(new StationCursorAdapter(getActivity()));
    getLoaderManager().initLoader(LOADER_ID,  null, this);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    setListAdapter(null);
  }
}