package com.github.biba.flashlang.operations.impl.info.firebase.impl.achievement;

import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.achievement.Achievement;
import com.github.biba.flashlang.firebase.db.connector.IDataSnapshotConverter;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.generic.AbstractLoadSingleOperation;
import com.github.biba.lib.contracts.ICallback;

public class LoadSingleOperation extends AbstractLoadSingleOperation<Achievement> {

    public LoadSingleOperation(final ICallback<Achievement> pCallback, final Selector pSelector) {
        super(pCallback, pSelector);
    }

    @Override
    protected String getTableName() {
        return Achievement.DbKeys.TABLE_NAME;
    }

    @Override
    protected IDataSnapshotConverter<Achievement> getDateSnapshotConverter() {
        return new Achievement.DataSnapshotConverter();
    }
}
