package com.module.progress.activity

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateInterpolator
import com.alibaba.android.arouter.facade.annotation.Route
import com.lib.base.mvvm.v.BaseActivity
import com.lib.common.router.RoutePath
import com.module.progress.R
import com.module.progress.databinding.ActivityProgressBinding
import com.module.progress.viewmodel.ProgressViewModel
import com.xuexiang.xui.utils.DensityUtils
import org.koin.android.ext.android.get


@Route(path = RoutePath.Progress.ACTIVITY_PROGRESS)
class ProgressActivity : BaseActivity<ActivityProgressBinding, ProgressViewModel>() {


    override val mViewModel: ProgressViewModel = get()

    override fun initialize() {

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun ActivityProgressBinding.initView() {
        mProgressBar.apply {
            progressDrawable = getDrawable(R.drawable.progress_drawable)
            indeterminateDrawable = getDrawable(android.R.drawable.progress_indeterminate_horizontal)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startAnim()
        }, 1000)
    }

    override fun initObserve() {

    }

    override fun initRequestData() {

    }

    private fun startAnim() {
        val animator = ValueAnimator.ofInt(0, 95)
        animator.duration = 2500
        animator.interpolator = AccelerateInterpolator()
        animator.addUpdateListener { valueAnimator ->
            mBinding.mProgressBar.progress = valueAnimator.animatedValue as Int
            mBinding.mProgressBar.invalidate()
        }
        animator.start()
    }
}