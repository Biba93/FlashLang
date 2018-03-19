package com.github.biba.flashlang.operations.impl.info.firebase.impl.generic;

import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.firebase.db.connector.IFirebaseDbConnector;
import com.github.biba.lib.contracts.IOperation;
import com.google.firebase.database.Query;

public abstract class AbstractLoadQueryOperation<Model extends IDbModel<String>> implements IOperation<Query> {

    private final Selector mSelector;

    public AbstractLoadQueryOperation(final Selector pSelector) {
        mSelector = pSelector;
    }

    @Override
    public Query perform() throws Exception {
        final Query query = IFirebaseDbConnector.Impl.Companion.getInstance()
                .query()
                .tableName(getTableName())
                .selector(mSelector)
                .query();
        return query;
    }

    protected abstract String getTableName();
}
