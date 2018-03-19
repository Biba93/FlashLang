package com.github.biba.flashlang.operations.impl.info.firebase.impl.generic;

import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.firebase.db.connector.IDataSnapshotConverter;
import com.github.biba.flashlang.firebase.db.connector.IFirebaseDbConnector;
import com.github.biba.flashlang.firebase.db.connector.IGetCallback;
import com.github.biba.lib.contracts.ICallback;
import com.github.biba.lib.contracts.IOperation;

import java.util.List;

public abstract class AbstractLoadListOperation<Model extends IDbModel<String>> implements IOperation<Void> {

    private final ICallback<List<Model>> mCallback;
    private final Selector mSelector;

    public AbstractLoadListOperation(final ICallback<List<Model>> pCallback, final Selector pSelector) {
        mCallback = pCallback;
        mSelector = pSelector;
    }

    @Override
    public Void perform() throws Exception {
        //noinspection unchecked
        IFirebaseDbConnector.Impl.Companion.getInstance()
                .query()
                .tableName(getTableName())
                .selector(mSelector)
                .converter(getDataSnapshotConverter())
                .get(new IGetCallback<Model>() {

                    @Override
                    public void onSuccess(final List<? extends Model> pModels) {
                        if (pModels != null) {
                            //noinspection unchecked
                            mCallback.onSuccess((List<Model>) pModels);
                        } else {
                            mCallback.onError(new Exception("Can't get list!"));
                        }
                    }

                    @Override
                    public void onError(final Throwable pThrowable) {
                        mCallback.onError(pThrowable);
                    }
                });
        return null;
    }

    protected abstract String getTableName();

    protected abstract IDataSnapshotConverter<Model> getDataSnapshotConverter();
}
