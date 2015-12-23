package com.digiburo.wxtrax.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digiburo.wxtrax.R;
import com.digiburo.wxtraxlib.db.DataBaseFacade;
import com.digiburo.wxtraxlib.db.ObservationModel;
import com.digiburo.wxtraxlib.db.StationModel;
import com.digiburo.wxtraxlib.db.StationTable;
import com.digiburo.wxtraxlib.utility.UserPreferenceHelper;

/**
 * Display "favorite" station details
 */
public class SplashFragment extends Fragment {
  private TextView tvPressure;
  private TextView tvStation;
  private TextView tvTemperature;
  private TextView tvTimeStamp;
  private TextView tvVisibility;
  private TextView tvWeather;
  private TextView tvWind;

  /**
   * mandatory empty ctor
   */
  public SplashFragment() {
    //empty
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);

    View view = inflater.inflate(R.layout.fragment_splash, container, false);
    return(view);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

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

    UserPreferenceHelper uph = new UserPreferenceHelper();
    Long stationRowId = uph.getFaveStation(getActivity());
    if (stationRowId > 0) {
      DataBaseFacade dbf = new DataBaseFacade(getActivity());
      StationModel stationModel = (StationModel) dbf.selectModel(stationRowId, new StationTable());
      ObservationModel observationModel = dbf.getLatestObservation(stationModel.getIdentifier());

      tvPressure.setText(observationModel.getPressure());
      tvStation.setText(observationModel.getIdentifier());
      tvTemperature.setText(observationModel.getTemperature());
      tvTimeStamp.setText(observationModel.getTimeStamp());
      tvVisibility.setText(observationModel.getVisibility());
      tvWeather.setText(observationModel.getWeather());
      tvWind.setText(observationModel.getWind());
    }
  }
}