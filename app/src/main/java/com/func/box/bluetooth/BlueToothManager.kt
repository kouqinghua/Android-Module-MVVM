package com.func.box.bluetooth

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter

import android.content.Intent
import android.util.Log


object BlueToothManager {

    val mBluetoothAdapter: BluetoothAdapter? by lazy {
        BluetoothAdapter.getDefaultAdapter()
    }

    /**
     * 自动打开蓝牙（同步）
     * 这个方法打开蓝牙会弹出提示
     * 需要在onActivityResult 方法中判断resultCode == RESULT_OK  true为成功
     */
    @JvmStatic
    fun openBlueSync(activity: Activity, requestCode: Int) {
        val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        activity.startActivityForResult(intent, requestCode)
    }


    /**
     * 蓝牙是否打开   true为打开
     */
    @JvmStatic
    fun isOpen(): Boolean {
        return mBluetoothAdapter != null && mBluetoothAdapter!!.isEnabled
    }

    /**
     * 扫描的方法 返回true 扫描成功
     * 通过接收广播获取扫描到的设备
     */
    @SuppressLint("MissingPermission")
    fun blueScan(): Boolean {
        if (!isOpen()) {
            Log.e("蓝牙相关操作--->扫描：", "Bluetooth not enable!");
            return false
        }

        //当前是否在扫描，如果是就取消当前的扫描，重新扫描
        if (mBluetoothAdapter!!.isDiscovering) {
            mBluetoothAdapter!!.cancelDiscovery()
        }

        //此方法是个异步操作，一般搜索12秒
        return mBluetoothAdapter!!.startDiscovery()
    }


    /**
     * 取消扫描蓝牙
     */
    @SuppressLint("MissingPermission")
    fun stopScan(): Boolean {
        if (mBluetoothAdapter != null) {
            return mBluetoothAdapter!!.cancelDiscovery()
        }
        return true;
    }

}