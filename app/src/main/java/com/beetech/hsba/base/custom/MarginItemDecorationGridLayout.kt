package com.beetech.hsba.base.custom

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecorationGridLayout(
    private val spaceSize: Int,
    private val spanCount: Int = 1,
    private val orientation: Int = GridLayoutManager.VERTICAL
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        val column = position % spanCount

        with(outRect) {
            if (orientation == GridLayoutManager.VERTICAL) {
                left = column*spaceSize/spanCount
                right = spaceSize - (column + 1) * spaceSize / spanCount
                if (parent.getChildAdapterPosition(view) >= spanCount) {
                    top = spaceSize
                }
            } else {
                top = column*spaceSize/spanCount
                bottom = spaceSize - (column + 1) * spaceSize / spanCount
                if (parent.getChildAdapterPosition(view) >= spanCount) {
                    left = spaceSize
                }
            }
        }
    }
}