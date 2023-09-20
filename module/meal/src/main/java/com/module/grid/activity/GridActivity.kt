package com.module.grid.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.lib.base.app.BaseBindAdapter
import com.lib.base.mvvm.v.BaseActivity
import com.lib.base.mvvm.vm.BaseViewModel
import com.lib.base.utils.GridDecoration
import com.lib.common.router.RoutePath
import com.module.grid.R
import com.module.grid.adapter.RoomAdapter
import com.module.grid.databinding.ActivityGridBinding
import com.module.grid.viewmodel.MealViewModel
import org.koin.android.ext.android.get


@Route(path = RoutePath.Grid.ACTIVITY_GRID)
class GridActivity : BaseActivity<ActivityGridBinding, MealViewModel>(), BaseBindAdapter.OnItemClickListener {


    override val mViewModel: MealViewModel = get()

    override fun ActivityGridBinding.initView() {
        mRooms.addItemDecoration(GridDecoration(5, 3))
        mRooms.layoutManager = GridLayoutManager(mmContext, 3)

        val adapter = RoomAdapter()
        adapter.setData(mViewModel.getRooms())
        adapter.setOnItemClickListener(this@GridActivity)
        mRooms.adapter = adapter
    }

    override fun initialize() {
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }

    override fun onItemClick(position: Int) {

    }
}