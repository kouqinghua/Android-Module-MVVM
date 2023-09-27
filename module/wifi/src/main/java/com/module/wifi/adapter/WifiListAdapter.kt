package com.module.wifi.adapter

import com.lib.base.app.BaseBindAdapter
import com.module.wifi.R
import com.module.wifi.bean.WifiEntity
import com.module.wifi.databinding.ItemWifiBinding

class WifiListAdapter : BaseBindAdapter<WifiEntity, ItemWifiBinding>(R.layout.item_wifi) {

    override fun invoke(binding: ItemWifiBinding, data: WifiEntity, position: Int) {
        data.run {
            binding.tvWifiName.text = wifiSSID
            binding.tvWifiSsid.text = wifiBSSID
            binding.ivWifiStrength.setImageResource(getStrengthIcon(wifiStrength))
            binding.ivNeedPassword.setImageResource(if (needPassword) R.mipmap.icon_lock else R.mipmap.icon_unlock)
        }
    }

    private fun getStrengthIcon(wifiStrength: Int): Int {
        return when (wifiStrength) {
            0 -> R.mipmap.wifi_strength_0
            1 -> R.mipmap.wifi_strength_1
            2 -> R.mipmap.wifi_strength_2
            else -> R.mipmap.wifi_strength_3
        }
    }
}