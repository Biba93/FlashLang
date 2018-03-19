package com.github.biba.flashlang.operations.impl.info.firebase.operations;

import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.collection.Collection;
import com.github.biba.flashlang.operations.impl.info.firebase.IModelOperations;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.collection.DeleteOperation;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.collection.LoadListOperation;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.collection.LoadQueryOperation;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.collection.LoadSingleOperation;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.collection.UpdateOperation;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.collection.UploadAllOperation;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.collection.UploadOperation;
import com.github.biba.lib.contracts.ICallback;
import com.github.biba.lib.contracts.IOperation;
import com.google.firebase.database.Query;

import java.util.List;

public class CollectionModelOperations implements IModelOperations<Collection> {

    @Override
    public IOperation<Void> upload(final Collection pModel) {
        return new UploadOperation(pModel);
    }

    @Override
    public IOperation<Void> uploadAll(final Collection[] pModels) {
        return new UploadAllOperation(pModels);
    }

    @Override
    public IOperation<Void> loadList(final ICallback<List<Collection>> pCallback, final Selector pSelector) {
        return new LoadListOperation(pCallback, pSelector);
    }

    @Override
    public IOperation<Void> loadSingle(final ICallback<Collection> pCallback, final Selector pSelector) {
        return new LoadSingleOperation(pCallback, pSelector);
    }

    @Override
    public IOperation<Query> loadQuery(final Selector pSelector) {
        return new LoadQueryOperation(pSelector);
    }

    @Override
    public IOperation<Void> update(final Collection pModel, final Selector pSelector) {
        return new UpdateOperation(pModel, pSelector);
    }

    @Override
    public IOperation<Void> delete(final Selector pSelector) {
        return new DeleteOperation(pSelector);
    }
}
