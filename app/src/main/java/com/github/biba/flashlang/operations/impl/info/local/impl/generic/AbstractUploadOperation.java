package com.github.biba.flashlang.operations.impl.info.local.impl.generic;

import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.db.connector.IDbTableConnector;
import com.github.biba.lib.contracts.IOperation;

public abstract class AbstractUploadOperation<Model extends IDbModel<String>> implements IOperation<Boolean> {

    private final Model mModel;

    public AbstractUploadOperation(final Model pModel) {
        mModel = pModel;
    }

    @Override
    public Boolean perform() throws Exception {
        boolean isInserted = IDbTableConnector.Companion.getInstance().insert(mModel);
        return isInserted;
    }
}
