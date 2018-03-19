package com.github.biba.flashlang.operations.impl.info.local.impl.card;

import com.github.biba.flashlang.db.connector.ICursorConverter;
import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.card.Card;
import com.github.biba.flashlang.operations.impl.info.local.impl.generic.AbstractLoadListOperation;

public class LoadListOperation extends AbstractLoadListOperation<Card> {

    public LoadListOperation(final String pGroupBy, final Selector[] pSelectors) {
        super(pGroupBy, pSelectors);
    }

    @Override
    protected String getTableName() {
        return Card.DbKeys.TABLE_NAME;
    }

    @Override
    protected ICursorConverter<Card> getCursorConverter() {
        return new Card.CursorConverter();
    }
}
