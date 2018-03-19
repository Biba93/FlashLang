package com.github.biba.flashlang.operations.impl.info.local.impl.achievement;

import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.achievement.Achievement;
import com.github.biba.flashlang.operations.impl.info.local.impl.generic.AbstractDeleteOperation;

public class DeleteOperation extends AbstractDeleteOperation<Achievement> {

    public DeleteOperation(final Selector[] pSelectors) {
        super(pSelectors);
    }

    @Override
    protected String getTableName() {
        return Achievement.DbKeys.TABLE_NAME;
    }
}
