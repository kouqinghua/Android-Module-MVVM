package com.func.box.adapter

import android.annotation.SuppressLint
import com.func.box.bean.MenuItem
import com.func.box.databinding.ItemMenuLayoutBinding
import com.lib.base.app.BaseBindAdapter
import com.lib.base.utils.background

class MenuAdapter(layoutId: Int) : BaseBindAdapter<MenuItem, ItemMenuLayoutBinding>(layoutId) {

    private var onItemMinus: ((position: Int) -> Unit)? = null
    private var onItemAdd: ((position: Int) -> Unit)? = null

    fun setItemMinus(click: (position: Int) -> Unit) {
        onItemMinus = click
    }

    fun setItemAdd(click: (position: Int) -> Unit) {
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