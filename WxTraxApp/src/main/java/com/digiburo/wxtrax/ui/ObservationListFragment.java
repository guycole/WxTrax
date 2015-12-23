package com.digiburo.wxtrax.ui;

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

import com.digiburo.wxtrax.R;
import com.digiburo.wxtraxlib.db.DataBaseFacade;
import com.digiburo.wxtraxlib.db.DataBaseTable;
import com.digiburo.wxtraxlib.db.ObservationTable;
import com.digiburo.wxtraxlib.db.StationModel;
import com.digiburo.wxtraxlib.db.StationTable;

/**
 * Display scrolling list of weather observations
 */
public class ObservationListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>  {
  private String stationIdentifier = "bogus";
  private Long stationRowId = 0L;

  //
  public static final int LOADER_ID = 314156;

  // context main
  public static final int CONTEXT_ITEM_1 = Menu.FIRST;
  public static final int CONTEXT_ITEM_2 = Menu.FIRST + 1;

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
    DataBaseTable table = new ObservationTable();

    String[] projection = table.getDefaultProjection();
    String orderBy = table.getDefaultSortOrder();

    //select for specified station
    String selection = ObservationTable.Columns.IDENTIFIER + "=?";
    String[] selectionArgs = new String[] {stationIdentifier};

    CursorLoader loader = new CursorLoader(getActivity(), ObservationTable.CONTENT_URI, projection, selection, selectionArgs, orderBy);
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
   * mandatory empty ctor
   */
  public ObservationListFragment() {
    //empty
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);

    DataBaseFacade dbf = new DataBaseFacade(activity);
    StationModel model = (StationModel) dbf.selectModel(stationRowId, new StationTable());
    stationIdentifier = model.getIdentifier();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    return(inflater.inflate(R.layout.fragment_observation_list, container, false));
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    registerForContextMenu(getListView());
    setListAdapter(new ObservationCursorAdapter(getActivity()));
    getLoaderManager().initLoader(LOADER_ID,  null, this);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    setListAdapter(null);
  }
}