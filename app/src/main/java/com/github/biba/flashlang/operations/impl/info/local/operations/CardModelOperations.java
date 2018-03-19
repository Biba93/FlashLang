package com.github.biba.flashlang.operations.impl.info.local.operations;

import android.database.Cursor;

import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.card.Card;
import com.github.biba.flashlang.operations.impl.info.local.IModelOperations;
import com.github.biba.flashlang.operations.impl.info.local.impl.card.DeleteOperation;
import com.github.biba.flashlang.operations.impl.info.local.impl.card.LoadCursorOperation;
import com.github.biba.flashlang.operations.impl.info.local.impl.card.LoadListOperation;
import com.github.biba.flashlang.operations.impl.info.local.impl.card.LoadSingleOperation;
import com.github.biba.flashlang.operations.impl.info.local.impl.card.UpdateOperation;
import com.github.biba.flashlang.operations.impl.info.local.impl.card.UploadAllOperation;
import com.github.biba.flashlang.operations.impl.info.local.impl.card.UploadOperation;
import com.github.biba.lib.contracts.IOperation;

import java.util.List;

public class CardModelOperations implements IModelOperations<Card> {

    @Override
    public IOperation<Boolean> upload(final Card pCard) {
        return new UploadOperation(pCard);
    }

    @Override
    public IOperation<Boolean> uploadAll(final Card[] pModels) {
        return new UploadAllOperation(pModels);
    }

    @Override
    public IOperation<Card> loadSingle(final Selector... pSelectors) {
        return new LoadSingleOperation(pSelectors);
    }

    @Override
    public IOperation<List<Card>> loadList(final String pGroupBy, final Selector... pSelectors) {
        return new LoadListOperation(pGroupBy, pSelectors);
    }

    @Override
    public IOperation<Cursor> loadCursor(final String pGroupBy, final Selector... pSelectors) {
        return new LoadCursorOperation(pGroupBy, pSelectors);
    }

    @Override
    public IOperation<Boolean> update(final Card pCard, final Selector... pSelectors) {
        return new UpdateOperation(pCard, pSelectors);
    }

    @Override
    public IOperation<Boolean> delete(final Selector... pSelectors) {
        return new DeleteOperation(pSelectors);
    }
}
