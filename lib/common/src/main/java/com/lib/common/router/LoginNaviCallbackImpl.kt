package com.lib.common.router

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.lib.base.network.log.LogCat

/**
 * 处理跳转结果
 * 使用两个参数的navigation方法，可以获取单次跳转的结果
 */
class LoginNaviCallbackImpl: NavigationCallback {

    override fun onFound(postcard: Postcard) {
        LogCat.e("LoginNaviCallbackImpl","找到了")
    }

    override fun onLost(postcard: Postcard) {
        LogCat.e("LoginNaviCallbackImpl","找不到了")
    }

    override fun onArrival(postcard: Postcard) {
        LogCat.e("LoginNaviCallbackImpl","跳转成功了")
    }

    override fun onInterrupt(postcard: Postcard) {
        LogCat.e("LoginNaviCallbackImpl","onInterrupt")
        val path = postcard.path
        val bundle = postcard.extras
        ARouter.getInstance()
            .build(RoutePath.Login.ACTIVITY_LOGIN)
            .with(bundle)
            .withString(RoutePath.PATH, path)
            .navigation()
    }
}