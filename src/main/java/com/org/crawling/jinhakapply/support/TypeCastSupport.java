package com.org.crawling.jinhakapply.support;

public class TypeCastSupport {

    private TypeCastSupport() {}

    public static int stringToInteger(String str) {
        return Integer.valueOf(str);
    }

    public static float stringToFloat(String str) {
        return Float.valueOf(str);
    }
}
