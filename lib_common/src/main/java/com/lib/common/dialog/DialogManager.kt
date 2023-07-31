package com.lib.common.dialog

import android.content.Context
import android.content.DialogInterface
import android.util.SparseArray
import android.view.*
import com.example.lib_common.R

class DialogManager(val mDialog: XDialog?, val mWindow: Window?) {


    private var viewHelper: DialogHelper? = null

    fun getDialog(): XDialog? {
        return mDialog
    }

    private fun getWindow(): Window? {
        return mWindow
    }

    private fun setViewHelper(viewHelper: DialogHelper) {
        this.viewHelper = viewHelper
    }

    fun setText(resId: Int, text: CharSequence?) {
        viewHelper!!.setText(resId, text)
    }

    fun setOnClickListener(resId: Int, listener: View.OnClickListener?) {
        viewHelper!!.setOnClickListener(resId, listener)
    }


    fun <T : View?> getView(resId: Int): T {
        return viewHelper!!.getView(resId)
    }

    class AlertParams(var mContext: Context, var mThemeResId: Int) {
        var mCancelable = true
        var mOnCancelListener: DialogInterface.OnCancelListener? = null
        var mOnDismissListener: DialogInterface.OnDismissListener? = null
        var mOnKeyListener: DialogInterface.OnKeyListener? = null
        var mView: View? = null
        var mViewLayoutResId = 0
        var mViewMap = SparseArray<CharSequence>()

        //弱引用 一般点击事件 传入的是activity引用, 防止内存泄漏
        var mListenerMap = SparseArray<View.OnClickListener>()
        var mWidth = ViewGroup.LayoutParams.WRAP_CONTENT
        var mHeight = ViewGroup.LayoutParams.WRAP_CONTENT
        var mGravity = Gravity.CENTER
        var mAnimation: Int = R.style.dialog_scale_anim

        /**
         * 绑定和设置参数
         */
        fun bind(mAlert: DialogManager) {
            var viewHelper: DialogHelper? = null
            if (mViewLayoutResId != 0) {
                viewHelper = DialogHelper(mContext, mViewLayoutResId)
            }
            if (mView != null) {
                viewHelper = DialogHelper(mView)
            }
            requireNotNull(viewHelper) { "请调用setLayoutView()方法设置布局" }
            mAlert.setViewHelper(viewHelper)
            for (j in 0 until mViewMap.size()) {
                mAlert.setText(mViewMap.keyAt(j), mViewMap.valueAt(j))
            }
            for (k in 0 until mListenerMap.size()) {
                mAlert.setOnClickListener(mListenerMap.keyAt(k), mListenerMap.valueAt(k))
            }
            mAlert.getDialog()!!.setContentView(viewHelper.getLayoutView()!!)
            val window = mAlert.getWindow()
            window!!.decorView.setPadding(0, 0, 0, 0)
            window.setGravity(mGravity)
            val params = window.attributes
            params.width = mWidth
            params.height = mHeight
            window.attributes = params
            if (mAnimation != 0) {
                window.setWindowAnimations(mAnimation)
            }
        }
    }
}