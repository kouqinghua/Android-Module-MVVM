package com.ktx.lib.ext

import android.content.Intent
import androidx.lifecycle.Observer
import com.ktx.lib.R
import com.ktx.lib.bus.BusData
import com.ktx.lib.bus.LiveDataBus
import com.ktx.lib.sdk.BaseActivity

fun BaseActivity.start(c: Class<*>) {
    startActivity(Intent(this, c))
    overridePendingTransition(R.anim.from_right_in, R.anim.from_right_out)
}

fun BaseActivity.startFromBottom(c: Class<*>) {
    startActivity(Intent(this, c))
    overridePendingTransition(R.anim.from_bottom_in, R.anim.from_bottom_out)
}

fun BaseActivity.closeFromTop() {
    finish()
    overridePendingTransition(0, R.anim.from_top_out)
}

fun send(key: String, data: Any? = null) {
    LiveDataBus.get().setSticky(key).postValue(BusData("", data))
}

fun send(key: String, value: String, data: Any? = null) {
    LiveDataBus.get().setSticky(key).postValue(BusData(value, data))
}

fun BaseActivity.obtain(key: String, observer: Observer<BusData?>) {
    LiveDataBus.get().getSticky(key)?.observe(this, observer)
}