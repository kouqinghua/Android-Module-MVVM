package com.lib.base.mvvm.v

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lib.base.R
import com.lib.base.mvvm.vm.BaseViewModel
import com.lib.base.network.UIState
import com.lib.base.network.UIStateType
import com.lib.base.network.log.LogCat
import com.lib.base.utils.BindingReflex
import com.lib.base.utils.toast
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

abstract class BaseDialogFragment<VM : BaseViewModel, VB : ViewDataBinding> : DialogFragment() {

    /**
     * 私有的 ViewBinding 此写法来自 Google Android 官方
     */
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

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val dm = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getMetrics(dm)
            dialog.window?.setLayout((dm.widthPixels * 0.95).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArguments()

        mViewModel.loadState.observe(this, observer)
        mLoading = XPopup.Builder(context).asLoading("加载中...", R.layout.loading_layout, LoadingPopupView.Style.Spinner)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    abstract fun initView()

    open fun initArguments() {}

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