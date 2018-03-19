package com.github.biba.flashlang.api.request;

import com.github.biba.lib.contracts.IRequest;

public interface ITranslationRequest extends IRequest {

    String getSourceText();

    String getSourceLanguage();

    String getTargetLanguage();

    String getApiKey();

}
