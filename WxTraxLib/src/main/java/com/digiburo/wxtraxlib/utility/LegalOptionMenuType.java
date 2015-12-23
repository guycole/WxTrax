package com.digiburo.wxtraxlib.utility;

/**
 * legal option menu selections
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
            return result;
        }

        for (LegalOptionMenuType token: LegalOptionMenuType.values()) {
            if (token.getName().equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}