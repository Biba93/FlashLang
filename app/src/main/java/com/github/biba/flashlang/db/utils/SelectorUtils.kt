package com.github.biba.flashlang.db.utils

import com.github.biba.flashlang.Constants
import com.github.biba.flashlang.domain.db.Selector
import com.github.biba.lib.Constants.Sql

class SelectorUtils {

    companion object {
        fun getSelection(vararg pSelectors: Selector): String? {
            if (pSelectors.isEmpty()) return null
            val selections = Array(pSelectors.size, { i ->
                convertSelector(pSelectors[i])
            })
            if (selections.isEmpty()) return null

            return if (selections.size == 1) {
                selections[0]
            } else {
                concatSelections(selections)
            }
        }

        private fun convertSelector(pSelector: Selector): String {
            when (pSelector) {
                is Selector.ByFieldSelector -> {
                    return pSelector.getSelection()
                }
                else -> {
                    return Constants.Strings.EMPTY_STRING
                }
            }
        }

        private fun concatSelections(pSelections: Array<String>): String {
            var finalSelection = ""
            for (selection in pSelections) {
                if (selection.isEmpty()) {
                    continue
                } else {
                    if (finalSelection.isEmpty()) {
                        finalSelection = selection
                        continue
                    }
                    finalSelection = String.format(Sql.WHERE_AND_TEMPLATE, finalSelection, selection)
                }
            }
            return finalSelection
        }

        private fun Selector.ByFieldSelector.getSelection(): String {
            return String.format(Sql.WHERE_EQUAL_TEMPLATE,
                    this.mFieldName, this.mFieldValue)

        }
    }
}
