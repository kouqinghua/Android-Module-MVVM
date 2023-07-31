package com.ktx.lib.ext

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// 仿购物页面recyclerview左右滑联动 右侧recyclerview调用 manager属于右侧recyclerview
fun RecyclerView.linkage(manager: LinearLayoutManager, left: RecyclerView, action: (Int) -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val firstIndex = manager.findFirstVisibleItemPosition()
            if (firstIndex != -1 && dy != 0) {
                left.smoothScrollToPosition(firstIndex)
                action.invoke(firstIndex)
            }
        }
    })
}