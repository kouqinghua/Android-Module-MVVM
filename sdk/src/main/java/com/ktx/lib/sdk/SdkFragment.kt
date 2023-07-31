package com.ktx.lib.sdk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.gyf.barlibrary.ImmersionBar
import com.ktx.lib.R
import com.ktx.lib.base.LoadState
import com.ktx.lib.databinding.FragmentBaseLayoutBinding
import com.ktx.lib.manager.DialogManager
import com.ktx.lib.manager.SharedManager
import com.ktx.lib.utils.UHandler
import com.ktx.lib.utils.UPermission
import com.ktx.lib.widget.Progress

abstract class SdkFragment(layoutId: Int) : Fragment(layoutId), LifecycleObserver {

    private lateinit var mBaseBinding: FragmentBaseLayoutBinding

    protected val mActivity by lazy {
        requireActivity()
    }

    val mDialogManager by lazy {
        DialogManager.instance
    }

    protected val mSharedManager by lazy {
        SharedManager.getInstance(mActivity)
    }

    private var mProgress: Progress? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBaseBinding = inflate(
            inflater,
            R.layout.fragment_base_layout,
            container,
            false
        )

        mBaseBinding.mBaseContainer.addView(root())

        initBase()

        return mBaseBinding.root
    }

    abstract fun root(): View

    private fun initBase() {
        lifecycle.addObserver(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
    }

    abstract fun initialize()

    /**
     * 跳转页面
     */
    protected fun go(c: Class<*>) {
        val intent = Intent(mActivity, c)
        startActivity(intent)
        mActivity.overridePendingTransition(R.anim.from_right_in, R.anim.from_right_out)
    }

    protected fun goTop(c: Class<*>) {
        val intent = Intent(mActivity, c)
        startActivity(intent)
        mActivity.overridePendingTransition(R.anim.from_bottom_in, R.anim.from_bottom_out)
    }

    protected fun alert(msg: String) {
        mBaseBinding.mBaseAlertText.text = msg
        mBaseBinding.mBaseAlertText.visibility = View.VISIBLE
        UHandler.postDelayed {
            mBaseBinding.mBaseAlertText.visibility = View.GONE
        }
    }

    protected fun setState(state: LoadState, msg: String) {
        when (state) {
            LoadState.LOADING -> {
                showProgress(mActivity, msg, 0)
            }

            LoadState.SUCCESS -> {
                hideProgress(mActivity)
            }

            LoadState.ERROR -> {
                hideProgress(mActivity)
                error()
            }
        }
    }

    protected open fun error() {}

    /**
     * 显示模态进度框
     */
    private fun showProgress(activity: Activity, msg: String, tag: Int) {
        if (!activity.isFinishing) {
            if (mProgress == null) {
                mProgress = Progress(activity, msg, tag)
            }
            mProgress?.show(true)
            mProgress?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            mProgress?.setOnDismissListener {
                if (mProgress != null) {
                    mProgress?.dismiss()
                    mProgress = null
                }
            }
        }
    }

    /**
     * 隐藏模态进度框
     */
    private fun hideProgress(activity: Activity) {
        if (!activity.isFinishing) {
            if (mProgress != null) {
                mProgress?.dismiss()
                mProgress = null
            }
        }
    }

    /**
     * 设置支持toolbar, 避免状态栏与标题栏重合
     */
    protected fun supportToolBar(mToolbar: Toolbar?) {
        ImmersionBar.setTitleBar(mActivity, mToolbar)
    }

    fun <B : ViewDataBinding> createDialogBinding(resId: Int): B {
        return inflate(
            LayoutInflater.from(activity),
            resId,
            null,
            false
        )
    }
}