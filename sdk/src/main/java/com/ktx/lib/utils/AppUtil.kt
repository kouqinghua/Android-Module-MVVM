package com.ktx.lib.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

class AppUtil {

    companion object{
        fun isMobile(phone: String?): Boolean {
            phone?.let {
                var p: Pattern? = null
                var m: Matcher? = null
                var isMobile = false
                p = Pattern.compile("^[1][2,3,4,5,7,6,8,9][0-9]{9}$") // 验证手机号
                m = p.matcher(it)
                isMobile = m.matches()
                return if (!isMobile) {
                    false
                } else { //手机号位数正确性判断
                    val length = phone.length
                    length == 11
                }
            }
            return false
        }
    }
}