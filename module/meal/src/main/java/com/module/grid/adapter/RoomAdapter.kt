package com.module.grid.adapter

import com.lib.base.app.BaseBindAdapter
import com.module.grid.R
import com.module.grid.databinding.ItemMenuLayoutBinding

class RoomAdapter : BaseBindAdapter<String, ItemMenuLayoutBinding>(R.layout.item_menu_layout) {
    override fun invoke(binding: ItemMenuLayoutBinding, data: String, position: Int) {
//        binding.mRoomName.text = data
    }
}