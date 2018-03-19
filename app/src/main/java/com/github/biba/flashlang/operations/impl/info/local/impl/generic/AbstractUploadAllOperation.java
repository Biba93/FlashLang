package com.github.biba.flashlang.operations.impl.info.local.impl.generic;

import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.db.connector.IDbTableConnector;
import com.github.biba.lib.contracts.IOperation;

public abstract class AbstractUploadAllOperation<Model extends IDbModel<String>> implements IOperation<Boolean> {

    private final Model[] mModels;

    public AbstractUploadAllOperation(final Model[] pModels) {
        mModels = pModels;
    }

    @Override
    public Boolean perform() throws Exception {
        final boolean isInserted = IDbTableConnector.Companion.getInstance()
                .insert(mModels);
        return isInserted;
    }
}
