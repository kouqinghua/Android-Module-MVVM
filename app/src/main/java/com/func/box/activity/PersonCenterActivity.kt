package com.func.box.activity

import com.func.box.databinding.PersonCenterLayoutBinding
import com.ktx.lib.base.BaseVMActivity
import com.ktx.lib.ext.binding

class PersonCenterActivity : BaseVMActivity() {

    private val mBinding: PersonCenterLayoutBinding by binding()

    override fun initView() {
        mBinding.text.text = "员工签署"
    }

    override fun observer() {

    }

    override fun initData() {

    }
}