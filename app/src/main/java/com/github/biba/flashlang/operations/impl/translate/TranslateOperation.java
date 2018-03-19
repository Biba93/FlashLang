package com.github.biba.flashlang.operations.impl.translate;

import com.github.biba.flashlang.api.ITranslator;
import com.github.biba.flashlang.api.models.translation.ITranslation;
import com.github.biba.flashlang.api.request.ITranslationRequest;
import com.github.biba.lib.context.ContextHolder;
import com.github.biba.lib.contracts.IOperation;

public class TranslateOperation implements IOperation<ITranslation> {

    private final ITranslationRequest mRequest;

    public TranslateOperation(final ITranslationRequest pRequest) {
        mRequest = pRequest;
    }

    @Override
    public ITranslation perform() throws Exception {
        return ITranslator.Impl.getTranslator(ContextHolder.getContext())
                .translate(mRequest);
    }

}
