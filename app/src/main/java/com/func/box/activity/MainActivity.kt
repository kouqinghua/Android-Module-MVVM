package com.func.box.activity

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.ACTION_FOUND
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.func.box.R
import com.func.box.adapter.RoomAdapter
import com.func.box.bluetooth.BlueToothManager
import com.func.box.databinding.ActivityMainBinding
import com.func.box.vm.MainViewModel
import com.ktx.lib.base.BaseBindAdapter
import com.ktx.lib.component.ItemDecoration
import com.lib.common.base.BaseVMActivity
import com.lib.common.ext.binding
import org.koin.android.ext.android.get

@RequiresApi(Build.VERSION_CODES.M)
class MainActivity : BaseVMActivity(), BaseBindAdapter.OnItemClickListener {

    private val mBinding: ActivityMainBinding by binding()
    private val mViewModel: MainViewModel = get()

    override fun initView() {
        hideLeftIcon()
        setBarTitle("选择房间/桌号点餐")
        initState(mViewModel)
        mBinding.mRooms.addItemDecoration(ItemDecoration(5, 3))
        mBinding.mRooms.layoutManager = GridLayoutManager(this, 3)
    }

    override fun observer() {
        mViewModel.apply {
            banners.observe(this@MainActivity) {
                Log.e("获取到的banner数据：", it[0].desc)
            }
        }
    }

    override fun initData() {
        val adapter = RoomAdapter(R.layout.item_room_layout)
        adapter.setData(mViewModel.getRooms())
        adapter.setOnItemClickListener(this)
        mBinding.mRooms.adapter = adapter
//        mViewModel.changeState(LoadState.HIDE)

        registerBluetoothReceiver()
    }

    override fun onItemClick(position: Int) {
        click()
    }

    private fun click() {
//        mViewModel.getBanner()
//        start(PagePath.login)
//        ARouter.getInstance().build(PagePath.login).navigation()
//        send("ROOM_NUM", mViewModel.getRooms()[position])
//        start(MakeMealActivity::class.java)
//        start(PersonCenterActivity::class.java)
//        alert("${mViewModel.getRooms()[position]}开始点餐")
        bluetooth()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun bluetooth() {
        val open = BlueToothManager.isOpen()
        Log.e("蓝牙状态：", open.toString())

        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT)
        } else {
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        applyPermission(permissions, 202)
    }

    override fun permissionSuccess(requestCode: Int) {
        BlueToothManager.openBlueSync(this, 200)

        BlueToothManager.blueScan()
    }

    // 注册蓝牙相关广播
    private fun registerBluetoothReceiver() {
        // 接收扫描结果的广播
        val ACTION_DISCOVERY_STARTED = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        val ACTION_DISCOVERY_FINISHED = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        val ACTION_FOUND = IntentFilter(ACTION_FOUND)

        val scanBlueReceiver = ScanBlueReceiver()

        registerReceiver(scanBlueReceiver, ACTION_DISCOVERY_STARTED)
        registerReceiver(scanBlueReceiver, ACTION_DISCOVERY_FINISHED)
        registerReceiver(scanBlueReceiver, ACTION_FOUND)
    }

    inner class ScanBlueReceiver : BroadcastReceiver() {

        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            Log.e("蓝牙相关操作--->action：", "action:$action")
            val device = intent?.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_NAME)
            when (action) {
                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                    Log.e("蓝牙相关操作--->扫描开始：", "action:$action")
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    Log.e("蓝牙相关操作--->扫描结束：", "action:$action")
                }
                ACTION_FOUND -> {
                    Log.e("蓝牙相关操作--->扫描中：", device.toString())
                    if (device.toString() == "18:65:90:CE:43:AC"){
                        BlueToothManager.stopScan()
                        device?.createBond()
                    }
                }
            }
        }
    }
}