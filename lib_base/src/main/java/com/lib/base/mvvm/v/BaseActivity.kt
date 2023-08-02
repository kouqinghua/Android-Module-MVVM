package com.lib.base.mvvm.v

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.lib.base.R
import com.lib.base.app.BaseConfig.statusBarDarkMode
import com.lib.base.databinding.ActivityBaseLayoutBinding
import com.lib.base.databinding.LoadingLayoutBinding
import com.lib.base.mvvm.vm.BaseViewModel
import com.lib.base.network.UIState
import com.lib.base.network.UIStateType
import com.lib.base.utils.BindingReflex
import com.lib.base.utils.console
import com.lib.base.utils.darkMode

abstract class BaseActivity<V : ViewBinding, VM : BaseViewModel> : AppCompatActivity(), LifecycleObserver {

    protected val mHandler = Handler(Looper.getMainLooper())
    protected lateinit var mContext: Context
    protected val mBinding: V by lazy(mode = LazyThreadSafetyMode.NONE) {
        BindingReflex.reflexViewBinding(javaClass, layoutInflater)
    }
    protected abstract val mViewModel: VM

    lateinit var mBaseBinding: ActivityBaseLayoutBinding
    protected lateinit var mLoadingBinding: LoadingLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)

        initBase()
        initialize()
    }

    abstract fun initialize()

    private fun initBase() {
        mBaseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base_layout)
        mBaseBinding.mBaseContainer.addView(mBinding.root)
        mLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.loading_layout, null, false)
        mViewModel.loadState.observe(this@BaseActivity, observer)
        darkMode(statusBarDarkMode)
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
}