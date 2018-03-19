package com.github.biba.flashlang.operations.impl.info.local.impl.user;

import com.github.biba.flashlang.domain.models.user.User;
import com.github.biba.flashlang.operations.impl.info.local.impl.generic.AbstractUploadAllOperation;

public class UploadAllOperation extends AbstractUploadAllOperation<User> {

    public UploadAllOperation(final User[] pModels) {
        super(pModels);
    }
}
