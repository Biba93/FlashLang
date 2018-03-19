package com.github.biba.flashlang.domain.models.user;

import com.github.biba.flashlang.domain.models.IIdentifiable;

public interface IUser extends IIdentifiable<String> {

    String getName();

    String getPictureUrl();
}
