package com.github.biba.flashlang.operations.impl.info.firebase.impl.achievement;

import com.github.biba.flashlang.domain.models.achievement.Achievement;
import com.github.biba.flashlang.operations.impl.info.firebase.impl.generic.AbstractUploadOperation;

public class UploadOperation extends AbstractUploadOperation<Achievement> {

    public UploadOperation(final Achievement pModel) {
        super(pModel);
    }
}
