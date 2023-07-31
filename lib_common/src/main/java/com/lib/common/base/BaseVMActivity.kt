package com.lib.common.base

import androidx.core.view.isVisible
import com.google.gson.Gson

abstract class BaseVMActivity : BaseActivity() {

    protected val mGson by lazy {
        Gson()
    }

    override fun initialize() {
        initView()
        observer()
        initData()
    }

    open fun initState(vm: SdkViewModel) {
        vm.mLoadState.observe(this) {
            when (it!!) {
                LoadState.LOADING -> {
                    loading()
                }
                LoadState.SUCCESS -> {
                    success()
                }
                LoadState.COMPLETE -> {
                    complete()
                }
                LoadState.EMPTY -> {
                    empty()
                }
                LoadState.ERROR -> {
                    error()
                }
                LoadState.NET_ERROR -> {
                    netError()
                }
                LoadState.HIDE -> {
                    hideProgress()
                }
            }
        }
    }

    abstract fun initView()

    abstract fun observer()

    abstract fun initData()


    open fun loading(str: String = "加载中...") {
        mLoadingBinding.mLoadText.isVisible = true
        mLoadingBinding.mLoadText.text = str
        mLoading.show()
//        showProgress(str, 0)
    }

    open fun complete(str: String = "加载完成") {
//        alert(str)
    }

    open fun success(str: String = "加载成功") {
//        showProgress(str, 1)
    }

    open fun empty(str: String = "暂无数据") {
//        alert(str)
    }

    open fun error(str: String = "加载失败") {
//        showProgress(str, 2)
    }

    open fun netError(str: String = "网络异常") {
//        alert(str)
    }

    /**
     * 隐藏模态进度框
     */
    protected fun hideProgress() {
        mLoading.dismiss()
    }

    protected fun <O> gson(res: String, c: Class<O>): O {
        return mGson.fromJson(res, c)
    }
}