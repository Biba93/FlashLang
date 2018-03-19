package com.github.biba.flashlang.operations.impl.info.local;

import android.database.Cursor;

import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.lib.contracts.IOperation;

import java.util.List;

public interface IModelOperations<Model extends IDbModel<String>> {

    IOperation<Boolean> upload(Model pModel);

    IOperation<Boolean> uploadAll(Model[] pModels);

    IOperation<Model> loadSingle(Selector... pSelectors);

    IOperation<List<Model>> loadList(String pGroupBy, Selector... pSelectors);

    IOperation<Cursor> loadCursor(String pGroupBy, Selector... pSelectors);

    IOperation<Boolean> update(Model pModel, Selector... pSelectors);

    IOperation<Boolean> delete(Selector... pSelectors);

}
