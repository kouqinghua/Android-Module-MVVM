package com.module.progress.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.module.progress.R
import com.xuexiang.xui.utils.DensityUtils.dp2px

@SuppressLint("UseCompatLoadingForDrawables")
class Spinner @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var mSpinnerItem: TextView? = null
    private var mSpinnerArrow: ImageView? = null

    private var myPopupView: View? = null
    private var mySpinnerLayoutResId: Int = R.layout.spinner_layout
    private var myPopupBackgroundResId: Int = R.drawable.spinner_layer

    init {
        val inflate = LayoutInflater.from(context).inflate(mySpinnerLayoutResId, this)
        mSpinnerArrow = findViewById<ImageView>(R.id.mIconDropDown)
        mSpinnerItem = findViewById<TextView>(R.id.mSpinnerText)
        context.getDrawable(R.mipmap.icon_drop_down)?.let {
            mSpinnerArrow?.setImageDrawable(it)
        }
        context.getDrawable(R.drawable.spinner_layer)?.let {
            (mSpinnerArrow?.parent as View).background = it
        }

//        addView(inflate)

        setOnClickListener {
            val mySpinnerPopupWindow = SpinnerPopup(context, myPopupView, myPopupBackgroundResId)
            mySpinnerPopupWindow.setOnDismissListener {
                mSpinnerArrow?.rotation = 0f // 方向标还原
            }
            mSpinnerArrow?.rotation = 180f // 方向标翻转180
            // 加入下拉列表位置偏移调整
            mySpinnerPopupWindow.showAsDropDown(mSpinnerItem?.parent as View, dp2px(0f), dp2px(10f))
        }
    }

    fun setSpinnerLayout(resId: Int) {
        this.mySpinnerLayoutResId = resId
    }

    fun setSpinnerBackground(@DrawableRes resId: Int) {
        context.getDrawable(resId)?.let {
            (mSpinnerArrow?.parent as View).background = it
        }
    }

    fun setPopupBackground(@DrawableRes resId: Int) {
        this.myPopupBackgroundResId = resId
    }

    fun setSpinnerView(popupView: View) {
        this.myPopupView = popupView
    }

    class SpinnerPopup(context: Context?, view: View?, backgroundResId: Int) : PopupWindow(context) {

        init {
            view?.background = context?.getDrawable(backgroundResId)
            contentView = view
            setBackgroundDrawable(ColorDrawable(0x00000000))
            isOutsideTouchable = true
            isFocusable = true
        }
    }
}