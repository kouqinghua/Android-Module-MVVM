package com.lib.common.router.service.main

import com.alibaba.android.arouter.facade.template.IProvider

interface IMainService: IProvider {

    fun getModuleName(): String
}