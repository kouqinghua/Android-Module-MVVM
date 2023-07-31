package com.ktx.lib.widget

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.ktx.lib.R
import com.ktx.lib.databinding.UiProgressLayoutBinding

class Progress :Dialog {

    private lateinit var mBinding: UiProgressLayoutBinding

    private var tag: Int = -1
    private var text: String

    constructor(context: Context, text: String, tag: Int) : super(context) {
        this.text = text
        this.tag = tag

        onCreate(context)
    }

    private fun onCreate(context: Context) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.ui_progress_layout,
            null,
            false)

        setContentView(mBinding.root)
        mBinding.sText.text = text
        setImg(tag)
    }

    private fun setImg(tag: Int) {
        if (tag != -1) {
            when (tag) {
                0 -> { //加载中
                    mBinding.run {
                        sBar.visibility = View.VISIBLE
                        sImg.visibility = View.GONE
                    }
                }

                1 -> { // 加载成功
                    mBinding.run {
                        sBar.visibility = View.GONE
                        sImg.visibility = View.VISIBLE
                        sImg.setImageResource(R.drawable.icon_loading_success)
                    }
                }

                2 -> { // 加载失败
                    mBinding.run {
                        sBar.visibility = View.GONE
                        sImg.visibility = View.VISIBLE
                        sImg.setImageResource(R.drawable.icon_loading_fail)
                    }
                }
            }
        }
    }


    fun show(trans: Boolean = false) {
        this.show()
        if (trans) {
            val window: Window? = this.window
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            val layoutParams = window?.attributes
            layoutParams?.dimAmount = 0.0f
            window?.attributes = layoutParams
        }
    }
}