package com.github.biba.flashlang.operations.impl.info.local.impl.collection;

import com.github.biba.flashlang.domain.models.collection.Collection;
import com.github.biba.flashlang.operations.impl.info.local.impl.generic.AbstractUploadAllOperation;

public class UploadAllOperation extends AbstractUploadAllOperation<Collection> {

    public UploadAllOperation(final Collection[] pModels) {
        super(pModels);
    }
}
