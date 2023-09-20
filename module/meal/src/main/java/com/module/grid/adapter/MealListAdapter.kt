package com.module.grid.adapter

import android.annotation.SuppressLint
import com.module.grid.bean.MenuItem
import com.lib.base.app.BaseBindAdapter
import com.module.grid.databinding.ItemMealLayoutBinding

class MealListAdapter(layoutId: Int) : BaseBindAdapter<MenuItem, ItemMealLayoutBinding>(layoutId) {

    @SuppressLint("SetTextI18n")
    override fun invoke(binding: ItemMealLayoutBinding, data: MenuItem, position: Int) {
        binding.run {
            mMenuTitle.text = data.title
            mPrice.text = "单价：¥ ${data.price}"
            mCount.text = "x ${data.count}"
        }
    }
}