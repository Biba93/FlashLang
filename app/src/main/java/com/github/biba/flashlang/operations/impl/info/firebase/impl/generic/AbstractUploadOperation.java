package com.github.biba.flashlang.operations.impl.info.firebase.impl.generic;

import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.firebase.db.connector.IFirebaseDbConnector;
import com.github.biba.lib.contracts.IOperation;

public abstract class AbstractUploadOperation<Model extends IDbModel<String>> implements IOperation<Void> {

    private final Model mModel;

    public AbstractUploadOperation(final Model pModel) {
        mModel = pModel;
    }

    @Override
    public Void perform() throws Exception {
        IFirebaseDbConnector.Impl.Companion.getInstance()
                .insert(mModel);
        return null;
    }
}
