package com.module.main.adapter

import com.lib.base.app.BaseBindAdapter
import com.module.main.databinding.RoomItemLayoutBinding

class RoomAdapter(layoutId: Int) : BaseBindAdapter<String, RoomItemLayoutBinding>(layoutId) {
    override fun invoke(binding: RoomItemLayoutBinding, data: String, position: Int) {
        binding.mRoomName.text = data
    }
}