package com.github.biba.flashlang.domain.models.collection;

import com.github.biba.flashlang.domain.models.IIdentifiable;

public interface ICollection extends IIdentifiable<String> {

    String getOwnerId();

    String getSourceLanguage();

    String getTargetLanguage();

    String getSourceLanguageCover();

    String getTargetLanguageCover();

}
