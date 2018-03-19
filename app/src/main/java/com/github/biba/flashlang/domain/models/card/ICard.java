package com.github.biba.flashlang.domain.models.card;

import com.github.biba.flashlang.domain.models.IIdentifiable;

public interface ICard extends IIdentifiable<String> {

    String getOwnerId();

    String getReferredCollectionId();

    String getSourceLanguageKey();

    String getSourceText();

    String getTargetLanguageKey();

    String getTranslatedText();

    String getPictureUrl();

}
