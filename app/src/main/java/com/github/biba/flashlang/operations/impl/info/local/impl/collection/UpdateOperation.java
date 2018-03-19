package com.github.biba.flashlang.operations.impl.info.local.impl.collection;

import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.collection.Collection;
import com.github.biba.flashlang.operations.impl.info.local.impl.generic.AbstractUpdateOperation;

public class UpdateOperation extends AbstractUpdateOperation<Collection> {

    public UpdateOperation(final Collection pModel, final Selector[] pSelectors) {
        super(pModel, pSelectors);
    }
}
