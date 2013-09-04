package com.digiburo.example.wxtrax2.ui;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.digiburo.example.wxtrax.lib.content.StationTable;
import com.digiburo.example.wxtrax.lib.utility.LogFacade;
import com.digiburo.example.wxtrax2.R;

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
    LogFacade.entry(LOG_TAG, "ctor");
 //   this.context = context;
  }
  
  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    LogFacade.entry(LOG_TAG, "bindView cursor size:" + cursor.getCount() + ":columns:" + cursor.getColumnCount());
  
    TextView tvIdentifier = (TextView) view.findViewById(R.id.textIdentifier01);
    tvIdentifier.setText(cursor.getString(cursor.getColumnIndex(StationTable.Columns.IDENTIFIER)));
    
    TextView tvLocation = (TextView) view.findViewById(R.id.textLocation01);
    tvLocation.setText(cursor.getString(cursor.getColumnIndex(StationTable.Columns.LOCATION)));
    
    LogFacade.exit(LOG_TAG, "bindView");
  }
  
  @Override
  public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
    LogFacade.entry(LOG_TAG, "newView");
    
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(R.layout.row_station, viewGroup, false);
    bindView(view, context, cursor);
    
    LogFacade.exit(LOG_TAG, "newView");
    return(view);
  }

  //
  public static final String LOG_TAG = StationCursorAdapter.class.getName();
}

/*
 * Copyright 2013 Digital Burro, INC
 * Created on Jan 5, 2013 by gsc
 */
