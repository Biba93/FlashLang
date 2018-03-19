package com.github.biba.flashlang.operations.impl.info.firebase.impl.collection;

import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.collection.Collection;
import com.github.biba.flashlang.firebase.db.connector.IDataSnapshotConverter;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.generic.AbstractLoadSingleOperation;
import com.github.biba.lib.contracts.ICallback;

public class LoadSingleOperation extends AbstractLoadSingleOperation<Collection> {

    public LoadSingleOperation(final ICallback<Collection> pCallback, final Selector pSelector) {
        super(pCallback, pSelector);
    }

    @Override
    protected String getTableName() {
        return Collection.DbKeys.TABLE_NAME;
    }

    @Override
    protected IDataSnapshotConverter<Collection> getDateSnapshotConverter() {
        return new Collection.DataSnapshotConverter();
    }
}
