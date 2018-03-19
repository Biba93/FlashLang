package com.github.biba.flashlang.operations.impl.info.firebase.impl.card;

import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.card.Card;
import com.github.biba.flashlang.firebase.db.connector.IDataSnapshotConverter;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.generic.AbstractLoadSingleOperation;
import com.github.biba.lib.contracts.ICallback;

public class LoadSingleOperation extends AbstractLoadSingleOperation<Card> {

    public LoadSingleOperation(final ICallback<Card> pCallback, final Selector pSelector) {
        super(pCallback, pSelector);
    }

    @Override
    protected String getTableName() {
        return Card.DbKeys.TABLE_NAME;
    }

    @Override
    protected IDataSnapshotConverter<Card> getDateSnapshotConverter() {
        return new Card.DataSnapshotConverter();
    }
}
