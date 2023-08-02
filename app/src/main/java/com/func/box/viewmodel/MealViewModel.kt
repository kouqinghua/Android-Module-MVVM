package com.func.box.viewmodel


import androidx.lifecycle.MutableLiveData
import com.func.box.bean.Menu
import com.func.box.bean.MenuItem
import com.lib.base.mvvm.vm.BaseViewModel

class MealViewModel : BaseViewModel() {

    var menus: MutableLiveData<List<Menu>> = MutableLiveData()

    fun getMenu() {

        val data = mutableListOf(
            Menu("店长推荐", listOf(MenuItem("炭火烤鱼", 48), MenuItem("孜然羊肉", 59), MenuItem("红烧鲈鱼", 36)), true),
            Menu("家常菜", listOf(MenuItem("尖椒腐竹", 16), MenuItem("麻婆豆腐", 18), MenuItem("干炸扁豆", 24), MenuItem("芹菜肉丝", 22)), false),
            Menu("特色炒菜", listOf(MenuItem("辣子炒鸡", 78)), false),
            Menu("夏季烧烤", listOf(MenuItem("羊肉串(大)", 66), MenuItem("翅中", 4), MenuItem("小红腰6", 16)), false),
            Menu("下酒菜", listOf(MenuItem("麻辣小龙虾", 88), MenuItem("炒田螺", 12)), false),
            Menu("开胃菜", listOf(MenuItem("辣椒炒肉", 20), MenuItem("麻辣香锅", 32)), false),
            Menu("熟食", listOf(MenuItem("辣炒猪脸", 24), MenuItem("肚包肉", 48)), false),
            Menu("凉菜", listOf(MenuItem("凉拌黄瓜", 12), MenuItem("老醋花生", 16)), false),
            Menu("酒水", listOf(MenuItem("青岛啤酒", 8), MenuItem("崂山啤酒", 6), MenuItem("雪花啤酒", 6)), false),
        )

        menus.value = data
    }


}