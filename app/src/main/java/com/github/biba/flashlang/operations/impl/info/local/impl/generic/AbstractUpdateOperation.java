package com.github.biba.flashlang.operations.impl.info.local.impl.generic;

import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.db.connector.IDbTableConnector;
import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.lib.contracts.IOperation;

public abstract class AbstractUpdateOperation<Model extends IDbModel<String>> implements IOperation<Boolean> {

    private final Selector[] mSelectors;
    private final Model mModel;

    public AbstractUpdateOperation(final Model pModel, final Selector[] pSelectors) {
        mModel = pModel;
        mSelectors = pSelectors;
    }

    @Override
    public Boolean perform() throws Exception {
        final boolean isUpdated = IDbTableConnector.Companion.getInstance()
                .update(mModel, mSelectors);
        return isUpdated;
    }

}
