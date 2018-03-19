package com.github.biba.lib.dbTest;

import com.github.biba.lib.db.annotations.dbTable;
import com.github.biba.lib.db.annotations.type.dbForeignKey;

@dbTable(name = "wrongTable")
class WrongTable {

    @dbForeignKey(referredTableName = "someTable", referredTableColumnName = "someColumn")
    int someInt;

}
