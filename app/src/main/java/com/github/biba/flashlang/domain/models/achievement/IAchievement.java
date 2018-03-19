package com.github.biba.flashlang.domain.models.achievement;

import com.github.biba.flashlang.domain.models.IIdentifiable;

public interface IAchievement extends IIdentifiable<String> {

    String getOwnerId();

    long getTotalConnections();

    long getTotalWords();

}
