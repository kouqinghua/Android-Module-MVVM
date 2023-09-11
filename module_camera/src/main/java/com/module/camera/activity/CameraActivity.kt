package com.module.camera.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.lib.base.mvvm.v.BaseActivity
import com.lib.common.router.RoutePath
import com.module.camera.databinding.CameraActivityLayoutBinding
import com.module.camera.vm.CameraViewModel
import org.koin.android.ext.android.get

@Route(path = RoutePath.Camera.ACTIVITY_CAMERA)
class CameraActivity : BaseActivity<CameraActivityLayoutBinding, CameraViewModel>() {


    override val mViewModel: CameraViewModel = get()

    override fun initialize() {
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }

    override fun CameraActivityLayoutBinding.initView() {
    }
}