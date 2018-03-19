package com.github.biba.flashlang.firebase.utils

import com.github.biba.flashlang.firebase.db.annotations.FirebaseDbElement

object DbUtils {

    fun getTableName(pClass: Class<*>?): String? {
        if (pClass == null) {
            return null
        }
        var name: String? = null
        val dbTableAnnotation = pClass.getAnnotation(FirebaseDbElement::class.java)
        if (dbTableAnnotation != null) {
            name = dbTableAnnotation.targetTableName
        }
        return name
    }

}
