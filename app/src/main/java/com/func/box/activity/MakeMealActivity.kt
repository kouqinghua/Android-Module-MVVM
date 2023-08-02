package com.func.box.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.func.box.MEAL_LIST
import com.func.box.R
import com.func.box.adapter.MenuListAdapter
import com.func.box.adapter.MenuTypeAdapter
import com.func.box.bean.MenuItem
import com.func.box.databinding.MakeMealLayoutBinding
import com.func.box.viewmodel.MealViewModel
import com.ktx.lib.base.BaseVMActivity
import com.ktx.lib.ext.*
import org.koin.android.ext.android.get

/**
 * 点餐页面
 */
class MakeMealActivity : AppCompatActivity() {

//    private val mBinding: MakeMealLayoutBinding by binding()
//    private val mViewModel: MealViewModel = get()
//    private lateinit var manager: LinearLayoutManager
//
//    private val mMenuTypeAdapter = MenuTypeAdapter(R.layout.item_menu_type_layout)
//    private val mMenuListAdapter = MenuListAdapter(R.layout.item_menu_list_layout)
//
//    override fun initView() {
//        mBinding.run {
//            mMenuTypes.adapter = mMenuTypeAdapter
//            mMenuList.adapter = mMenuListAdapter
//            manager = mMenuList.layoutManager as LinearLayoutManager
//
//            mMenuTypeAdapter.setOnItemClick {
//                mMenuTypeAdapter.setCheck(it)
//                manager.scrollToPositionWithOffset(it, 0)
//            }
//
//            mMenuList.apply {
//                adapter = mMenuListAdapter
//
//                linkage(manager, mMenuTypes) {
//                    mMenuTypeAdapter.setCheck(it)
//                }
//            }
//
//            onClick(mCreateOrder) {
//                val doneList = arrayListOf<MenuItem>()
//                mViewModel.menus.value?.forEach {
//                    it.list.forEach { item ->
//                        if (item.count > 0) {
//                            doneList.add(item)
//                        }
//                    }
//                }
//                send(MEAL_LIST, doneList)
//                startFromBottom(MealDetailActivity::class.java)
//            }
//        }
//    }
//
//    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
//    override fun observer() {
//        obtain("ROOM_NUM") {
//            setBarTitle("${it?.value!!}点餐")
//        }
//
//        mViewModel.menus.observe(this) {
//            mMenuTypeAdapter.setData(it)
//            mMenuListAdapter.setData(it)
//            var total = 0
//            it.forEach { menuList ->
//                menuList.list.forEach { item ->
//                    if (item.count >= 0) {
//                        total += (item.count * item.price)
//                    }
//                }
//            }
//
//            mBinding.mTotalPrice.text = "合计: ${total}元"
//
//            mMenuListAdapter.apply {
//                setChildClick { parent, child ->
//                    it[parent].list[child].count++
//                    mViewModel.menus.value = it
//                    mMenuListAdapter.notifyDataSetChanged()
//                }
//
//                setItemAdd { parent, child ->
//                    it[parent].list[child].count++
//                    mViewModel.menus.value = it
//                    mMenuListAdapter.notifyDataSetChanged()
//                }
//
//                setItemMinus { parent, child ->
//                    if (it[parent].list[child].count == 0) {
//                        return@setItemMinus
//                    }
//
//                    it[parent].list[child].count--
//                    mViewModel.menus.value = it
//                    mMenuListAdapter.notifyDataSetChanged()
//                }
//            }
//        }
//    }
//
//    override fun initData() {
//        mViewModel.getMenu()
//    }
}