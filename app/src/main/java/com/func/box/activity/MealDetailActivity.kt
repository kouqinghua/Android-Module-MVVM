package com.func.box.activity

import androidx.appcompat.app.AppCompatActivity
import com.func.box.MEAL_LIST
import com.func.box.R
import com.func.box.adapter.MealListAdapter
import com.func.box.bean.MenuItem
import com.func.box.databinding.MealDetailLayoutBinding
import com.ktx.lib.base.BaseVMActivity
import com.ktx.lib.ext.binding
import com.ktx.lib.ext.closeFromTop
import com.ktx.lib.ext.obtain
import com.ktx.lib.ext.onClick

class MealDetailActivity : AppCompatActivity() {

//    private val mBinding: MealDetailLayoutBinding by binding()
//
//    private val mAdapter = MealListAdapter(R.layout.item_meal_layout)
//
//    override fun initView() {
//        setBarTitle("点餐详情")
//
//        mBinding.run {
//            mMealList.adapter = mAdapter
//            onClick(mContinueMeal) {
//                closeFromTop()
//            }
//
//            onClick(mGenerateOrder) {
//                alert("下单成功")
//            }
//        }
//    }
//
//    override fun observer() {
//        obtain(MEAL_LIST) {
//            val data = it?.data as List<MenuItem>
//            mAdapter.setData(data)
//        }
//    }
//
//    override fun initData() {
//    }
//
//    override fun onBackPressed() {
//        closeFromTop()
//    }
}