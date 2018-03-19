package com.github.biba.lib.httpclient;

import java.util.Collections;
import java.util.Map;

public class Headers {

    private Map<String, String> mMap;

    public Headers() {
    }

    public void addHeader(final String pKey, final String pValue) {
        mMap.put(pKey, pValue);
    }

    Map<String, String> getMap() {
        return Collections.unmodifiableMap(mMap);
    }
}
