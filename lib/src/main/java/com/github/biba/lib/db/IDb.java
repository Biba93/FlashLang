package com.github.biba.lib.db;

public interface IDb {

    String getName();

    int getVersion();

    Class<?>[] getTableModels();
}
