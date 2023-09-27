package com.module.grid.adapter

import com.lib.base.app.BaseBindAdapter
import com.module.grid.R
import com.module.grid.databinding.ItemMenuLayoutBinding
import com.module.grid.databinding.ItemRoomLayoutBinding

class RoomAdapter : BaseBindAdapter<String, ItemRoomLayoutBinding>(R.layout.item_room_layout) {

    override fun invoke(binding: ItemRoomLayoutBinding, data: String, position: Int) {
        binding.mRoomName.text = data
    }
}