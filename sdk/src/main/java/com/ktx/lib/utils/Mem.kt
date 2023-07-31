package com.ktx.lib.utils

import com.tencent.mmkv.MMKV

class Mem {

    companion object{
        fun string(key: String): String? {
            return MMKV.defaultMMKV().decodeString(key)
        }

        fun string(key: String, value: String) {
            MMKV.defaultMMKV().encode(key, value)
        }

        fun int(key: String): Int {
            return MMKV.defaultMMKV().decodeInt(key, -1)
        }

        fun int(key: String, value: Int) {
            MMKV.defaultMMKV().encode(key, value)
        }

        fun bool(key: String): Boolean {
            return MMKV.defaultMMKV().decodeBool(key, false)
        }

        fun bool(key: String, value: Boolean) {
            MMKV.defaultMMKV().encode(key, value)
        }

        fun double(key: String): Double {
            return MMKV.defaultMMKV().decodeDouble(key, 0.0)
        }

        fun double(key: String, value: Double) {
            MMKV.defaultMMKV().encode(key, value)
        }

        fun float(key: String): Float {
            return MMKV.defaultMMKV().decodeFloat(key, 0.0F)
        }

        fun float(key: String, value: Float) {
            MMKV.defaultMMKV().encode(key, value)
        }

        fun long(key: String): Long {
            return MMKV.defaultMMKV().decodeLong(key, 0L)
        }

        fun long(key: String, value: Long) {
            MMKV.defaultMMKV().encode(key, value)
        }
    }
}
