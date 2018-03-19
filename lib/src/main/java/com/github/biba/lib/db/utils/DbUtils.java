package com.github.biba.lib.db.utils;

import android.support.annotation.Nullable;

import com.github.biba.lib.db.annotations.dbTable;
import com.github.biba.lib.db.annotations.dbTableElement;
import com.github.biba.lib.db.annotations.type.dbInteger;
import com.github.biba.lib.db.annotations.type.dbLong;
import com.github.biba.lib.db.annotations.type.dbString;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public final class DbUtils {

    @Nullable
    public static String getTableName(final Class<?> pClass) {

        if (pClass == null) {
            return null;
        }

        String name = null;
        final dbTable dbTableAnnotation = pClass.getAnnotation(dbTable.class);
        if (dbTableAnnotation != null) {
            name = dbTableAnnotation.name();
        } else {
            final dbTableElement dbTableElementAnnotation = pClass.getAnnotation(dbTableElement.class);
            if (dbTableElementAnnotation != null) {
                name = dbTableElementAnnotation.targetTableName();
            }
        }
        return name;
    }

    @Nullable
    public static String getFieldName(final Field pField) {

        if (pField == null) {
            return null;
        }

        final Annotation[] annotations = pField.getAnnotations();
        for (final Annotation annotation :
                annotations) {
            final Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.equals(dbInteger.class)) {
                return ((dbInteger) annotation).name();
            }
            if (annotationType.equals(dbLong.class)) {
                return ((dbLong) annotation).name();
            }
            if (annotationType.equals(dbString.class)) {
                return ((dbString) annotation).name();
            }
        }
        return null;
    }

}
