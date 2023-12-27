package com.lib.base.mvvm.v

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lib.base.R
import com.lib.base.mvvm.vm.BaseViewModel
import com.lib.base.network.UIState
import com.lib.base.network.UIStateType
import com.lib.base.network.log.LogCat
import com.lib.base.utils.BindingReflex
import com.lib.base.utils.toast
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

abstract class BaseBottomDialogFragment<VM : BaseViewModel, VB : ViewDataBinding> : BottomSheetDialogFragment() {


    private var _binding: VB? = null

    protected val mBinding get() = _binding!!

    private lateinit var mLoading: LoadingPopupView

    protected abstract val mViewModel: VM

    protected lateinit var owner: LifecycleOwner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        owner = this
        _binding = BindingReflex.reflexViewBinding(javaClass, layoutInflater)
        return _binding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme);
        initArguments()

        mViewModel.loadState.observe(this, observer)
        mLoading = XPopup.Builder(context).asLoading("加载中...", R.layout.loading_layout, LoadingPopupView.Style.Spinner)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initData()
    }

    open fun initArguments() {}

    abstract fun initView()

    abstract fun initData()

    protected fun showLoading(loading: String = "加载中...") {
        mLoading.setTitle(loading)
        mLoading.show()
    }

    protected fun hideLoading() {
        mLoading.dismiss()
    }

    private val observer by lazy {
        Observer<UIState> {
            it?.let {
                when (it.stateType) {
                    UIStateType.SUCCESS -> {
                        hideLoading()
                        LogCat.e("接口请求", "success...")
                    }

                    UIStateType.LOADING -> {
                        LogCat.e("接口请求", "loading...")
                    }

                    UIStateType.EMPTY, UIStateType.ERROR, UIStateType.NETWORK_ERROR -> {
                        hideLoading()
                        if (requestFailed(it.key)) {
                            it.msg?.let { msg -> toast(msg) }
                        }
                    }
                }
            }
        }
    }

    open fun requestFailed(key: String?): Boolean {
        return true
    }

}