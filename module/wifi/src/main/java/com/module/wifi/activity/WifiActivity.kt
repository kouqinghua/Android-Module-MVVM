package com.module.wifi.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import android.net.wifi.WifiNetworkSuggestion
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.lib.base.mvvm.v.BaseActivity
import com.lib.base.mvvm.vm.BaseViewModel
import com.lib.base.utils.console
import com.lib.common.router.RoutePath
import com.module.wifi.R
import com.module.wifi.adapter.WifiListAdapter
import com.module.wifi.bean.WifiEntity
import com.module.wifi.databinding.ActivityWifiBinding
import com.module.wifi.databinding.WifiInputPasswordBinding


@Route(path = RoutePath.Wifi.ACTIVITY_WIFI)
class WifiActivity : BaseActivity<ActivityWifiBinding, BaseViewModel>() {


    private var wifiManager: WifiManager? = null
    private var connectivityManager: ConnectivityManager? = null

    private var wifiPasswordDialog: AlertDialog? = null

    private var requestPermissionName: String = Manifest.permission.ACCESS_FINE_LOCATION


    private val wifiList = arrayListOf<WifiEntity>()
    private val mWifiAdapter by lazy {
        WifiListAdapter()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private val requestSinglePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted: Boolean ->
        if (granted) {
            if (wifiManager?.isWifiEnabled == true) {
                wifiManager?.startScan()
            }
        } else {
            //未同意授权
            if (!shouldShowRequestPermissionRationale(requestPermissionName)) {
                //用户拒绝权限并且系统不再弹出请求权限的弹窗
                //这时需要我们自己处理，比如自定义弹窗告知用户为何必须要申请这个权限
            }
        }
    }

    override val mViewModel by viewModels<BaseViewModel>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initialize() {
        setBarTitle("module:wifi")

        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

        // 注册广播
        registerReceiver(scanResultReceiver, IntentFilter().apply {
            addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        })

        registerReceiver(connectResultReceiver, IntentFilter().apply {
            addAction(WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION)
        })

        // 检测定位权限
        if (ActivityCompat.checkSelfPermission(this, requestPermissionName) == PackageManager.PERMISSION_GRANTED) {
            if (wifiManager?.isWifiEnabled == true) {
                wifiManager?.startScan()
            }
        } else {
            requestSinglePermissionLauncher.launch(requestPermissionName)
        }
    }

    override fun ActivityWifiBinding.initView() {
        mWifiList.run {
            layoutManager = LinearLayoutManager(this@WifiActivity)
            adapter = mWifiAdapter
        }

        mWifiAdapter.setOnItemClickListener { position, _ ->
            console(wifiList[position].capabilities)
            if (wifiList[position].capabilities.contains("WPA") || wifiList[position].capabilities.contains("WEP")) {
                showInputWIFIPasswordDialog(wifiList[position])
            }
        }
    }


    override fun initObserve() {

    }

    override fun initRequestData() {

    }

    private fun showInputWIFIPasswordDialog(wifiEntity: WifiEntity) {
        val wifiPwdBinding = WifiInputPasswordBinding.inflate(layoutInflater)
        wifiPasswordDialog = AlertDialog.Builder(this)
            .setTitle(wifiEntity.wifiSSID)
            .setView(wifiPwdBinding.root)
            .setPositiveButton("ok") { dialog, _ ->
                wifiPwdBinding.wifiPassword.text?.toString()?.let {
                    connectSelectedWIFIByPassword(wifiEntity, it)
                }
                dialog?.dismiss()
            }
            .setNegativeButton("cancel") { dialog, which ->
                dialog?.dismiss()
            }
            .create()
        wifiPasswordDialog?.setOnDismissListener {
            wifiPwdBinding.wifiPassword.text?.clear()
        }
        wifiPasswordDialog?.show()
    }

    private fun connectSelectedWIFIByPassword(wifiEntity: WifiEntity, pwd: String) {
        when {
            wifiEntity.capabilities.contains("wpa", true) -> {
                // 加密类型为WPA、WPA2P、WPA3
                connectWIFIWithWPAPassword(wifiEntity, pwd)
            }
            wifiEntity.capabilities.contains("wep", true) -> {
                // 加密类型为WEP，已过时
                connectWIFIWithWEPPassword(wifiEntity, pwd)
            }
        }
    }

    private fun connectWIFIWithWPAPassword(wifiInfo: WifiEntity, password: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val suggestionBuilder = WifiNetworkSuggestion.Builder()
                .setSsid(wifiInfo.wifiSSID)
                .setBssid(MacAddress.fromString(wifiInfo.wifiBSSID))
            if (wifiInfo.capabilities.contains("wpa3", true)) {
                suggestionBuilder.setWpa3Passphrase(password)
            } else {
                suggestionBuilder.setWpa2Passphrase(password)
            }


            val wifiConnectStatus = wifiManager?.addNetworkSuggestions(listOf(suggestionBuilder.build()))
            console("查看wifi连接状态$wifiConnectStatus")
            if (wifiConnectStatus == WifiManager.STATUS_NETWORK_SUGGESTIONS_SUCCESS) {
                val wifiNetworkSpecifier = WifiNetworkSpecifier.Builder()
                    .setSsid(wifiInfo.wifiSSID)
                    .setWpa2Passphrase(password)
                    .build()
                val request = NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .setNetworkSpecifier(wifiNetworkSpecifier)
                    .build()
                val mNetworkCallback = object : NetworkCallback() {
                    override fun onAvailable(@NonNull network: Network) {
                        super.onAvailable(network)
                        console(network)
                    }

                    override fun onUnavailable() {
                        super.onUnavailable()
                    }
                }
                // step3-连接wifi
                connectivityManager?.requestNetwork(request, mNetworkCallback)
            }
        } else {
            val wifiConfig = WifiConfiguration()
            wifiConfig.SSID = "\"${wifiInfo.wifiSSID}\""
            wifiConfig.preSharedKey = "\"$password\""
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA)
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN)
            wifiManager?.run {
                enableNetwork(addNetwork(wifiConfig), true)
            }
        }
    }

    private fun connectWIFIWithWEPPassword(wifiInfo: WifiEntity, password: String) {
        val wifiConfig = WifiConfiguration()
        wifiConfig.SSID = "\"${wifiInfo.wifiSSID}\""
        wifiConfig.wepKeys[0] = "\"$password\""
        wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
        wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
        wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED)
        wifiManager?.run {
            enableNetwork(addNetwork(wifiConfig), true)
        }
    }

    private val scanResultReceiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.R)
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false) == true) {
                wifiList.clear()
                // 扫描完成
                wifiManager?.scanResults?.forEach {
                    val ssid = it.SSID
                    val bssid = it.BSSID
                    // 获取WIFI加密类型
                    val capabilities = it.capabilities
                    // 获取WIFI信号强度
                    val level = wifiManager?.calculateSignalLevel(it.level) ?: 0
                    wifiList.add(WifiEntity(ssid, bssid, capabilities.contains("wpa", true) || capabilities.contains("wep", true), capabilities, level))
                }
                // 根据信号强度降序排列
                wifiList.sortByDescending { it.wifiStrength }
                mWifiAdapter.setData(wifiList)
            }
        }
    }

    private val connectResultReceiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.R)
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context?, intent: Intent?) {
            if (!intent?.action.equals(WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION)) {
                return;
            }

            val tmpInfo = intent!!.extras!![ConnectivityManager.EXTRA_NETWORK_INFO] as NetworkInfo?
            console(tmpInfo)
        }
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            connectivityManager?.run {
                if (boundNetworkForProcess != network) {
                    if (boundNetworkForProcess != network) {
                        bindProcessToNetwork(network)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onDestroy() {
        super.onDestroy()
        // 移除广播
        unregisterReceiver(scanResultReceiver)
        connectivityManager?.run {
            bindProcessToNetwork(null)
            try {
                unregisterNetworkCallback(networkCallback)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}