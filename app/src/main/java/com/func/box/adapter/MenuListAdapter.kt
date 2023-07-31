package com.func.box.adapter

import com.func.box.R
import com.func.box.bean.Menu
import com.func.box.databinding.ItemMenuListLayoutBinding
import com.ktx.lib.base.BaseBindAdapter

class MenuListAdapter(layoutId: Int) : BaseBindAdapter<Menu, ItemMenuListLayoutBinding>(layoutId) {

    private lateinit var adapter: MenuAdapter

    private var onChildClick: ((parent: Int, child: Int) -> Unit)? = null
    private var onItemMinus: ((parent: Int, child: Int) -> Unit)? = null
    private var onItemAdd: ((parent: Int, child: Int) -> Unit)? = null

    fun setItemMinus(click: (parent: Int, child: Int) -> Unit) {
        onItemMinus = click
    }

    fun setItemAdd(click: (parent: Int, child: Int) -> Unit) {
        onItemAdd = click
    }

    fun setChildClick(click: (parent: Int, child: Int) -> Unit) {
        this.onChildClick = click
    }

    override fun invoke(binding: ItemMenuListLayoutBinding, data: Menu, position: Int) {
        adapter = MenuAdapter(R.layout.item_menu_layout)
        adapter.setData(data.list)
        binding.mMenus.adapter = adapter

        adapter.setOnItemClick {
            onChildClick?.invoke(position, it)
        }

        adapter.setItemAdd {
            onItemAdd?.invoke(position, it)
        }

        adapter.setItemMinus {
            onItemMinus?.invoke(position, it)
        }
    }
}