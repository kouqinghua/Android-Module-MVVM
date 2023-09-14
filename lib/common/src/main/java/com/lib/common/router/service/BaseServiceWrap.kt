package com.lib.common.router.service

import com.alibaba.android.arouter.launcher.ARouter

open class BaseServiceWrap {
    init {
        ARouter.getInstance().inject(this)
    }

//    /**
//     * 根据Schema Link 跳转页面
//     */
//    fun openSchemeRouteActivity(url: String) {
//        val params = UrlParser.getQueryParams(url)
//        LogCat.e(params)
//        when (params["page"]) {
//            RoutePath.Login.ACTIVITY_LOGIN -> {
//                LoginServiceWrap.instance.toLoginActivity()
//            }
//
//            RoutePath.Course.ACTIVITY_COURSE_DETAIL -> {
//                val courseId: Long = params["id"]?.toLong() ?: 0
//                if (courseId > 0) {
//                    if (TokenUtils.getToken() == null) {
//                        LoginServiceWrap.instance.toLoginActivity()
//                        return
//                    }
//                    CourseServiceWrap.instance.toCourseDetailActivity(courseId)
//                }
//            }
//
//            RoutePath.Course.ACTIVITY_COURSE_LIST -> {
//                val motionType = params["motionType"]
//                if (motionType != null) {
//                    CourseServiceWrap.instance.toCourseListActivity(motionType)
//                }
//            }
//
//            RoutePath.WebView.ACTIVITY_WEB_VIEW -> {
//                val url = params["url"]
//                if (url != null) {
//                    WebViewServiceWrap.instance.toWebViewActivity(url)
//                }
//            }
//        }
//    }
//
//    private fun skipOtherAppActivity(content: Context, packageName: String, activityClass: String) {
//        var intent = Intent()
//        val component = ComponentName(packageName, activityClass)
//        intent.component = component
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        content.startActivity(intent)
//    }

}