package com.github.biba.flashlang.operations.impl.info.firebase.impl.user;

import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.user.User;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.generic.AbstractDeleteOperation;

public class DeleteOperation extends AbstractDeleteOperation<User> {

    public DeleteOperation(final Selector pSelector) {
        super(pSelector);
    }

    @Override
    protected String getTableName() {
        return User.DbKeys.TABLE_NAME;
    }
}
