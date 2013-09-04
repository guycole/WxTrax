package com.digiburo.example.wxtrax.lib.utility;

/**
 * //type comment//
 *
 * @author guycole
 */
public enum LegalOptionMenuType {
  UNKNOWN("Unknown"),
  ABOUT("About"),
  NEW_STATION("NewStation"),
  PREFERENCE("Preference");

  LegalOptionMenuType(String name) {
    typeName = name;
  }

  private final String typeName;

  public String getName() {
    return(typeName);
  }

  /**
   *
   * @param arg
   * @return
   */
  public static LegalOptionMenuType discoverMatchingEnum(String arg) {
    LegalOptionMenuType result = UNKNOWN;

    if (arg == null) {
      return(result);
    }

    for (LegalOptionMenuType token: LegalOptionMenuType.values()) {
      if (token.getName().equals(arg)) {
        result = token;
      }
    }

    return(result);
  }
}

/*
 * Copyright 2013 Digital Burro, INC
 * Created on Sep 1, 2013 by guycole
 */