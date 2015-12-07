package com.digiburo.wxtrax.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.digiburo.wxtrax.R;

/**
 * Menu option:About, shameless self promotion
 * Implemented as a WebView w/local HTML content
 */
public class AboutFragment extends Fragment {

  /**
   * mandatory empty ctor
   */
  public AboutFragment() {
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
    View view = inflater.inflate(R.layout.fragment_about, container, false);
    return(view);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    WebView webView = (WebView) getActivity().findViewById(R.id.webView);
    webView.loadUrl("file:///android_asset/html/about.html");
  }
}