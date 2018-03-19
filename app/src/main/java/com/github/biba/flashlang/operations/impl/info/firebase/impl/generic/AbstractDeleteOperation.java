package com.github.biba.flashlang.operations.impl.info.firebase.impl.generic;

import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.firebase.db.connector.IFirebaseDbConnector;
import com.github.biba.lib.contracts.IOperation;

public abstract class AbstractDeleteOperation<Model extends IDbModel<String>> implements IOperation<Void> {

    private final Selector mSelector;

    public AbstractDeleteOperation(final Selector pSelector) {
        mSelector = pSelector;
    }

    @Override
    public Void perform() throws Exception {
        IFirebaseDbConnector.Impl.Companion.getInstance().delete(getTableName(), mSelector);
        return null;
    }

    protected abstract String getTableName();
}
