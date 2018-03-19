package com.github.biba.flashlang.db.utils

import android.content.ContentValues
import com.github.biba.flashlang.db.IDbModel
import com.github.biba.flashlang.domain.db.MapUtils

class ConversionUtils {

    companion object {
        fun <Element : IDbModel<String>> convertToInsert(pElement: Element): ContentValues {
            return MapUtils.toContentValues(pElement.convertToInsert())
        }

        fun <Element : IDbModel<String>> convertToInsert(pElements: Array<Element>): Array<ContentValues> {
            return Array(pElements.size, { i ->
                convertToInsert(pElements[i])
            })
        }

        fun <Element : IDbModel<String>> convertToUpdate(pElement: Element): ContentValues {
            return MapUtils.toContentValues(pElement.convertToUpdate())
        }

    }
}
