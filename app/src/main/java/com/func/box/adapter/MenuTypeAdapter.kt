package com.func.box.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import com.func.box.bean.Menu
import com.func.box.databinding.ItemMenuTypeLayoutBinding
import com.lib.base.app.BaseBindAdapter

class MenuTypeAdapter(layoutId: Int) : BaseBindAdapter<Menu, ItemMenuTypeLayoutBinding>(layoutId) {

    override fun invoke(binding: ItemMenuTypeLayoutBinding, data: Menu, position: Int) {
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