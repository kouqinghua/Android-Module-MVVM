package com.func.box.adapter

import android.annotation.SuppressLint
import com.func.box.bean.MenuItem
import com.func.box.databinding.ItemMealLayoutBinding
import com.lib.base.app.BaseBindAdapter

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