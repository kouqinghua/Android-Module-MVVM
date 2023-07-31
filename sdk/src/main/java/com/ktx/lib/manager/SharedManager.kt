package com.ktx.lib.manager

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SharedManager private constructor(context: Context) {

    var mContext: Context = context.applicationContext;

    companion object {
        @Volatile
        @SuppressLint("StaticFieldLeak")
        var instance: SharedManager? = null

        fun getInstance(c: Context): SharedManager {
            if (instance == null) {
                synchronized(SharedManager::class) {
                    if (instance == null) {
                        instance = SharedManager(c)
                    }
                }
            }
            return instance!!
        }
    }

    /**
     * 存储字符串
     */
    fun putString(key: String?, value: String?) {
        val s: SharedPreferences = mContext.getSharedPreferences("config", Context.MODE_PRIVATE)
        s.edit().putString(key, value).apply()
    }

    /**
     * 获取字符串
     */
    fun getString(key: String?): String? {
        val s: SharedPreferences = mContext.getSharedPreferences("config", Context.MODE_PRIVATE)
        return s.getString(key, "")
    }

    /**
     * 存储Boolean
     */
    fun putBoolean(key: String?, b: Boolean?) {
        val s: SharedPreferences = mContext.getSharedPreferences("config", Context.MODE_PRIVATE)
        s.edit().putBoolean(key, b!!).apply()
    }

    /**
     * 获取Boolean
     */
    fun getBoolean(key: String?): Boolean {
        val s: SharedPreferences = mContext.getSharedPreferences("config", Context.MODE_PRIVATE)
        return s.getBoolean(key, false)
    }

    /**
     * 存储字符串
     */
    fun putInt(key: String?, value: Int) {
        val s: SharedPreferences = mContext.getSharedPreferences("config", Context.MODE_PRIVATE)
        s.edit().putInt(key, value).apply()
    }

    /**
     * 获取字符串
     */
    fun getInt(key: String?): Int {
        val s: SharedPreferences = mContext.getSharedPreferences("config", Context.MODE_PRIVATE)
        return s.getInt(key, 0)
    }
}