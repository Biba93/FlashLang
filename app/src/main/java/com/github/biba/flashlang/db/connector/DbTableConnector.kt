package com.github.biba.flashlang.db.connector

import android.database.Cursor
import com.github.biba.flashlang.db.IDbModel
import com.github.biba.flashlang.db.utils.ConversionUtils.Companion.convertToInsert
import com.github.biba.flashlang.db.utils.ConversionUtils.Companion.convertToUpdate
import com.github.biba.flashlang.db.utils.SelectorUtils
import com.github.biba.flashlang.domain.db.Selector
import com.github.biba.lib.db.IDbOperations
import com.github.biba.lib.db.annotations.dbTableElement
import com.github.biba.lib.db.utils.DbUtils
import com.github.biba.lib.logs.Log
import com.github.biba.lib.utils.IOUtils
import java.util.*

class DbTableConnector : IDbTableConnector {

    private val mDbOperations = IDbOperations.Impl.getInstance()
    private val LOG_TAG = DbTableConnector::class.simpleName

    override fun <Element : IDbModel<String>> insert(@dbTableElement pElement: Element): Boolean {
        val tableName = getTableName(pElement)
        if (tableName != null) {
            return try {
                val inserted = mDbOperations.insert(tableName, convertToInsert(pElement))
                inserted > 0
            } catch (pE: Exception) {
                Log.e(LOG_TAG, pE.message)
                throw pE
            }

        }
        return false
    }

    override fun <Element : IDbModel<String>> insert(pTableName: String, pElements: Array<Element>): Boolean {
        val inserted = mDbOperations.bulkInsert(pTableName, convertToInsert(pElements))
        return inserted == pElements.size
    }

    override fun <Element : IDbModel<String>> insert(@dbTableElement pElements: Array<Element>): Boolean {
        var isInserted = true
        for (element in pElements) {
            isInserted = (isInserted and (insert(element)))
        }
        return isInserted
    }

    override fun delete(pTableName: String, vararg pSelectors: Selector): Boolean {
        val selection = SelectorUtils.getSelection(*pSelectors)
        val deleted = mDbOperations.delete(pTableName, selection, null)
        return deleted > 0
    }

    override fun <Element : IDbModel<String>> update(@dbTableElement pElement: Element, vararg pSelectors: Selector): Boolean {
        val selection = SelectorUtils.getSelection(*pSelectors)
        val tableName = getTableName(pElement)
        if (tableName != null) {
            return try {
                val updated = mDbOperations.update(tableName, convertToUpdate(pElement), selection, null)
                updated > 0
            } catch (pE: Exception) {
                Log.e(LOG_TAG, pE.message)
                throw pE
            }

        }
        return false
    }

    override fun <Element : IDbModel<String>> get(pTableName: String, pCursorConverter: ICursorConverter<Element>, groupBy: String?,
                                                  vararg pSelectors: Selector): List<Element>? {
        var cursor: Cursor? = null
        try {
            cursor = get(pTableName, groupBy, *pSelectors) ?: return null
            val elements: MutableList<Element>?
            elements = ArrayList()
            while (cursor.moveToNext()) {
                elements.add(pCursorConverter.convert(cursor))
            }
            return elements
        } catch (pE: Exception) {
            Log.e(LOG_TAG, pE.message)
        } finally {
            IOUtils.close(cursor)
        }
        return null
    }

    override fun get(pTableName: String, groupBy: String?, vararg pSelectors: Selector): Cursor? {
        val selection = SelectorUtils.getSelection(*pSelectors)
        val cursor: Cursor?
        try {
            cursor = mDbOperations.query()
                    .table(pTableName)
                    .selection(selection)
                    .groupBy(groupBy)
                    .cursor()
            return cursor
        } catch (pE: Exception) {
            Log.e(LOG_TAG, pE.message)
        }
        return null
    }

    private fun <Element : IDbModel<String>> getTableName(@dbTableElement pElement: Element): String? {
        return DbUtils.getTableName(pElement.javaClass)
    }


}
