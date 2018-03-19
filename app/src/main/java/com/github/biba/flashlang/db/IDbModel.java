package com.github.biba.flashlang.db;

import com.github.biba.flashlang.domain.models.IIdentifiable;

import java.util.Map;

public interface IDbModel<IdType> extends IIdentifiable<IdType> {

    Map<String, Object> convertToInsert();

    Map<String, Object> convertToUpdate();
}
