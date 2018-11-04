package com.grzegorziwanek.tumblrviewer.util

import android.content.Context
import android.support.design.chip.Chip
import android.support.design.chip.ChipGroup
import com.grzegorziwanek.tumblrviewer.R
import javax.inject.Inject

class ChipGenerator @Inject constructor() {

    fun removeRedundantChips(container: ChipGroup, newCount: Int) {
        if (container.childCount > newCount) {
            for (index in (container.childCount - 1) downTo ((container.childCount - 1) - newCount)) {
                container.removeViewAt(index)
            }
        }
    }

    fun addChips(container: ChipGroup, items: List<String>) {
        for ((i, item) in items.withIndex()) {
            if (container.getChildAt(i) == null) container.addView(getChip(item, container.context))
            else (container.getChildAt(i) as Chip).text = item
        }
    }

    private fun getChip(text: String, context: Context): Chip {
        val chip = Chip(context)
        chip.setTextAppearanceResource(R.style.ChipTextStyle)
        chip.text = text
        chip.isClickable = false
        chip.setChipBackgroundColorResource(R.color.grey_10)
        chip.elevation = 6f
        return chip
    }
}