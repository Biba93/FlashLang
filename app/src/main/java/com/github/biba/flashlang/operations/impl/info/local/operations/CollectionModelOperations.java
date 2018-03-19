package com.github.biba.flashlang.operations.impl.info.local.operations;

import android.database.Cursor;

import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.collection.Collection;
import com.github.biba.flashlang.operations.impl.info.local.IModelOperations;
import com.github.biba.flashlang.operations.impl.info.local.impl.collection.DeleteOperation;
import com.github.biba.flashlang.operations.impl.info.local.impl.collection.LoadCursorOperation;
import com.github.biba.flashlang.operations.impl.info.local.impl.collection.LoadListOperation;
import com.github.biba.flashlang.operations.impl.info.local.impl.collection.LoadSingleOperation;
import com.github.biba.flashlang.operations.impl.info.local.impl.collection.UpdateOperation;
import com.github.biba.flashlang.operations.impl.info.local.impl.collection.UploadAllOperation;
import com.github.biba.flashlang.operations.impl.info.local.impl.collection.UploadOperation;
import com.github.biba.lib.contracts.IOperation;

import java.util.List;

public class CollectionModelOperations implements IModelOperations<Collection> {

    @Override
    public IOperation<Boolean> upload(final Collection pCollection) {
        return new UploadOperation(pCollection);
    }

    @Override
    public IOperation<Boolean> uploadAll(final Collection[] pModels) {
        return new UploadAllOperation(pModels);
    }

    @Override
    public IOperation<Collection> loadSingle(final Selector... pSelectors) {
        return new LoadSingleOperation(pSelectors);
    }

    @Override
    public IOperation<List<Collection>> loadList(final String pGroupBy, final Selector... pSelectors) {
        return new LoadListOperation(pGroupBy, pSelectors);
    }

    @Override
    public IOperation<Cursor> loadCursor(final String pGroupBy, final Selector... pSelectors) {
        return new LoadCursorOperation(pGroupBy, pSelectors);
    }

    @Override
    public IOperation<Boolean> update(final Collection pCollection, final Selector... pSelectors) {
        return new UpdateOperation(pCollection, pSelectors);
    }

    @Override
    public IOperation<Boolean> delete(final Selector... pSelectors) {
        return new DeleteOperation(pSelectors);
    }
}
