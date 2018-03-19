package com.github.biba.flashlang.operations.impl.info.firebase.impl.card;

import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.achievement.Achievement;
import com.github.biba.flashlang.domain.models.card.Card;
import com.github.biba.flashlang.firebase.db.connector.IDataSnapshotConverter;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.generic.AbstractLoadListOperation;
import com.github.biba.lib.contracts.ICallback;

import java.util.List;

public class LoadListOperation extends AbstractLoadListOperation<Card> {

    public LoadListOperation(final ICallback<List<Card>> pCallback, final Selector pSelector) {
        super(pCallback, pSelector);
    }

    @Override
    protected String getTableName() {
        return Achievement.DbKeys.TABLE_NAME;
    }

    @Override
    protected IDataSnapshotConverter<Card> getDataSnapshotConverter() {
        return new Card.DataSnapshotConverter();
    }
}
