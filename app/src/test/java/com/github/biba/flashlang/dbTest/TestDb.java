package com.github.biba.flashlang.dbTest;

import com.github.biba.lib.db.IDb;

public class TestDb implements IDb {

    @Override
    public String getName() {
        return "test.info";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public Class<?>[] getTableModels() {
        return new Class[]{
                TestModel.class
        };
    }
}
