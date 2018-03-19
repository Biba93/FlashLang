package com.github.biba.flashlang.firebase.utils

import com.github.biba.flashlang.domain.db.Selector
import com.google.firebase.database.Query

class SelectorUtils {

    companion object {

        fun applySelector(pQuery: Query, pSelector: Selector): Query {
            val finalQuery: Query
            when (pSelector) {
                is Selector.ByFieldSelector -> {
                    finalQuery = pQuery.orderByChild(pSelector.mFieldName)
                            .equalTo(pSelector.mFieldValue)
                    return finalQuery
                }
                else -> {
                    throw IllegalStateException("No such selector")
                }
            }
        }
    }

}
