package com.digiburo.wxtrax.ui;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.digiburo.wxtrax.R;
import com.digiburo.wxtraxlib.db.StationTable;

/**
 * cursor adapter for StationListFragment
 *
 * @author gsc
 */
public class StationCursorAdapter extends CursorAdapter {

  /**
   *
   * @param context
   * @param from
   * @param to
   */
  public StationCursorAdapter(Context context) {
    super(context, null, true);
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    TextView tvIdentifier = (TextView) view.findViewById(R.id.textIdentifier01);
    tvIdentifier.setText(cursor.getString(cursor.getColumnIndex(StationTable.Columns.IDENTIFIER)));

    TextView tvLocation = (TextView) view.findViewById(R.id.textLocation01);
    tvLocation.setText(cursor.getString(cursor.getColumnIndex(StationTable.Columns.LOCATION)));
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(R.layout.row_station, viewGroup, false);
    bindView(view, context, cursor);
    return view;
  }
}