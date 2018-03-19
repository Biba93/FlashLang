package com.github.biba.lib.db.utils;

import com.github.biba.lib.db.annotations.dbPrimaryKey;
import com.github.biba.lib.db.annotations.type.dbForeignKey;
import com.github.biba.lib.db.annotations.type.dbInteger;
import com.github.biba.lib.db.annotations.type.dbLong;
import com.github.biba.lib.db.annotations.type.dbString;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public final class AnnotationUtils {

    public static String getType(final AnnotatedElement pElement) {

        if (pElement == null) {
            return null;
        }

        final Annotation[] annotations = pElement.getAnnotations();

        for (final Annotation annotation :
                annotations) {
            final Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.equals(dbInteger.class)) {
                return ((dbInteger) annotation).type();
            }
            if (annotationType.equals(dbLong.class)) {
                return ((dbLong) annotation).type();
            }
            if (annotationType.equals(dbString.class)) {
                return ((dbString) annotation).type();
            }
        }

        return null;

    }

    public static String getName(final AnnotatedElement pElement) {

        if (pElement == null) {
            return null;
        }

        final Annotation[] annotations = pElement.getAnnotations();

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

    public static boolean isPrimaryKey(final AnnotatedElement pElement) {
        try {
            final dbPrimaryKey annotation = pElement.getAnnotation(dbPrimaryKey.class);
            if (annotation != null) {
                return true;
            }
        } catch (final Exception pE) {
            return false;
        }
        return false;
    }

    public static boolean isPrimaryKeyNull(final AnnotatedElement pElement) {
        try {
            final dbPrimaryKey annotation = pElement.getAnnotation(dbPrimaryKey.class);
            if (annotation != null) {
                return annotation.isNull();
            }
        } catch (final Exception pE) {
            return false;
        }
        return false;
    }

    public static boolean isForeignKey(final AnnotatedElement pElement) {
        try {
            final dbForeignKey annotation = pElement.getAnnotation(dbForeignKey.class);
            if (annotation != null) {
                return true;
            }
        } catch (final Exception pE) {
            return false;
        }
        return false;
    }

    public static String getReferredTableName(final AnnotatedElement pElement) {
        try {
            final dbForeignKey annotation = pElement.getAnnotation(dbForeignKey.class);
            if (annotation != null) {
                return annotation.referredTableName();
            }
        } catch (final Exception pE) {
            return null;
        }
        return null;
    }

    public static String getReferredColumnName(final AnnotatedElement pElement) {
        try {
            final dbForeignKey annotation = pElement.getAnnotation(dbForeignKey.class);
            if (annotation != null) {
                return annotation.referredTableColumnName();
            }
        } catch (final Exception pE) {
            return null;
        }
        return null;
    }

}
