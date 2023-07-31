package com.lib.common.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.lib_common.R
import com.example.lib_common.databinding.LayoutActionBarBinding
import com.lib.common.base.BaseConfig

class ActionBar : RelativeLayout {

    private var mBarRightTextColor: Int? = 0
    private var mBarLeftHint: Boolean? = false
    private var mBarRightText: String? = ""
    private var mBarLeftIconMargin: Float = 0f
    private var mBarLeftIcon: Drawable? = null
    private var mBarRightIconMargin: Float = 0f
    private var mBarRightIcon: Drawable? = null
    private var mBarTitleColor: Int = 0
    private var mBarTitleSize: Float = 0f
    private var mBarTitle: String? = ""
    private var mContext: Context? = null
    private var mBarLeftIconWidth: Float = 0f
    private var mBarLeftIconHeight: Float = 0f
    private var mBarRightIconWidth: Float = 0f
    private var mBarRightIconHeight: Float = 0f
    private lateinit var mBinding: LayoutActionBarBinding

    constructor(context: Context) : super(context) {
        init(context)
    }

    @SuppressLint("Recycle")
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val ob = context.obtainStyledAttributes(attrs, R.styleable.ActionBar)
        mBarLeftHint = ob.getBoolean(R.styleable.ActionBar_bar_left_hint, false)
        mBarTitle = ob.getString(R.styleable.ActionBar_bar_title)
        mBarRightText = ob.getString(R.styleable.ActionBar_bar_right_text)
        mBarRightTextColor = ob.getColor(R.styleable.ActionBar_bar_right_color, 0)
        mBarTitleSize = ob.getDimension(R.styleable.ActionBar_bar_title_size, 0f)
        mBarTitleColor = ob.getColor(R.styleable.ActionBar_bar_title_color, 0)
        mBarRightIcon = ob.getDrawable(R.styleable.ActionBar_bar_right_icon)
        mBarRightIconMargin = ob.getDimension(R.styleable.ActionBar_bar_right_icon_margin, 0f)
        mBarLeftIcon = ob.getDrawable(R.styleable.ActionBar_bar_left_icon)
        mBarLeftIconMargin = ob.getDimension(R.styleable.ActionBar_bar_left_icon_margin, 0f)
        mBarLeftIconWidth = ob.getDimension(R.styleable.ActionBar_bar_left_width, 0f)
        mBarLeftIconHeight = ob.getDimension(R.styleable.ActionBar_bar_left_height, 0f)
        mBarRightIconWidth = ob.getDimension(R.styleable.ActionBar_bar_right_width, 0f)
        mBarRightIconHeight = ob.getDimension(R.styleable.ActionBar_bar_right_height, 0f)
        ob.recycle()

        init(context)
    }

    private fun init(context: Context) {
        mContext = context
        val view = View.inflate(context, R.layout.layout_action_bar, null)
        mBinding = DataBindingUtil.bind(view)!!
        initData()
        addView(view)
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        //设置ActionBar的高度
        if (BaseConfig.actionBarHeight != 0) {
            val parentParams = mBinding.barLayout.layoutParams as LinearLayout.LayoutParams
            parentParams.height = BaseConfig.actionBarHeight
            mBinding.barLayout.layoutParams = parentParams
        }
        //设置ActionBar的背景颜色
        if (BaseConfig.actionBarBg != 0) {
            mBinding.barLayout.setBackgroundColor(
                ContextCompat.getColor(context, BaseConfig.actionBarBg)
            )
        }

        actionBarTitle()
        actionBarLeft()
        actionBarRight()
    }

    // 左侧按钮
    private fun actionBarLeft() {
        mBinding.barIvBack.apply {
            //是否显示
            visibility = if (mBarLeftHint!!) GONE else VISIBLE
            //通过属性设置
            if (mBarLeftIcon != null) {
                //设置左侧图标
                setImageDrawable(mBarLeftIcon)
                //设置距离左侧的边距
                val params = layoutParams as LinearLayout.LayoutParams
                if (mBarLeftIconMargin != 0f) {
                    params.leftMargin = mBarLeftIconMargin.toInt()
                }
                //设置宽高
                if (mBarLeftIconWidth != 0f) {
                    params.width = mBarLeftIconWidth.toInt()
                    params.height = mBarLeftIconHeight.toInt()
                }
                layoutParams = params
                //设置左侧点击的事件
                mBinding.barLayoutBack.setOnClickListener {
                    if (mOnLeftClickListener != null) {
                        mOnLeftClickListener!!.leftClick()
                    }
                }
            } else {
                if (BaseConfig.leftIcon != View.NO_ID) {
                    setBarLeftIcon(
                        BaseConfig.leftIcon,
                        BaseConfig.leftIconWidth,
                        BaseConfig.leftIconHeight,
                        BaseConfig.leftIconMarginLeft
                    )
                }
                mBinding.barLayoutBack.setOnClickListener {
                    //返回，销毁页面
                    (mContext as Activity).finish()
                }
            }
        }
    }

    // 右侧按钮
    private fun actionBarRight() {
        //内容不为空，显示右侧内容
        if (!TextUtils.isEmpty(mBarRightText)) {
            mBinding.barTvRight.visibility = View.VISIBLE
            mBinding.barTvRight.text = mBarRightText
        }

        //右侧设置图片格式
        if (mBarRightIcon != null) {
            mBinding.barTvRight.visibility = View.VISIBLE
            mBinding.barTvRight.background = mBarRightIcon
        }

        val layoutParams = mBinding.barTvRight.layoutParams as LayoutParams
        //设置距离右边距离
        if (mBarRightIconMargin != 0f) {
            layoutParams.rightMargin = mBarRightIconMargin.toInt()
        }
        //设置宽
        if (mBarRightIconWidth != 0f) {
            layoutParams.width = mBarRightIconWidth.toInt()
        }
        //设置高
        if (mBarRightIconHeight != 0f) {
            layoutParams.height = mBarRightIconHeight.toInt()
        }
        mBinding.barTvRight.layoutParams = layoutParams

        //设置右边的颜色
        if (mBarRightTextColor != 0) {
            mBinding.barTvRight.setTextColor(mBarRightTextColor!!)
        }

        //右侧按钮点击事件
        mBinding.barTvRight.setOnClickListener {
            if (mOnRightClickListener != null) {
                mOnRightClickListener!!.rightClick()
            }
        }
    }

    // 设置标题
    private fun actionBarTitle() {
        mBinding.barTvTitle.apply {
            //设置title
            if (!TextUtils.isEmpty(mBarTitle)) text = mBarTitle

            //设置title大小
            if (mBarTitleSize != 0f) {
                textSize = mBarTitleSize
            } else if (BaseConfig.titleSize != 0f) {
                //全局设置title文字大小
                textSize = BaseConfig.titleSize
            }
            //设置title颜色
            if (mBarTitleColor != 0) {
                setTextColor(mBarTitleColor)
            } else if (BaseConfig.titleColor != 0) {
                //全局设置title颜色
                //设置标题颜色
                setTextColor(ContextCompat.getColor(context, BaseConfig.titleColor))
            }
        }
    }

    /**
     * 设置Title
     */
    fun setBarTitle(title: String) {
        mBinding.barTvTitle.text = title
    }

    /**
     * 设置标题颜色
     */
    fun setBarTitleColor(color: Int) {
        mBinding.barTvTitle.setTextColor(color)
    }

    /**
     * 设置标题大小
     */
    fun setBarTitleSize(size: Float) {
        mBinding.barTvTitle.textSize = size
    }

    /**
     * 只显示标题
     */
    fun onlyShowTitle() {
        mBinding.barLayoutBack.isVisible = false
        mBinding.barTvRight.isVisible = false
    }

    /**
     * 显示右侧按钮
     */
    fun getRightMenu(menu: Any): TextView {
        if (menu is String) {
            mBarRightText = menu
        } else if (menu is Drawable) {
            mBarRightIcon = menu
        }
        actionBarRight()
        return mBinding.barTvRight
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:获取title
     */
    fun getBarTitle(): TextView {
        return mBinding.barTvTitle
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:左侧
     */
    fun getLeftMenu(): ImageView {
        return mBinding.barIvBack
    }

    /**
     * 设置左边的图标及宽高距离左边的距离
     */
    private fun setBarLeftIcon(icon: Int, l: Int = 0, w: Int = 0, h: Int = 0): ActionBar {
        if (icon != View.NO_ID) {
            mBinding.barIvBack.setImageResource(icon)
            if (l != 0) {
                val layoutParams = mBinding.barIvBack.layoutParams as LinearLayout.LayoutParams
                layoutParams.apply {
                    width = w
                    height = h
                    leftMargin = l
                }
                mBinding.barIvBack.layoutParams = layoutParams
            }
            mBinding.barLayoutBack.setOnClickListener {
                if (mOnLeftClickListener != null) {
                    mOnLeftClickListener!!.leftClick()
                }
            }
        }
        return this
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:设置右边的宽高和距离
     */
    fun setBarRightParams(r: Int = 0, w: Int = 0, h: Int = 0): ActionBar {
        if (r != 0) {
            val layoutParams = mBinding.barTvRight.layoutParams as LayoutParams
            layoutParams.width = w
            layoutParams.height = h
            layoutParams.rightMargin = r
            mBinding.barTvRight.layoutParams = layoutParams
        }
        return this
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:隐藏左边的按钮
     */
    fun hideLeftBack() {
        mBinding.barLayoutBack.visibility = View.GONE
    }

    fun setOnRightClickListener(action: () -> Unit) {
        mOnRightClickListener = object : OnRightClickListener {
            override fun rightClick() {
                action()
            }

        }
    }

    private var mOnRightClickListener: OnRightClickListener? = null

    interface OnRightClickListener {
        fun rightClick()
    }

    private var mOnLeftClickListener: OnLeftClickListener? = null

    interface OnLeftClickListener {
        fun leftClick()
    }

    fun setOnLeftClickListener(action: () -> Unit) {
        mOnLeftClickListener = object : OnLeftClickListener {
            override fun leftClick() {
                action()
            }
        }
    }
}