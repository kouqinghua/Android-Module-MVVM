package com.ktx.lib.component

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration(private val space: Int, private val count: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        val column = position % count

        // column * (列间距 * (1f / 列数))
        outRect.left = column * space / count
        // 列间距 - (column + 1) * (列间距 * (1f /列数))
        outRect.right = space - (column + 1) * space / count

        // 如果position > 行数，说明不是在第一行，
        // 则不指定行高，其他行的上间距为 top=mRowSpacing
        if (position >= count) {
            outRect.top = space// item top
        }
    }
}