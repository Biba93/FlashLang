package com.github.biba.flashlang.firebase.db.connector

import com.github.biba.flashlang.db.IDbModel
import com.github.biba.flashlang.domain.db.Selector
import com.github.biba.flashlang.firebase.db.FirebaseQuery
import com.github.biba.flashlang.firebase.db.annotations.FirebaseDbElement
import com.github.biba.flashlang.firebase.utils.DbUtils
import com.github.biba.flashlang.firebase.utils.SelectorUtils
import com.google.firebase.database.*

class FirebaseDbConnector : IFirebaseDbConnector {

    private val mFirebaseDatabase = FirebaseDatabase.getInstance()

    override fun getKeyForElement(pTableName: String): String {
        val key = mFirebaseDatabase.reference.child(pTableName).push().key
        return key
    }

    override fun <Element : IDbModel<String>> insert(pElement: Element) {
        val tableName = getTableName(pElement)
        if (!tableName.isNullOrBlank()) {
            mFirebaseDatabase.reference.child(tableName).child(pElement.id)
                    .setValue(pElement.convertToInsert())
        }

    }

    override fun <Element : IDbModel<String>> insert(pElements: Array<Element>) {
        for (element in pElements) {
            insert(element)
        }

    }

    override fun delete(pTableName: String, pSelector: Selector) {
        val query = query(pTableName, pSelector)
        if (query != null) {
            try {
                val listener: ValueEventListener = object : ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (appleSnapshot in dataSnapshot.children) {
                            appleSnapshot.ref.removeValue()
                            query.removeEventListener(this)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        query.removeEventListener(this)
                    }
                }
                query.addValueEventListener(listener)
            } catch (e: Exception) {

            }
        }
    }

    override fun <Element : IDbModel<String>> update(pElement: Element, pSelector: Selector) {
        val tableName = getTableName(pElement)
        if (!tableName.isNullOrBlank()) {
            val query = query(tableName!!, pSelector)
            if (query != null) {
                val listener: ValueEventListener = object : ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (appleSnapshot in dataSnapshot.children) {
                            appleSnapshot.ref.updateChildren(pElement.convertToUpdate())
                            query.removeEventListener(this)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        query.removeEventListener(this)
                    }
                }
                query.addValueEventListener(listener)
            }

        }
    }

    override fun <Element : IDbModel<String>> query(pFirebaseQuery: FirebaseQuery<Element>): Query? {
        val tableName = pFirebaseQuery.tableName ?: return null
        val selector = pFirebaseQuery.selector

        val query = query(tableName, selector)
        return query
    }

    override fun <Element : IDbModel<String>> query(): FirebaseQuery<Element> {
        return FirebaseQuery<Element>()
    }

    override fun <Element : IDbModel<String>> get(pFirebaseQuery: FirebaseQuery<Element>,
                                                  pCallback: IGetCallback<Element>) {
        val query = query(pFirebaseQuery)
        if (query != null) {
            val converter = pFirebaseQuery.converter
            if (converter != null) {
                val elements: MutableList<Element> = mutableListOf()
                val listener: ValueEventListener = object : ValueEventListener {
                    override fun onCancelled(pError: DatabaseError?) {
                        query.removeEventListener(this)
                        pCallback.onError(InterruptedException("Event Canceled"))
                    }

                    override fun onDataChange(pSnapshot: DataSnapshot?) {
                        query.removeEventListener(this)

                        if (pSnapshot != null) {
                            val children = pSnapshot.children
                            children.mapTo(elements) { converter.convert(it) }
                        }
                        pCallback.onSuccess(elements)
                    }
                }
                query.addListenerForSingleValueEvent(listener)
            } else {
                pCallback.onError(NullPointerException("Converter is null!"))
            }
        } else {
            pCallback.onError(NullPointerException("FirebaseQuery is null!"))
        }
    }

    private fun query(pTableName: String, pSelector: Selector?): Query? {
        val ref = mFirebaseDatabase.reference.child(pTableName)
        return if (pSelector == null) {
            ref
        } else {
            val query: Query? = SelectorUtils.applySelector(ref, pSelector)
            query
        }
    }

    private fun <Element : IDbModel<String>> getTableName(@FirebaseDbElement pElement: Element): String? {
        return DbUtils.getTableName(pElement.javaClass)
    }


}
