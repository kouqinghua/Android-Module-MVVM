package com.module.main.activity

import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.lib.base.app.BaseBindAdapter
import com.lib.base.mvvm.v.BaseActivity
import com.lib.base.utils.GridDecoration
import com.lib.common.router.RoutePath
import com.lib.common.router.service.camera.CameraServiceWrap
import com.lib.common.router.service.login.LoginServiceWrap
import com.module.main.R
import com.module.main.adapter.RoomAdapter
import com.module.main.databinding.MainActivityBinding
import com.module.main.viewmodel.MainViewModel
import org.koin.android.ext.android.get

@Route(path = RoutePath.Main.ACTIVITY_MAIN)
class MainActivity : BaseActivity<MainActivityBinding, MainViewModel>(), BaseBindAdapter.OnItemClickListener {

    override val mViewModel: MainViewModel = get()


    override fun MainActivityBinding.initView() {
        mRooms.addItemDecoration(GridDecoration(5, 3))
        mRooms.layoutManager = GridLayoutManager(mmContext, 3)

        val adapter = RoomAdapter(R.layout.room_item_layout)
        adapter.setData(mViewModel.getRooms())
        adapter.setOnItemClickListener(this@MainActivity)
        mRooms.adapter = adapter
    }

    override fun initialize() {

    }

    override fun initObserve() {

    }

    override fun initRequestData() {

    }

    override fun onItemClick(position: Int) {
        CameraServiceWrap.instance.toCameraActivity(this)
//        LoginServiceWrap.instance.toLoginActivity(this)
    }
}