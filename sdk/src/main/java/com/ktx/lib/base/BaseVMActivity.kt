package com.ktx.lib.base

import com.google.gson.Gson
import com.ktx.lib.Constant
import com.ktx.lib.sdk.BaseActivity
import com.ktx.lib.utils.Mem


abstract class BaseVMActivity : BaseActivity() {

    protected val mGson by lazy {
        Gson()
    }

    override fun initialize() {
        initView()
        observer()
        initData()
    }

    open fun initState(vm: BaseViewModel) {
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
//                    hideProgress()
                }
            }
        }
    }

    abstract fun initView()

    abstract fun observer()

    abstract fun initData()


    open fun loading(str: String = "加载中") {
        showProgress(str, 0)
    }

    open fun complete(str: String = "加载完成") {
        alert(str)
    }

    open fun success(str: String = "加载成功") {
        showProgress(str, 1)
    }

    open fun empty(str: String = "暂无数据") {
        alert(str)
    }

    open fun error(str: String = "加载失败") {
        showProgress(str, 2)
    }

    open fun netError(str: String = "网络异常") {
        alert(str)
    }


    //---------------------------------------------------------------------------------


    protected fun hasToken(): Boolean {
        return !Mem.string(Constant.TOKEN).isNullOrEmpty()
    }

    protected fun token(): String {
        return Mem.string(Constant.TOKEN)!!
    }

    protected fun <O> gson(res: String, c: Class<O>): O {
        return mGson.fromJson(res, c)
    }

    protected fun isVip(): Boolean {
        return Mem.int(Constant.LEVEL) > 1
    }
}