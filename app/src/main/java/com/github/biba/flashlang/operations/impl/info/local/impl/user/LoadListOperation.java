package com.github.biba.flashlang.operations.impl.info.local.impl.user;

import com.github.biba.flashlang.db.connector.ICursorConverter;
import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.user.User;
import com.github.biba.flashlang.operations.impl.info.local.impl.generic.AbstractLoadListOperation;

public class LoadListOperation extends AbstractLoadListOperation<User> {

    public LoadListOperation(final String pGroupBy, final Selector[] pSelectors) {
        super(pGroupBy, pSelectors);
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
