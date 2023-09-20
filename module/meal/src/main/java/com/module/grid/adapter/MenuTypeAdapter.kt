package com.module.grid.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import com.lib.base.app.BaseBindAdapter
import com.module.grid.R
import com.module.grid.bean.Menu
import com.module.grid.databinding.ItemMenuTypeLayoutBinding

class MenuTypeAdapter(layoutId: Int) : BaseBindAdapter<Menu, ItemMenuTypeLayoutBinding>(layoutId) {

    override fun invoke(binding: ItemMenuTypeLayoutBinding, data: Menu, position: Int) {
        R.layout.item_menu_type_layout
        binding.mMenuType.text = data.type
        if (data.check) {
            binding.mItem.setBackgroundColor(Color.parseColor("#F1F1F1"))
        } else {
            binding.mItem.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCheck(position: Int) {
        getData().forEach {
            it.check = false
        }

        getData()[position].check = true
        notifyDataSetChanged()
    }
}