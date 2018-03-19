package com.github.biba.flashlang.operations.impl.info.local.impl.card;

import com.github.biba.flashlang.domain.models.card.Card;
import com.github.biba.flashlang.operations.impl.info.local.impl.generic.AbstractUploadAllOperation;

public class UploadAllOperation extends AbstractUploadAllOperation<Card> {

    public UploadAllOperation(final Card[] pModels) {
        super(pModels);
    }
}
