package com.github.biba.flashlang.ui.adapter;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({CardType.NO_IMAGE, CardType.WITH_IMAGE})
@Retention(RetentionPolicy.SOURCE)
public @interface CardType {

    int NO_IMAGE = 1;
    int WITH_IMAGE = 2;
}
