package com.digiburo.wxtrax.ui;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.digiburo.wxtrax.R;
import com.digiburo.wxtraxlib.db.ObservationTable;

/**
 * cursor adapter for StationListFragment
 *
 * @author gsc
 */
public class ObservationCursorAdapter extends CursorAdapter {

  /**
   *
   * @param context
   * @param from
   * @param to
   */
  public ObservationCursorAdapter(Context context) {
    super(context, null, true);
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    TextView row_timestamp = (TextView) view.findViewById(R.id.row_timestamp01);
    row_timestamp.setText(cursor.getString(cursor.getColumnIndex(ObservationTable.Columns.TIMESTAMP)));

    TextView row_weather = (TextView) view.findViewById(R.id.row_weather01);
    row_weather.setText(cursor.getString(cursor.getColumnIndex(ObservationTable.Columns.WEATHER)));

    TextView row_temperature = (TextView) view.findViewById(R.id.row_temperature01);
    row_temperature.setText(cursor.getString(cursor.getColumnIndex(ObservationTable.Columns.TEMPERATURE)));

    TextView row_visibility = (TextView) view.findViewById(R.id.row_visibility01);
    row_visibility.setText(cursor.getString(cursor.getColumnIndex(ObservationTable.Columns.VISIBILITY)));

    TextView row_pressure = (TextView) view.findViewById(R.id.row_pressure01);
    row_pressure.setText(cursor.getString(cursor.getColumnIndex(ObservationTable.Columns.PRESSURE)));

    TextView row_wind = (TextView) view.findViewById(R.id.row_wind01);
    row_wind.setText(cursor.getString(cursor.getColumnIndex(ObservationTable.Columns.WIND)));
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(R.layout.row_observation, viewGroup, false);
    bindView(view, context, cursor);
    return view;
  }
}
