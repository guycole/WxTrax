package com.digiburo.example.wxtrax.app.test;

import com.digiburo.example.wxtrax.lib.utility.UserPreferenceHelper;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author gsc
 */
public class UserPreferenceHelperTest {

  /**
   * @throws Throwable
   */
  @Test
  public void test01() throws Throwable {
    System.out.println("test01 start");

    UserPreferenceHelper uph = new UserPreferenceHelper();
    assertNotNull(uph);
//    assertFalse(uph.isEmptyPreferences(getContext()));
//    assertTrue(uph.isAdMob(getContext()));
//    assertTrue(uph.isGoogleAnalytics(getContext()));

  }
}
/*
 * Copyright 2014 Digital Burro, INC
 * Created 3/20/14 by gsc
 */
