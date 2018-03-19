package com.github.biba.flashlang.operations.impl.info.local.impl.generic;

import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.db.connector.ICursorConverter;
import com.github.biba.flashlang.db.connector.IDbTableConnector;
import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.lib.contracts.IOperation;

import java.util.List;

public abstract class AbstractLoadSingleOperation<Model extends IDbModel<String>> implements IOperation<Model> {

    private final Selector[] mSelectors;

    public AbstractLoadSingleOperation(final Selector[] pSelectors) {
        mSelectors = pSelectors;
    }

    @Override
    public Model perform() throws Exception {
        final List<Model> achievements = IDbTableConnector.Companion.getInstance()
                .get(getTableName(), getCursorConverter(), null,
                        mSelectors);
        if (achievements != null && !achievements.isEmpty()) {
            return achievements.get(0);
        }
        return null;
    }

    protected abstract String getTableName();

    protected abstract ICursorConverter<Model> getCursorConverter();
}
