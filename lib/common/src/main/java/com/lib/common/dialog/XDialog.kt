package com.lib.common.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.example.lib_common.R

class XDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    private lateinit var mAlert: DialogManager

    init {
        mAlert = DialogManager(this, window)
    }

    fun setText(resId: Int, text: CharSequence?) {
        mAlert.setText(resId, text)
    }

    fun setOnClickListener(resId: Int, listener: View.OnClickListener?) {
        mAlert.setOnClickListener(resId, listener)
    }

    fun <T : View?> getView(resId: Int): T {
        return mAlert.getView(resId)
    }

    class Builder internal constructor(context: Context, themeResId: Int) {

        private val P: DialogManager.AlertParams

        constructor(context: Context) : this(context, R.style.dialog)

        init {
            P = DialogManager.AlertParams(context, themeResId)
        }

        fun setLayoutView(view: View?): Builder {
            P.mView = view
            P.mViewLayoutResId = 0
            return this
        }

        fun setLayoutView(layoutResId: Int): Builder {
            P.mView = null
            P.mViewLayoutResId = layoutResId
            return this
        }

        fun setCancel(cancelable: Boolean): Builder {
            P.mCancelable = cancelable
            return this
        }

        fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener?): Builder {
            P.mOnCancelListener = onCancelListener
            return this
        }

        fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener?): Builder {
            P.mOnDismissListener = onDismissListener
            return this
        }

        fun setOnKeyListener(onKeyListener: DialogInterface.OnKeyListener?): Builder {
            P.mOnKeyListener = onKeyListener
            return this
        }

        fun setText(viewId: Int, text: CharSequence?): Builder {
            P.mViewMap.put(viewId, text)
            return this
        }

        fun setOnClickListener(viewId: Int, listener: View.OnClickListener?): Builder {
            P.mListenerMap.put(viewId, listener)
            return this
        }

        fun full(): Builder {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT
            return this
        }

        fun bottom(isAnimation: Boolean): Builder {
            if (isAnimation) {
                P.mAnimation = R.style.dialog_from_bottom_anim
            }
            P.mGravity = Gravity.BOTTOM
            return this
        }

        fun setWidthAndHeight(width: Int, height: Int): Builder {
            P.mWidth = width
            P.mHeight = height
            return this
        }

        fun setAnimation(styleAnimation: Int): Builder {
            P.mAnimation = styleAnimation
            return this
        }

        fun create(): XDialog {
            val dialog = XDialog(P.mContext, P.mThemeResId)
            P.bind(dialog.mAlert)
            dialog.setCancelable(P.mCancelable)
            dialog.setCanceledOnTouchOutside(P.mCancelable)
            dialog.setOnCancelListener(P.mOnCancelListener)
            dialog.setOnDismissListener(P.mOnDismissListener)
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener)
            }
            return dialog
        }
    }
}