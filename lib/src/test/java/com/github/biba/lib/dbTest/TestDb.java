package com.github.biba.lib.dbTest;

import com.github.biba.lib.db.IDb;

public class TestDb implements IDb {

    @Override
    public String getName() {
        return "testdb.db";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public Class<?>[] getTableModels() {
        return new Class[]{CorrectTableElement.class};
    }
}
