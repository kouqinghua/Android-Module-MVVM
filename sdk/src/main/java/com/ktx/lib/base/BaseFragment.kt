package com.ktx.lib.base

import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.ktx.lib.Constant
import com.ktx.lib.bus.BusData
import com.ktx.lib.bus.LiveDataBus
import com.ktx.lib.sdk.SdkFragment
import com.ktx.lib.utils.Mem
import com.ktx.lib.utils.UPermission

abstract class BaseFragment(layoutId: Int) : SdkFragment(layoutId) {


    override fun initialize() {

        initView()
        observer()
        initData()
    }

    abstract fun initView()

    abstract fun observer()

    protected open fun initData() {}

    fun setLoadState(owner: LifecycleOwner, state: MutableLiveData<LoadState>, msg: String = "加载中..") {
        state.observe(owner) {
            setState(it, msg)
        }
    }

    fun hasToken(): Boolean {
        return !Mem.string(Constant.TOKEN).isNullOrEmpty()
    }

    fun postValue(key: String, data: Any? = null) {
        LiveDataBus.get().setSticky(key).postValue(BusData("", data))
    }

    fun postValue(key: String, value: String, data: Any? = null) {
        LiveDataBus.get().setSticky(key).postValue(BusData(value, data))
    }

    protected fun acceptValue(key: String, observer: Observer<BusData?>) {
        LiveDataBus.get().getSticky(key)?.observe(this, observer)
    }

    protected fun glide(url: String?, image: ImageView) {
        url?.let {
            Glide.with(this).load(url).into(image)
        }
    }

    /**
     * 请求权限
     */
    open fun requestPermission(permissions: Array<String>, requestCode: Int) {
        if (UPermission.checkPermissions(mActivity, permissions)) {
            permissionSuccess(requestCode)
        } else {
            val needPermissions: List<String> = UPermission.getDeniedPermissions(mActivity, permissions)
            ActivityCompat.requestPermissions(
                    mActivity,
                    needPermissions.toTypedArray(),
                    requestCode
            )
        }
    }

    protected open fun permissionSuccess(requestCode: Int) {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (UPermission.verifyPermissions(grantResults)) {
            permissionSuccess(requestCode)
        }
    }

    protected fun token(): String {
        return Mem.string(Constant.TOKEN)!!
    }
}