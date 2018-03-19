package com.github.biba.flashlang.operations.impl.info.firebase.operations;

import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.card.Card;
import com.github.biba.flashlang.operations.impl.info.firebase.IModelOperations;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.card.DeleteOperation;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.card.LoadListOperation;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.card.LoadQueryOperation;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.card.LoadSingleOperation;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.card.UpdateOperation;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.card.UploadAllOperation;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.card.UploadOperation;
import com.github.biba.lib.contracts.ICallback;
import com.github.biba.lib.contracts.IOperation;
import com.google.firebase.database.Query;

import java.util.List;

public class CardModelOperations implements IModelOperations<Card> {

    @Override
    public IOperation<Void> upload(final Card pModel) {
        return new UploadOperation(pModel);
    }

    @Override
    public IOperation<Void> uploadAll(final Card[] pModels) {
        return new UploadAllOperation(pModels);
    }

    @Override
    public IOperation<Void> loadList(final ICallback<List<Card>> pCallback, final Selector pSelector) {
        return new LoadListOperation(pCallback, pSelector);
    }

    @Override
    public IOperation<Void> loadSingle(final ICallback<Card> pCallback, final Selector pSelector) {
        return new LoadSingleOperation(pCallback, pSelector);
    }

    @Override
    public IOperation<Query> loadQuery(final Selector pSelector) {
        return new LoadQueryOperation(pSelector);
    }

    @Override
    public IOperation<Void> update(final Card pModel, final Selector pSelector) {
        return new UpdateOperation(pModel, pSelector);
    }

    @Override
    public IOperation<Void> delete(final Selector pSelector) {
        return new DeleteOperation(pSelector);
    }
}
