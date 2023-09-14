package com.lib.base.mvvm.v

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.lib.base.mvvm.vm.BaseViewModel
import com.lib.base.network.UIState
import com.lib.base.network.UIStateType
import com.lib.base.utils.BindingReflex
import com.lib.base.utils.console
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.internal.CustomAdapt
import kotlin.system.measureTimeMillis

abstract class BaseFragment<V : ViewBinding, VM : BaseViewModel> : Fragment(),FrameView<V>, CustomAdapt {

    lateinit var mmContext: Context

    // 私有的 ViewBinding 此写法来自 Google Android 官方
    private var _binding: V? = null

    protected val mBinding get() = _binding!!
    private var isLoaded = false

    protected abstract val mViewModel: VM

    override fun isBaseOnWidth(): Boolean {
        return true
    }

    override fun getSizeInDp(): Float {
        return 375F
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mmContext = context
        AutoSizeConfig.getInstance().isCustomFragment = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = BindingReflex.reflexViewBinding(javaClass, layoutInflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.loadState.observe(viewLifecycleOwner, observer)
        measureTimeMillis {
            // ARouter 依赖注入
            ARouter.getInstance().inject(this)
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            lazyInit()
            isLoaded = true
        }
    }

    private fun lazyInit() {
        initialize()
        _binding?.initView()
        initRequestData()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        isLoaded = false
    }
}