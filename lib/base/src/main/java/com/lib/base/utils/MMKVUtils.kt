package com.lib.base.utils

import android.content.Context
import android.os.Parcelable
import com.tencent.mmkv.MMKV

/**
 * MMKV 工具类
 */
object MMKVUtils {

    private var mmkv: MMKV? = null

    /**
     * 初始化
     */
    fun init(context: Context) {
        MMKV.initialize(context.applicationContext)
        mmkv = MMKV.defaultMMKV()
    }

    private fun getMMKV(): MMKV? {
        if (mmkv == null) {
            mmkv = MMKV.defaultMMKV()
        }
        return mmkv
    }

    //=======================================键值保存==================================================//
    /**
     * 保存键值
     *
     * @param key
     * @param value
     * @return
     */
    fun put(key: String?, value: Any?): Boolean {
        when (value) {
            is Int -> {
                return getMMKV()!!.encode(key, (value as Int?)!!)
            }

            is Float -> {
                return getMMKV()!!.encode(key, (value as Float?)!!)
            }

            is String -> {
                return getMMKV()!!.encode(key, value as String?)
            }

            is Boolean -> {
                return getMMKV()!!.encode(key, (value as Boolean?)!!)
            }

            is Long -> {
                return getMMKV()!!.encode(key, (value as Long?)!!)
            }

            is Double -> {
                return getMMKV()!!.encode(key, (value as Double?)!!)
            }

            is Parcelable -> {
                return getMMKV()!!.encode(key, value as Parcelable?)
            }

            is ByteArray -> {
                return getMMKV()!!.encode(key, value as ByteArray?)
            }

            is Set<*> -> {
                return getMMKV()!!.encode(key, value as Set<String?>?)
            }

            else -> return false
        }
    }


    //=======================================键值获取==================================================//

    //=======================================键值获取==================================================//
    /**
     * 获取键值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    operator fun get(key: String?, defaultValue: Any?): Any? {
        when (defaultValue) {
            is Int -> {
                return getMMKV()!!.decodeInt(key, (defaultValue as Int?)!!)
            }

            is Float -> {
                return getMMKV()!!.decodeFloat(key, (defaultValue as Float?)!!)
            }

            is String -> {
                return getMMKV()!!.decodeString(key, defaultValue as String?)
            }

            is Boolean -> {
                return getMMKV()!!.decodeBool(key, (defaultValue as Boolean?)!!)
            }

            is Long -> {
                return getMMKV()!!.decodeLong(key, (defaultValue as Long?)!!)
            }

            is Double -> {
                return getMMKV()!!.decodeDouble(key, (defaultValue as Double?)!!)
            }

            is ByteArray -> {
                return getMMKV()!!.decodeBytes(key)
            }

            is Set<*> -> {
                return getMMKV()!!.decodeStringSet(key, defaultValue as Set<String?>?)
            }

            else -> return null
        }
    }


    /**
     * 根据key获取boolean值
     *
     * @param key
     * @param defValue
     * @return
     */
    fun getBoolean(key: String?, defValue: Boolean): Boolean {
        try {
            return getMMKV()!!.getBoolean(key, defValue)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defValue
    }

    /**
     * 根据key获取long值
     *
     * @param key
     * @param defValue
     * @return
     */
    fun getLong(key: String?, defValue: Long): Long {
        try {
            return getMMKV()!!.getLong(key, defValue)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defValue
    }

    /**
     * 根据key获取float值
     *
     * @param key
     * @param defValue
     * @return
     */
    fun getFloat(key: String?, defValue: Float): Float {
        try {
            return getMMKV()!!.getFloat(key, defValue)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defValue
    }

    /**
     * 根据key获取String值
     *
     * @param key
     * @param defValue
     * @return
     */
    fun getString(key: String?, defValue: String?): String? {
        try {
            return getMMKV()!!.getString(key, defValue)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defValue
    }

    /**
     * 根据key获取int值
     *
     * @param key
     * @param defValue
     * @return
     */
    fun getInt(key: String?, defValue: Int): Int {
        try {
            return getMMKV()!!.getInt(key, defValue)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defValue
    }


    /**
     * 根据key获取double值
     *
     * @param key
     * @param defValue
     * @return
     */
    fun getDouble(key: String?, defValue: Double): Double {
        try {
            return getMMKV()!!.decodeDouble(key, defValue)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defValue
    }


    /**
     * 获取对象
     *
     * @param key
     * @param tClass 类型
     * @param <T>
     * @return
    </T> */
    fun <T : Parcelable?> getObject(key: String?, tClass: Class<T>?): T? {
        return getMMKV()!!.decodeParcelable(key, tClass)
    }

    /**
     * 获取对象
     *
     * @param key
     * @param tClass 类型
     * @param <T>
     * @return
    </T> */
    fun <T : Parcelable?> getObject(key: String?, tClass: Class<T>?, defValue: T): T? {
        try {
            return getMMKV()!!.decodeParcelable(key, tClass, defValue)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defValue
    }


    /**
     * 判断键值对是否存在
     *
     * @param key 键
     * @return 键值对是否存在
     */
    fun containsKey(key: String?): Boolean {
        return getMMKV()!!.containsKey(key)
    }

    /**
     * 清除指定键值对
     *
     * @param key 键
     */
    fun remove(key: String?) {
        getMMKV()!!.remove(key).apply()
    }
}