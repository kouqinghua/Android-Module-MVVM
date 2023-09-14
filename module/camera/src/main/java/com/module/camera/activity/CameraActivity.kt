package com.module.camera.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.UseCaseGroup
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.google.common.util.concurrent.ListenableFuture
import com.lib.base.mvvm.v.BaseActivity
import com.lib.base.utils.console
import com.lib.common.router.RoutePath
import com.module.camera.databinding.ActivityCameraBinding
import com.module.camera.vm.CameraViewModel
import org.koin.android.ext.android.get
import java.io.File
import java.util.Timer
import java.util.TimerTask


@Route(path = RoutePath.Camera.ACTIVITY_CAMERA)
class CameraActivity : BaseActivity<ActivityCameraBinding, CameraViewModel>() {


    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    private lateinit var savePath: File
    private lateinit var outputFileOption: ImageCapture.OutputFileOptions


    override val mViewModel: CameraViewModel = get()

    override fun initialize() {
        checkPermission()
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }

    override fun ActivityCameraBinding.initView() {
        mTakePicture.setOnClickListener {
            Handler(Looper.getMainLooper()).postDelayed({
                console("开始拍照了")
                takePicture()
            }, 2000)
        }
    }

    private fun takePicture() {
        savePath = getFile()

        val metadata = ImageCapture.Metadata()
        outputFileOption = ImageCapture.OutputFileOptions.Builder(savePath).setMetadata(metadata).build()
        imageCapture.takePicture(outputFileOption, ContextCompat.getMainExecutor(this@CameraActivity), object : ImageCapture.OnImageSavedCallback {
            override fun onError(error: ImageCaptureException) {
                console(error)
            }

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                console("拍照保存路径：${outputFileResults.savedUri}")
                Glide.with(this@CameraActivity).load(outputFileResults.savedUri).into(mBinding.mImage)
            }
        })
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1002)
        } else {
            initCamera()
        }
    }

    private fun initCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview: Preview = Preview.Builder().build()

        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        imageCapture = ImageCapture.Builder().setTargetRotation(mBinding.mPreviewView.display.rotation).build()

        preview.setSurfaceProvider(mBinding.mPreviewView.surfaceProvider)

        val viewPort = mBinding.mPreviewView.viewPort

        val useCaseGroup = UseCaseGroup.Builder()
            .addUseCase(preview)
            .addUseCase(imageCapture)
            .setViewPort(viewPort!!)
            .build()


        cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, useCaseGroup)
    }


    private fun getFile(): File {
        // 使用缓存目录,这个时候不需要申请存储权限
        // 目录不存在，那么创建
        val dir = File(externalCacheDir, "images")
        if (!dir.exists()) {
            dir.mkdir()
        }
        // 创建文件
        return File(dir, "/${System.currentTimeMillis()}.jpg")
    }
}