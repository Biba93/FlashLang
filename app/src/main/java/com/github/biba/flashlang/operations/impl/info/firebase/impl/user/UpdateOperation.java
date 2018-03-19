package com.github.biba.flashlang.operations.impl.info.firebase.impl.user;

import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.user.User;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.generic.AbstractUpdateOperation;

public class UpdateOperation extends AbstractUpdateOperation<User> {

    public UpdateOperation(final User pModel, final Selector pSelector) {
        super(pModel, pSelector);
    }
}
