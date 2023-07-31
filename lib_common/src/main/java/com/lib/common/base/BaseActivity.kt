package com.lib.common.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import com.example.lib_common.R
import com.example.lib_common.databinding.*
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import com.lib.common.base.BaseConfig.statusBarColor
import com.lib.common.base.BaseConfig.statusBarDarkMode
import com.lib.common.dialog.XDialog
import com.lib.common.pageTransition
import com.lib.common.utils.Permisson
import com.lib.common.utils.Permisson.REQUEST_CODE_PERMISSION
import com.lib.common.utils.darkMode
import com.lib.common.utils.statusBarColor

abstract class BaseActivity : AppCompatActivity(), LifecycleObserver {

    protected lateinit var mContext: Context

    lateinit var mBaseBinding: ActivityBaseLayoutBinding
    protected lateinit var mLoadingBinding: LayoutLoadingBinding
    protected lateinit var mLoading: XDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)

        initBase()
        initialize()
    }

    abstract fun initialize()

    private fun initBase() {
        mBaseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base_layout)

        mLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.layout_loading, null, false)
        mLoading = XDialog.Builder(this).setLayoutView(mLoadingBinding.root).create()

        darkMode(statusBarDarkMode)
    }

    // 动态改变状态栏颜色和标题
    fun setDarkTitle(dark: Boolean, color: Int, title: String) {
        try {
            darkMode(dark)
            statusBarColor(color)
            setBarTitle(title)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 设置标题
    protected fun setBarTitle(title: String) {
        mBaseBinding.mActionBar.run { setBarTitle(title) }
    }

    // 隐藏标题栏左侧按钮
    fun hideLeftIcon() {
        mBaseBinding.mActionBar.hideLeftBack()
    }

    // 隐藏标题栏
    fun hideActionBar() {
        mBaseBinding.mActionBar.isVisible = false
    }

    /**
     * 请求权限
     */
    protected fun applyPermission(permissions: Array<String>, requestCode: Int) {
        REQUEST_CODE_PERMISSION = requestCode
        if (Permisson.checkPermissions(this, permissions)) {
            permissionSuccess(requestCode)
        } else {
            val needPermissions: List<String> = Permisson.getDeniedPermissions(this, permissions)
            ActivityCompat.requestPermissions(this, needPermissions.toTypedArray(), requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (Permisson.verifyPermissions(grantResults)) {
                permissionSuccess(REQUEST_CODE_PERMISSION)
            } else {
                permissionFail(REQUEST_CODE_PERMISSION)
            }
        }
    }

    protected open fun permissionSuccess(requestCode: Int) {}

    protected open fun permissionFail(requestCode: Int) {}

    override fun onBackPressed() {
        super.onBackPressed()
        pageTransition()
        finish()
    }
}