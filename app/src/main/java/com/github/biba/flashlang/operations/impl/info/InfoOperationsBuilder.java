package com.github.biba.flashlang.operations.impl.info;

import com.github.biba.flashlang.operations.impl.common.CommonOperationsBuilder;
import com.github.biba.flashlang.operations.impl.info.firebase.FirebaseOperationsBuilder;
import com.github.biba.flashlang.operations.impl.info.local.LocalOperationsBuilder;
import com.github.biba.flashlang.operations.impl.info.storage.StorageOperationsBuilder;

public class InfoOperationsBuilder {

    public CommonOperationsBuilder common() {
        return new CommonOperationsBuilder();
    }

    public FirebaseOperationsBuilder firebase() {
        return new FirebaseOperationsBuilder();
    }

    public LocalOperationsBuilder local() {
        return new LocalOperationsBuilder();
    }

    public StorageOperationsBuilder storage() {
        return new StorageOperationsBuilder();
    }

}
