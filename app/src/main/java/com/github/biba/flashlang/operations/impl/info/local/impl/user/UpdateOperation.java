package com.github.biba.flashlang.operations.impl.info.local.impl.user;

import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.user.User;
import com.github.biba.flashlang.operations.impl.info.local.impl.generic.AbstractUpdateOperation;

public class UpdateOperation extends AbstractUpdateOperation<User> {

    public UpdateOperation(final User pModel, final Selector[] pSelectors) {
        super(pModel, pSelectors);
    }
}
