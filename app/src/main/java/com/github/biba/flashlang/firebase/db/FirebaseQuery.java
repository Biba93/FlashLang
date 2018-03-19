package com.github.biba.flashlang.firebase.db;

import android.support.annotation.Nullable;

import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.firebase.db.connector.IDataSnapshotConverter;
import com.github.biba.flashlang.firebase.db.connector.IFirebaseDbConnector;
import com.github.biba.flashlang.firebase.db.connector.IGetCallback;
import com.google.firebase.database.Query;

public class FirebaseQuery<Element extends IDbModel<String>> {

    private String mTableName;
    private Selector mSelector;
    private IDataSnapshotConverter<Element> mConverter;

    @Nullable
    public String getTableName() {
        return mTableName;
    }

    @Nullable
    public Selector getSelector() {
        return mSelector;
    }

    @Nullable
    public IDataSnapshotConverter<Element> getConverter() {

        return mConverter;
    }

    public FirebaseQuery tableName(final String pTableName) {
        mTableName = pTableName;
        return this;
    }

    public FirebaseQuery selector(final Selector pSelector) {
        mSelector = pSelector;
        return this;
    }

    public FirebaseQuery converter(final IDataSnapshotConverter<Element> pConverter) {
        mConverter = pConverter;
        return this;
    }

    public void get(final IGetCallback<Element> pCallback) {
        IFirebaseDbConnector.Impl.Companion.getInstance().get(this, pCallback);
    }

    public Query query() {
        final Query query = IFirebaseDbConnector.Impl.Companion.getInstance().query(this);
        return query;
    }
}
