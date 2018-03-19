package com.github.biba.lib.db.sql;

import com.github.biba.lib.Constants;
import com.github.biba.lib.db.utils.AnnotationUtils;

import java.lang.reflect.Field;

import static com.github.biba.lib.Constants.Sql.NOT_NULL;
import static com.github.biba.lib.Constants.Sql.PRIMARY_KEY;
import static com.github.biba.lib.Constants.Sql.WORD_SEPARATOR;

public class ColumnSql {

    private final String mFieldSql;
    private final String mForeignKeySql;

    public ColumnSql(final Field pField) {
        final FieldDescription fieldDescription = new FieldDescription(pField);
        mFieldSql = buildFieldSql(fieldDescription);
        mForeignKeySql = buildForeignKeySql(fieldDescription);
    }

    public String getFieldSql() {
        return mFieldSql;
    }

    public String getForeignKeySql() {
        return mForeignKeySql;
    }

    private static String buildFieldSql(final FieldDescription pFieldDescription) {
        final StringBuilder sqlBuilder = new StringBuilder();

        final String fieldName = pFieldDescription.getFieldName();
        if (fieldName == null) {
            return null;
        }
        sqlBuilder.append(fieldName);

        final String fieldType = pFieldDescription.getFieldType();
        if (fieldType == null) {
            return null;
        }
        sqlBuilder.append(WORD_SEPARATOR);
        sqlBuilder.append(fieldType);

        if (pFieldDescription.isPrimaryKey()) {
            sqlBuilder.append(WORD_SEPARATOR);
            sqlBuilder.append(PRIMARY_KEY);
            if (!pFieldDescription.isPrimaryKeyNull()) {
                sqlBuilder.append(WORD_SEPARATOR);
                sqlBuilder.append(NOT_NULL);
            }

        }

        return sqlBuilder.toString();
    }

    private static String buildForeignKeySql(final FieldDescription pFieldDescription) {
        if (pFieldDescription.isForeignKey()) {
            final String fieldName = pFieldDescription.getFieldName();
            final String referredTableName = pFieldDescription.getReferredTableName();
            final String referredColumnName = pFieldDescription.getReferredColumnName();
            if (fieldName == null
                    || referredTableName == null
                    || referredColumnName == null) {
                return null;
            }
            return String.format(Constants.Sql.FOREIGN_KEY_TEMPLATE, fieldName, referredTableName, referredColumnName);
        } else {
            return null;
        }
    }

    private static class FieldDescription {

        private final String mFieldType;
        private final String mFieldName;
        private final boolean mIsPrimaryKey;
        private final boolean mIsPrimaryKeyNull;
        private final boolean mIsForeignKey;
        private final String mReferredTableName;
        private final String mReferredColumnName;

        FieldDescription(final Field pField) {
            mFieldType = AnnotationUtils.getType(pField);
            mFieldName = AnnotationUtils.getName(pField);
            mIsPrimaryKey = AnnotationUtils.isPrimaryKey(pField);
            mIsPrimaryKeyNull = AnnotationUtils.isPrimaryKeyNull(pField);
            mIsForeignKey = AnnotationUtils.isForeignKey(pField);
            mReferredTableName = AnnotationUtils.getReferredTableName(pField);
            mReferredColumnName = AnnotationUtils.getReferredColumnName(pField);
        }

        String getFieldType() {
            return mFieldType;
        }

        String getFieldName() {
            return mFieldName;
        }

        boolean isPrimaryKey() {
            return mIsPrimaryKey;
        }

        boolean isPrimaryKeyNull() {
            return mIsPrimaryKeyNull;
        }

        boolean isForeignKey() {
            return mIsForeignKey;
        }

        String getReferredTableName() {
            return mReferredTableName;
        }

        String getReferredColumnName() {
            return mReferredColumnName;
        }
    }

}
