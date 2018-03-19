package com.github.biba.flashlang.operations.impl.info.firebase.impl.generic;

import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.firebase.db.connector.IFirebaseDbConnector;
import com.github.biba.lib.contracts.IOperation;

public abstract class AbstractUpdateOperation<Model extends IDbModel<String>> implements IOperation<Void> {

    private final Selector mSelector;
    private final Model mModel;

    public AbstractUpdateOperation(final Model pModel, final Selector pSelector) {
        mModel = pModel;
        mSelector = pSelector;
    }

    @Override
    public Void perform() throws Exception {
        IFirebaseDbConnector.Impl.Companion.getInstance()
                .update(mModel, mSelector);
        return null;
    }

}
