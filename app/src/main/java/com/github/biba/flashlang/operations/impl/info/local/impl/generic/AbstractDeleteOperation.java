package com.github.biba.flashlang.operations.impl.info.local.impl.generic;

import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.db.connector.IDbTableConnector;
import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.lib.contracts.IOperation;

public abstract class AbstractDeleteOperation<Model extends IDbModel<String>> implements IOperation<Boolean> {

    private final Selector[] mSelectors;

    public AbstractDeleteOperation(final Selector[] pSelectors) {
        mSelectors = pSelectors;
    }

    @Override
    public Boolean perform() throws Exception {
        boolean isDeleted = IDbTableConnector.Companion.getInstance()
                .delete(getTableName(), mSelectors);
        return isDeleted;
    }

    protected abstract String getTableName();
}
