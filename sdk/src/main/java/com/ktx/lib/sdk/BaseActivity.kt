package com.ktx.lib.sdk

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import com.ktx.lib.R
import com.ktx.lib.base.BaseConfig
import com.ktx.lib.databinding.ActivityBaseLayoutBinding
import com.ktx.lib.databinding.UiLoadingLayoutBinding
import com.ktx.lib.ext.darkMode
import com.ktx.lib.ext.immersive
import com.ktx.lib.ext.statusBarColor
import com.ktx.lib.manager.ActivityManager
import com.ktx.lib.manager.DialogManager
import com.ktx.lib.utils.UHandler
import com.ktx.lib.utils.UPermission
import com.ktx.lib.widget.Progress

abstract class BaseActivity : AppCompatActivity(), LifecycleObserver {

    protected val TAG: String by lazy {
        this::class.java.simpleName
    }

    private lateinit var mContext: Context

    private var REQUEST_CODE_PERMISSION = -10

    lateinit var mBaseBinding: ActivityBaseLayoutBinding

    private lateinit var mLoadingView: View

    private var mProgress: Progress? = null

    protected val mActivityManager by lazy {
        ActivityManager.instance
    }

    protected val mDialogManager by lazy {
        DialogManager.instance
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
        mActivityManager.addActivity(this)

//        try {
        mContext = this
        initBase()
        initialize()
//        } catch (e: Exception) {

//        }
    }

    abstract fun initialize()

    private fun initBase() {
        darkMode(BaseConfig.statusBarDarkMode)
        statusBarColor(ContextCompat.getColor(this, BaseConfig.statusBarColor))
        mBaseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base_layout)
    }

    private fun loadLoadingView(): View {
        val binding = DataBindingUtil.inflate<UiLoadingLayoutBinding>(
            layoutInflater,
            R.layout.ui_loading_layout,
            null,
            false
        )
        return binding.root
    }

    /**
     * 动态改变状态栏颜色和标题
     */
    fun setDarkTitle(dark: Boolean, color: Int, title: String) {
        try {
            darkMode(dark)
            statusBarColor(ContextCompat.getColor(this, color))
            setBarTitle(title)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 设置标题
     */
    protected fun setBarTitle(title: String) {
        mBaseBinding.mActionBar.run {
            setBarTitle(title)
        }
    }

    /**
     * 隐藏标题栏左侧按钮
     */
    fun hideLeftBack() {
        mBaseBinding.mActionBar.hideLeftBack()
    }

    /**
     * 隐藏标题栏
     */
    fun hideActionBar() {
        mBaseBinding.mActionBar.isVisible = false
    }

    /**
     * 透明状态栏
     */
    fun translucentWindow(dark: Boolean) {
        try {
            immersive(0, dark)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 设置缺省页
     */
    fun setEmptyOrError(view: View) {
        mBaseBinding.mBaseContainer.isVisible = false
        mBaseBinding.mEmptyOrError.run {
            isVisible = true
            removeAllViews()
            addView(view)
        }
    }

    /**
     * 隐藏
     */
    fun hideEmptyOrErrorView() {
        mBaseBinding.mEmptyOrError.isVisible = false
        mBaseBinding.mBaseContainer.isVisible = true
    }

    /**
     * 获取错误或为空的view
     */
    fun getEmptyOrErrorView(): FrameLayout {
        return mBaseBinding.mEmptyOrError
    }

    /**
     * 请求权限
     */
    protected fun requestPermission(permissions: Array<String>, requestCode: Int) {
        this.REQUEST_CODE_PERMISSION = requestCode
        if (UPermission.checkPermissions(this, permissions)) {
            permissionSuccess(REQUEST_CODE_PERMISSION)
        } else {
            val needPermissions: List<String> = UPermission.getDeniedPermissions(this, permissions)
            ActivityCompat.requestPermissions(
                this,
                needPermissions.toTypedArray(),
                REQUEST_CODE_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (UPermission.verifyPermissions(grantResults)) {
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
        overridePendingTransition(R.anim.from_left_in, R.anim.from_left_out)
        finish()
    }

    protected fun alert(msg: String) {
        if (!mBaseBinding.mBaseAlertText.isVisible) {
            mBaseBinding.mBaseAlertText.text = msg
            mBaseBinding.mBaseAlertText.visibility = VISIBLE
            UHandler.postDelayed {
                mBaseBinding.mBaseAlertText.visibility = GONE
            }
        }
    }

    /**
     * 显示模态进度框
     */
    protected fun showProgress(msg: String, tag: Int) {
        if (!isFinishing) {
            if (mProgress == null) {
                mProgress = Progress(this, msg, tag)
            }
            mProgress?.show(true)
            mProgress?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            mProgress?.setOnDismissListener {
                if (mProgress != null) {
                    mProgress?.dismiss()
                    mProgress = null
                }
            }
        }
    }
}
