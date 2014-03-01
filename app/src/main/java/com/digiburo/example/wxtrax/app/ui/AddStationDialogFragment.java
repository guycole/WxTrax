package com.digiburo.example.wxtrax.app.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.digiburo.example.wxtrax.app.R;

/**
 * user input of new station to monitor
 */
public class AddStationDialogFragment extends DialogFragment {

  /**
   * Fragment Factory
   * @return
   */
  public static AddStationDialogFragment newInstance() {
    AddStationDialogFragment fragment = new AddStationDialogFragment();
    Bundle bundle = new Bundle();
    fragment.setArguments(bundle);
    return(fragment);
  }

  @Override
  public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
    getDialog().setTitle(R.string.dialog_add_station_title);

    View view = layoutInflater.inflate(R.layout.dialog_new_station, container, false);
    final EditText editText = (EditText) view.findViewById(R.id.edit_station01);

    Button buttonSave = (Button) view.findViewById(R.id.button_save01);
    buttonSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        ((MainActivity) getActivity()).onStationAddYes(editText.getText().toString().trim());
        dismiss();
      }
    });

    Button buttonCancel = (Button) view.findViewById(R.id.button_cancel01);
    buttonCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        ((MainActivity) getActivity()).onStationAddNo();
        dismiss();
      }
    });

    return(view);
  }

  //
  public static final String LOG_TAG = AddStationDialogFragment.class.getName();
}
/*
 * Copyright 2013 Digital Burro, INC
 * Created on Jan 5, 2013 by gsc
 */
