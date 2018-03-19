package com.github.biba.flashlang.operations.impl.info.local.impl.generic;

import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.db.connector.ICursorConverter;
import com.github.biba.flashlang.db.connector.IDbTableConnector;
import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.lib.contracts.IOperation;

import java.util.List;

public abstract class AbstractLoadListOperation<Model extends IDbModel<String>> implements IOperation<List<Model>> {

    private final String mGroupBy;
    private final Selector[] mSelectors;

    public AbstractLoadListOperation(final String pGroupBy, final Selector[] pSelectors) {
        mGroupBy = pGroupBy;
        mSelectors = pSelectors;
    }

    @Override
    public List<Model> perform() throws Exception {
        final List<Model> models = IDbTableConnector.Companion.getInstance()
                .get(getTableName(), getCursorConverter(),
                        mGroupBy, mSelectors);

        return models;
    }

    protected abstract String getTableName();

    protected abstract ICursorConverter<Model> getCursorConverter();
}
