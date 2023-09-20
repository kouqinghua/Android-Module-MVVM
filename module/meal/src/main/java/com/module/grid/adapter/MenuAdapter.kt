package com.module.grid.adapter

import android.annotation.SuppressLint
import com.module.grid.bean.MenuItem
import com.lib.base.app.BaseBindAdapter
import com.lib.base.utils.background
import com.module.grid.R
import com.module.grid.databinding.ItemMenuLayoutBinding

class MenuAdapter : BaseBindAdapter<MenuItem, ItemMenuLayoutBinding>(R.layout.item_menu_layout) {

    private var onItemMinus: ((position: Int) -> Unit)? = null
    private var onItemAdd: ((position: Int) -> Unit)? = null

    fun setItemMinus(click: (position: Int) -> Unit) {
        onItemMinus = click
    }

    fun setItemAdd(click: (position: Int) -> Unit) {
        R.layout.item_menu_layout
        onItemAdd = click
    }

    @SuppressLint("SetTextI18n")
    override fun invoke(binding: ItemMenuLayoutBinding, data: MenuItem, position: Int) {
        binding.run {
            mMenu.text = data.title
            mCount.text = data.count.toString()
            mPrice.text = "单价：¥ ${data.price}"

            mItem.background(if (data.count > 0) "#DDDDDD" else "#FFFFFF")

            mMinus.setOnClickListener {
                onItemMinus?.invoke(position)
            }

            mAdd.setOnClickListener {
                onItemAdd?.invoke(position)
            }
        }
    }
}