package com.lib.base.mvvm.v

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.lib.base.R
import com.lib.base.app.BaseConfig.statusBarDarkMode
import com.lib.base.databinding.ActivityBaseLayoutBinding
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
import com.lib.base.utils.darkMode
import me.jessyan.autosize.internal.CustomAdapt

abstract class BaseActivity<V : ViewBinding, VM : BaseViewModel> : AppCompatActivity(), FrameView<V>, CustomAdapt, NetworkStateChangeListener {

    protected val mHandler = Handler(Looper.getMainLooper())
    protected lateinit var mmContext: Context
    protected val mBinding: V by lazy(mode = LazyThreadSafetyMode.NONE) {
        BindingReflex.reflexViewBinding(javaClass, layoutInflater)
    }
    protected abstract val mViewModel: VM

    private lateinit var mBaseBinding: ActivityBaseLayoutBinding
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
        setToolbar(statusBarDarkMode, Color.WHITE)
        initNetworkListener()
        initBase()
    }

    private fun initBase() {
        mBaseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base_layout)
        mBaseBinding.mBaseContainer.addView(mBinding.root)
        mLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.loading_layout, null, false)
        mViewModel.loadState.observe(this@BaseActivity, observer)
        initialize()
        mBinding.initView()
        initRequestData()
    }

    protected open fun setToolbar(isDarkFont: Boolean, color: Int) {
        ImmersionBar.with(this)
            // 原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
            .statusBarDarkFont(isDarkFont)
            // 状态栏颜色，不写默认透明色
            // .statusBarColor(color)
            //去掉状态栏
            .fitsSystemWindows(false)
            .transparentBar()
            .init()
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