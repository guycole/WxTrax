package com.digiburo.example.wxtrax2.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digiburo.example.wxtrax.lib.content.DataBaseFacade;
import com.digiburo.example.wxtrax.lib.content.ObservationModel;
import com.digiburo.example.wxtrax.lib.content.StationModel;
import com.digiburo.example.wxtrax.lib.content.StationTable;
import com.digiburo.example.wxtrax.lib.utility.LogFacade;
import com.digiburo.example.wxtrax.lib.utility.UserPreferenceHelper;
import com.digiburo.example.wxtrax2.R;

/**
 * Display "favorite" station details
 */
public class SplashFragment extends Fragment {

  /**
   * mandatory empty ctor
   */
  public SplashFragment() {
    //empty
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    LogFacade.entry(LOG_TAG, "onAttach");
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

    View view = inflater.inflate(R.layout.fragment_splash, container, false);
    return(view);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    LogFacade.entry(LOG_TAG, "onActivityCreated");

    //
    tvPressure = (TextView) getActivity().findViewById(R.id.label_pressure01);
    tvStation = (TextView) getActivity().findViewById(R.id.label_station01);
    tvTemperature = (TextView) getActivity().findViewById(R.id.label_temperature01);
    tvTimeStamp = (TextView) getActivity().findViewById(R.id.label_timestamp01);
    tvVisibility = (TextView) getActivity().findViewById(R.id.label_visibility01);
    tvWeather = (TextView) getActivity().findViewById(R.id.label_weather01);
    tvWind = (TextView) getActivity().findViewById(R.id.label_wind01);
  }

  @Override
  public void onStart() {
    super.onStart();
    LogFacade.entry(LOG_TAG, "onStart");
    
    UserPreferenceHelper uph = new UserPreferenceHelper();
    Long stationRowId = uph.getFaveStation(getActivity());
    if (stationRowId > 0) {
      DataBaseFacade dbf = new DataBaseFacade(getActivity());
      StationModel stationModel = (StationModel) dbf.selectModel(stationRowId, new StationTable());
      ObservationModel observationModel = (ObservationModel) dbf.getLatestObservation(stationModel.getIdentifier());
        
      tvPressure.setText(observationModel.getPressure());
      tvStation.setText(observationModel.getIdentifier());
      tvTemperature.setText(observationModel.getTemperature());
      tvTimeStamp.setText(observationModel.getTimeStamp());
      tvVisibility.setText(observationModel.getVisibility());
      tvWeather.setText(observationModel.getWeather());
      tvWind.setText(observationModel.getWind());
    }
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
  private TextView tvPressure;
  private TextView tvStation;
  private TextView tvTemperature;
  private TextView tvTimeStamp;
  private TextView tvVisibility;
  private TextView tvWeather;
  private TextView tvWind;

  //
  public static final String LOG_TAG = SplashFragment.class.getName();
}
/*
 * Copyright 2013 Digital Burro, INC
 * Created on Jan 5, 2013 by gsc
 */
