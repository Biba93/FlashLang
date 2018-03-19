package com.github.biba.flashlang.operations.impl.info.firebase.impl.generic;

import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.firebase.db.connector.IFirebaseDbConnector;
import com.github.biba.lib.contracts.IOperation;

public abstract class AbstractUploadAllOperation<Model extends IDbModel<String>> implements IOperation<Void> {

    private final Model[] mModels;

    public AbstractUploadAllOperation(final Model[] pModels) {
        mModels = pModels;
    }

    @Override
    public Void perform() throws Exception {
        IFirebaseDbConnector.Impl.Companion.getInstance()
                .insert(mModels);
        return null;
    }
}
