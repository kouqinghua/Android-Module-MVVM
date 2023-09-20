package com.module.grid.activity

import androidx.appcompat.app.AppCompatActivity

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