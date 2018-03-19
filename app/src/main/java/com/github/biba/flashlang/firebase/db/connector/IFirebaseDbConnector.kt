package com.github.biba.flashlang.firebase.db.connector

import com.github.biba.flashlang.db.IDbModel
import com.github.biba.flashlang.domain.db.Selector
import com.github.biba.flashlang.firebase.db.FirebaseQuery
import com.github.biba.flashlang.firebase.db.annotations.FirebaseDbElement
import com.google.firebase.database.Query

interface IFirebaseDbConnector {

    fun getKeyForElement(pTableName: String): String

    fun <Element : IDbModel<String>> insert(@FirebaseDbElement pElement: Element)

    fun <Element : IDbModel<String>> insert(@FirebaseDbElement pElements: Array<Element>)

    fun delete(pTableName: String, pSelector: Selector)

    fun <Element : IDbModel<String>> update(@FirebaseDbElement pElement: Element, pSelector: Selector)

    fun <Element : IDbModel<String>> query(pFirebaseQuery: FirebaseQuery<Element>): Query?

    fun <Element : IDbModel<String>> query(): FirebaseQuery<Element>

    fun <Element : IDbModel<String>> get(pFirebaseQuery: FirebaseQuery<Element>,
                                         pCallback: IGetCallback<Element>)

    class Impl {
        companion object {
            fun getInstance(): IFirebaseDbConnector {
                return FirebaseDbConnector()
            }
        }
    }


}
