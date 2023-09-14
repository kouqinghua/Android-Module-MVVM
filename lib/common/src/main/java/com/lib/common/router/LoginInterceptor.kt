package com.lib.common.router

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.lib.base.network.log.LogCat

/**
 * 声明拦截器(拦截跳转过程，面向切面编程)
 * 比较经典的应用就是在跳转过程中处理登陆事件，这样就不需要在目标页重复做登陆检查
 * 拦截器会在跳转之间执行，多个拦截器会按优先级顺序依次执行
 */
@Interceptor(name = "login", priority = 1)
class LoginInterceptor : IInterceptor {

    companion object {
        const val TAG = "LoginInterceptor"
    }

    override fun init(context: Context?) {
        LogCat.e("路由登录拦截器初始化成功", TAG)
    }

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        LogCat.e("进拦截器了....", TAG)
        val path = postcard.path
        val isLogin = true
        if (isLogin) {
            LogCat.e("已经登录了", TAG)
            callback.onContinue(postcard)
        } else {
            when (path) {
                RoutePath.Main.ACTIVITY_MAIN -> {
                    LogCat.e("未登录", TAG)
                    callback.onInterrupt(null)
                }

                else -> {
                    LogCat.e("不需要登录", TAG)
                    callback.onContinue(postcard)
                }
            }
        }
    }
}