package com.github.biba.flashlang.operations.impl.info.firebase.impl.user;

import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.user.User;
import com.github.biba.flashlang.firebase.db.connector.IDataSnapshotConverter;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.generic.AbstractLoadSingleOperation;
import com.github.biba.lib.contracts.ICallback;

public class LoadSingleOperation extends AbstractLoadSingleOperation<User> {

    public LoadSingleOperation(final ICallback<User> pCallback, final Selector pSelector) {
        super(pCallback, pSelector);
    }

    @Override
    protected String getTableName() {
        return User.DbKeys.TABLE_NAME;
    }

    @Override
    protected IDataSnapshotConverter<User> getDateSnapshotConverter() {
        return new User.DataSnapshotConverter();
    }
}
