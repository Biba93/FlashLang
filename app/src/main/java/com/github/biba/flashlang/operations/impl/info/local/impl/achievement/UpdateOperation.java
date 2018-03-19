package com.github.biba.flashlang.operations.impl.info.local.impl.achievement;

import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.domain.models.achievement.Achievement;
import com.github.biba.flashlang.operations.impl.info.local.impl.generic.AbstractUpdateOperation;

public class UpdateOperation extends AbstractUpdateOperation<Achievement> {

    public UpdateOperation(final Achievement pModel, final Selector[] pSelectors) {
        super(pModel, pSelectors);
    }
}
