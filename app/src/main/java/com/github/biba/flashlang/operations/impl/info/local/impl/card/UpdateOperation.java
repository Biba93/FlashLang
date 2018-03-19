package com.github.biba.flashlang.operations.impl.info.local.impl.card;

import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.card.Card;
import com.github.biba.flashlang.operations.impl.info.local.impl.generic.AbstractUpdateOperation;

public class UpdateOperation extends AbstractUpdateOperation<Card> {

    public UpdateOperation(final Card pModel, final Selector[] pSelectors) {
        super(pModel, pSelectors);
    }
}
