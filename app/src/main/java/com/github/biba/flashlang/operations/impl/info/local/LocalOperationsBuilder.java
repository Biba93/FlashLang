package com.github.biba.flashlang.operations.impl.info.local;

import com.github.biba.flashlang.domain.models.achievement.Achievement;
import com.github.biba.flashlang.domain.models.card.Card;
import com.github.biba.flashlang.domain.models.collection.Collection;
import com.github.biba.flashlang.domain.models.user.User;
import com.github.biba.flashlang.operations.impl.info.local.operations.AchievementModelOperations;
import com.github.biba.flashlang.operations.impl.info.local.operations.CardModelOperations;
import com.github.biba.flashlang.operations.impl.info.local.operations.CollectionModelOperations;
import com.github.biba.flashlang.operations.impl.info.local.operations.UserModelOperations;

public class LocalOperationsBuilder {

    public IModelOperations<User> user() {
        return new UserModelOperations();
    }

    public IModelOperations<Card> card() {
        return new CardModelOperations();
    }

    public IModelOperations<Collection> collection() {
        return new CollectionModelOperations();
    }

    public IModelOperations<Achievement> achievement() {
        return new AchievementModelOperations();
    }

}
