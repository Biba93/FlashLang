package com.github.biba.flashlang.operations.impl.info.local.impl.user;

import com.github.biba.flashlang.db.connector.ICursorConverter;
import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.user.User;
import com.github.biba.flashlang.operations.impl.info.local.impl.generic.AbstractLoadSingleOperation;

public class LoadSingleOperation extends AbstractLoadSingleOperation<User> {

    public LoadSingleOperation(final Selector[] pSelectors) {
        super(pSelectors);
    }

    @Override
    protected String getTableName() {
        return User.DbKeys.TABLE_NAME;
    }

    @Override
    protected ICursorConverter<User> getCursorConverter() {
        return new User.CursorConverter();
    }
}
