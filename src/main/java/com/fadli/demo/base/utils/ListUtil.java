package com.fadli.demo.base.utils;

import java.util.List;

public class ListUtil {

    public static boolean isEmpty(List list) {
        if (list.size() == 0 || list == null) {
            return true;
        }

        return false;
    }
}
