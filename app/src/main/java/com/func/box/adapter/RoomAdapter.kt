package com.func.box.adapter

import com.func.box.databinding.ItemRoomLayoutBinding
import com.ktx.lib.base.BaseBindAdapter

class RoomAdapter(layoutId: Int) : BaseBindAdapter<String, ItemRoomLayoutBinding>(layoutId) {
    override fun invoke(binding: ItemRoomLayoutBinding, data: String, position: Int) {
        binding.mRoomName.text = data
    }
}