package com.github.biba.flashlang.operations.impl.translate;

import com.github.biba.flashlang.api.models.translation.ITranslation;
import com.github.biba.flashlang.api.request.ITranslationRequest;
import com.github.biba.flashlang.domain.models.user.IUser;
import com.github.biba.lib.contracts.ICallback;
import com.github.biba.lib.contracts.IOperation;

public class TranslateOperationBuilder {

    public IOperation<Void> getApiKey(final ICallback<String> pCallback) {
        return new GetTranslateApiKeyOperation(pCallback);
    }

    public IOperation<ITranslation> translate(final ITranslationRequest pRequest) {
        return new TranslateOperation(pRequest);
    }

    public IOperation<Void> saveTranslation(final IUser pOwner, final ITranslation pTranslation) {
        return new SaveTranslationOperation(pOwner, pTranslation);
    }

}
