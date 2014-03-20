package com.digiburo.example.wxtrax.app.test;

import android.app.Activity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import org.robolectric.annotation.Config;

import com.digiburo.example.wxtrax.app.ui.MenuActivity;

@RunWith(RobolectricTestRunner.class)
public class MenuActivityTest {

  @Test @Config(reportSdk = 10)
  public void testCreation() {
//    Activity activity = Robolectric.buildActivity(MenuActivity.class).create().get();
//    assertTrue(activity != null);
  }
}
