package com.github.biba.flashlang.db.connector

import android.database.Cursor

interface ICursorConverter<out Element> {

    fun convert(pCursor: Cursor): Element

}
