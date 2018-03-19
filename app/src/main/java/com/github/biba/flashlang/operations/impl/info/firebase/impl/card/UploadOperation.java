package com.github.biba.flashlang.operations.impl.info.firebase.impl.card;

import com.github.biba.flashlang.domain.models.card.Card;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.generic.AbstractUploadOperation;

public class UploadOperation extends AbstractUploadOperation<Card> {

    public UploadOperation(final Card pModel) {
        super(pModel);
    }
}
