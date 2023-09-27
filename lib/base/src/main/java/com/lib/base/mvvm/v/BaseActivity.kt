package com.lib.base.mvvm.v

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ktx.immersionBar
import com.lib.base.R
import com.lib.base.app.BaseConfig.statusBarDarkMode
import com.lib.base.databinding.ActivityBaseBinding
import com.lib.base.databinding.LoadingLayoutBinding
import com.lib.base.mvvm.vm.BaseViewModel
import com.lib.base.network.UIState
import com.lib.base.network.UIStateType
import com.lib.base.network.state.AutoRegisterNetListener
import com.lib.base.network.state.NetworkStateChangeListener
import com.lib.base.network.state.NetworkTypeEnum
import com.lib.base.utils.BindingReflex
import com.lib.base.utils.BugFixUtils
import com.lib.base.utils.console
import me.jessyan.autosize.internal.CustomAdapt

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity(), FrameView<VB>, CustomAdapt, NetworkStateChangeListener {

    protected val mHandler = Handler(Looper.getMainLooper())
    protected lateinit var mmContext: Context
    protected lateinit var mmActivity: Activity
    protected val mBinding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        BindingReflex.reflexViewBinding(javaClass, layoutInflater)
    }
    protected abstract val mViewModel: VM

    private lateinit var mBaseBinding: ActivityBaseBinding
    private lateinit var mLoadingBinding: LoadingLayoutBinding

    // return true 时表示宽度适配
    // return false 时表示高度适配
    override fun isBaseOnWidth(): Boolean {
        return true
    }

    override fun getSizeInDp(): Float {
        return 375F
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mmContext = this
        mmActivity = this
        initNetworkListener()
        initBase()
    }

    private fun initBase() {
        mBaseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base)
        mBaseBinding.mBaseContainer.addView(mBinding.root)
        mLoadingBinding = LoadingLayoutBinding.inflate(layoutInflater)
        mViewModel.loadState.observe(this@BaseActivity, observer)
        setToolbar(statusBarDarkMode)
        initialize()
        mBinding.initView()
        initObserve()
        initRequestData()
    }

    // fits:false-->去掉状态栏
    protected open fun setToolbar(isDarkFont: Boolean) {
        immersionBar {
            statusBarDarkFont(isDarkFont)
            fitsSystemWindows(true)
            transparentBar()
        }
    }

    protected fun setBarTitle(title: String) {
        mBaseBinding.mActionBar.setBarTitle(title)
    }

    protected fun hideBackIcon() {
        mBaseBinding.mActionBar.hideLeftBack()
    }

    /**
     * 初始化网络状态监听
     */
    private fun initNetworkListener() {
        lifecycle.addObserver(AutoRegisterNetListener(this))
    }

    private val observer by lazy {
        Observer<UIState> {
            it?.let {
                when (it.stateType) {
                    UIStateType.LOADING -> {
                    }

                    UIStateType.SUCCESS -> {
                        console("UIStateType.SUCCESS")
                    }

                    UIStateType.EMPTY -> {
                        console("UIStateType.EMPTY")
                    }

                    UIStateType.ERROR -> {
                        console("UIStateType.ERROR")
                    }

                    UIStateType.NETWORK_ERROR -> {
                        console("UIStateType.NETWORK_ERROR")
                    }
                }
            }
        }
    }

    override fun networkTypeChange(type: NetworkTypeEnum) {}

    override fun networkConnectChange(isConnected: Boolean) {
        Toast.makeText(this, if (isConnected) "网络已连接" else "网络已断开", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        mHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
        // 解决某些特定机型会触发的Android本身的Bug
        BugFixUtils().fixSoftInputLeaks(this)
    }
}