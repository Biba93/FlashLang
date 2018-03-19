package com.github.biba.flashlang.db;

import com.github.biba.flashlang.Constants;
import com.github.biba.flashlang.domain.models.achievement.Achievement;
import com.github.biba.flashlang.domain.models.card.Card;
import com.github.biba.flashlang.domain.models.collection.Collection;
import com.github.biba.flashlang.domain.models.user.User;
import com.github.biba.lib.db.IDb;

public final class AppDb implements IDb {

    @Override
    public String getName() {
        return Constants.Db.DB_NAME;
    }

    @Override
    public int getVersion() {
        return Constants.Db.DB_VERSION;
    }

    @Override
    public Class<?>[] getTableModels() {
        return new Class[]{
                User.class,
                Achievement.class,
                Collection.class,
                Card.class,
        };
    }
}
