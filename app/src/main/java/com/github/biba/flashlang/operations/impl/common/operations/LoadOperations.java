package com.github.biba.flashlang.operations.impl.common.operations;

import com.github.biba.flashlang.domain.models.user.User;
import com.github.biba.flashlang.operations.impl.common.impl.LoadCurrentUserOperation;
import com.github.biba.lib.contracts.IOperation;

public class LoadOperations {

    public IOperation<User> currentUser() {
        return new LoadCurrentUserOperation();
    }

}
