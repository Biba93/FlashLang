package com.github.biba.flashlang.domain.db;

import android.content.ContentValues;
import android.os.Parcel;

import java.util.Map;

public final class MapUtils {

    public static ContentValues toContentValues(final Map<String, Object> pMap) {
        final Parcel parcel = Parcel.obtain();
        parcel.writeMap(pMap);
        parcel.setDataPosition(0);
        return ContentValues.CREATOR.createFromParcel(parcel);
    }

}

