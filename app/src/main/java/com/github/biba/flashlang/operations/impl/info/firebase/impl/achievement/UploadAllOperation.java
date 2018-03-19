package com.github.biba.flashlang.operations.impl.info.firebase.impl.achievement;

import com.github.biba.flashlang.domain.models.achievement.Achievement;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.generic.AbstractUploadAllOperation;

public class UploadAllOperation extends AbstractUploadAllOperation<Achievement> {

    public UploadAllOperation(final Achievement[] pModels) {
        super(pModels);
    }
}
