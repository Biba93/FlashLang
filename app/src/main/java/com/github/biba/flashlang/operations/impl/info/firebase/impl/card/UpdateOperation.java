package com.github.biba.flashlang.operations.impl.info.firebase.impl.card;

import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.card.Card;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.generic.AbstractUpdateOperation;

public class UpdateOperation extends AbstractUpdateOperation<Card> {

    public UpdateOperation(final Card pModel, final Selector pSelector) {
        super(pModel, pSelector);
    }
}
