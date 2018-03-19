package com.github.biba.flashlang.operations.impl.common.operations;

import com.github.biba.flashlang.operations.impl.common.impl.IncreaseUserAchievementOperation;
import com.github.biba.lib.contracts.IOperation;

public class UpdateOperations {

    public IOperation<Void> increaseUserAchievement(final String pUserId, final int pConnectionsIncrement, final int pWordsIncrement) {
        return new IncreaseUserAchievementOperation(pUserId, pConnectionsIncrement, pWordsIncrement);
    }

}
