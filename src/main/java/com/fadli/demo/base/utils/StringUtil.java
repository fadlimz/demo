package com.fadli.demo.base.utils;

import com.fadli.demo.base.constants.BaseConstants;

public class StringUtil {

    public static boolean isBlank(String stringValue) {
        if (stringValue.equals(BaseConstants.EMPTY) || stringValue.equals(BaseConstants.BLANK) ||
            stringValue.isEmpty() || stringValue.isBlank() || stringValue.equals(null))  {
            return true;
        }

        return false;
    }
}
