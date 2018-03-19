package com.github.biba.flashlang.operations.impl.info.local.impl.card;

import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.card.Card;
import com.github.biba.flashlang.operations.impl.info.local.impl.generic.AbstractDeleteOperation;

public class DeleteOperation extends AbstractDeleteOperation<Card> {

    public DeleteOperation(final Selector[] pSelectors) {
        super(pSelectors);
    }

    @Override
    protected String getTableName() {
        return Card.DbKeys.TABLE_NAME;
    }
}
