package com.github.biba.flashlang.operations.impl.common.operations;

import com.github.biba.flashlang.operations.impl.common.impl.fetch.FetchUserAchievementsOperation;
import com.github.biba.flashlang.operations.impl.common.impl.fetch.FetchUserCardsOperation;
import com.github.biba.flashlang.operations.impl.common.impl.fetch.FetchUserCollectionsOperation;
import com.github.biba.flashlang.operations.impl.common.impl.fetch.FetchUserDataOperation;
import com.github.biba.flashlang.operations.impl.common.impl.fetch.FetchUserInfoOperation;
import com.github.biba.lib.contracts.IOperation;

public class FetchOperations {

    public IOperation<Void> fetchAll(final String pUserId) {
        return new FetchUserDataOperation(pUserId);
    }

    public IOperation<Void> fetchUserInfo(final String pUserId) {
        return new FetchUserInfoOperation(pUserId);
    }

    public IOperation<Void> fetchUserAchievemets(final String pUserId) {
        return new FetchUserAchievementsOperation(pUserId);
    }

    public IOperation<Void> fetchUserCards(final String pUserId) {
        return new FetchUserCardsOperation(pUserId);
    }

    public IOperation<Void> fetchUserCollections(final String pUserId) {
        return new FetchUserCollectionsOperation(pUserId);
    }

}
