package com.github.biba.flashlang.firebase.db.connector

import com.google.firebase.database.DataSnapshot

interface IDataSnapshotConverter<out Element> {

    fun convert(pSnapshot: DataSnapshot): Element
}
