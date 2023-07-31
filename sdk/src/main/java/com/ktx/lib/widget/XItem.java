package com.ktx.lib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ktx.lib.R;


/**
 * 系统条目item
 * 作者: 寇清华
 * 时间: 2018-09-26 17:26
 * 邮箱:252706952@qq.com
 */

public class XItem extends LinearLayout {

    private Context context;
    private TextView mTitle;
    private TextView mTitleContent;
    private XSwitch mOnOff;
    private ImageView mRightImage;

    private ImageView mLeftIcon;

    public ImageView getLeftIcon() {
        return mLeftIcon;
    }

    public TextView getContent() {
        return mTitleContent;
    }

    public TextView getTitle() {
        return mTitle;
    }

    public XItem(Context context) {
        this(context, null);
    }

    public XItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        initView(attrs);
    }

    @SuppressLint("CustomViewStyleable")
    private void initView(AttributeSet attrs) {

        View view = LinearLayout.inflate(context, R.layout.ui_common_item_layout, this);

        mTitle = view.findViewById(R.id.mTitle);
        mTitleContent = view.findViewById(R.id.mTitleContent);

        mOnOff = view.findViewById(R.id.mOnOff);
        mRightImage = view.findViewById(R.id.mRightImage);
        mLeftIcon = view.findViewById(R.id.mLeftIcon);

        @SuppressLint("Recycle")
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XItem);

        if (typedArray != null) {
            boolean titleContentGone = typedArray.getBoolean(R.styleable.XItem_title_content_gone, false);
            if (titleContentGone) {
                mTitleContent.setVisibility(INVISIBLE);
            } else {
                mTitleContent.setVisibility(VISIBLE);
            }

            float contentSize = typedArray.getDimension(R.styleable.XItem_text_content_size, 0);
            if (contentSize != 0) {
                mTitleContent.setTextSize(contentSize);
            }

            boolean leftIconGone = typedArray.getBoolean(R.styleable.XItem_left_icon_gone, true);
            if (leftIconGone) {
                mLeftIcon.setVisibility(GONE);
            } else {
                mLeftIcon.setVisibility(VISIBLE);
            }

            int leftIconId = typedArray.getResourceId(R.styleable.XItem_left_icon, 0);
            if (leftIconId != 0) {
                mLeftIcon.setBackgroundResource(leftIconId);
            }

            boolean rightImageGone = typedArray.getBoolean(R.styleable.XItem_right_image_gone, false);
            if (rightImageGone) {
                mRightImage.setVisibility(GONE);
            } else {
                mRightImage.setVisibility(VISIBLE);
            }

            boolean switchGone = typedArray.getBoolean(R.styleable.XItem_switch_gone, true);
            if (switchGone) {
                mOnOff.setVisibility(GONE);
            } else {
                mOnOff.setVisibility(VISIBLE);
            }

            String leftTitle = typedArray.getString(R.styleable.XItem_left_title);
            if (!TextUtils.isEmpty(leftTitle)) {
                mTitle.setText(leftTitle);
            }

            int titleColor = typedArray.getColor(R.styleable.XItem_title_color, 0x000000);
            if (titleColor == 0) {
                mTitle.setTextColor(Color.parseColor("#000000"));
            } else {
                mTitle.setTextColor(titleColor);
            }

            int contentColor = typedArray.getColor(R.styleable.XItem_text_content_color, 0x000000);
            if (contentColor == 0) {
                mTitleContent.setTextColor(Color.parseColor("#828282"));
            } else {
                mTitleContent.setTextColor(contentColor);
            }

            String textContent = typedArray.getString(R.styleable.XItem_text_content);
            if (!TextUtils.isEmpty(textContent)) {
                mTitleContent.setText(textContent);
            }
            typedArray.recycle();
        }
    }

    /**
     * 设置左边文字
     */
    public void setTitle(String s) {
        mTitle.setText(s);
    }

    /**
     * 设置左边文字大小
     */
    public void setTitleSize(float f) {
        mTitle.setTextSize(f);
    }

    /**
     * 设置左边文字是否为粗体
     */
    public void setTitleBold(boolean isBold) {
        if (isBold) {
            mTitle.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }

    /**
     * 设置左边文字是颜色
     */
    public void setTitleColor(int c) {
        mTitle.setTextColor(c);
    }

    /**
     * 设置中间文字描述内容字体大小
     */
    public void setTitleContentSize(float f) {
        mTitleContent.setTextSize(f);
    }

    /**
     * 设置中间文字描述内容
     */
    public void setTitleContent(String s) {
        mTitleContent.setText(s);
    }

    /**
     * 设置中间文字描述内容
     */
    public void setTitleContentColor(int c) {
        mTitleContent.setTextColor(c);
    }
    public void setLeftIcon(int resid) {
        mLeftIcon.setBackgroundResource(resid);
    }

    /**
     * 获取中间文字描述内容
     */
    public String getTitleContent() {
        return mTitleContent.getText().toString();
    }

    /**
     * 设置中间文字是否显示
     */
    public void setTitleContentGone(boolean b) {
        if (b) {
            mTitleContent.setVisibility(INVISIBLE);
        } else {
            mTitleContent.setVisibility(VISIBLE);
        }
    }

    /**
     * 设置右边开关按钮是否显示
     */
    public void setSwitchGone(boolean b) {
        if (b) {
            mOnOff.setVisibility(GONE);
        } else {
            mOnOff.setVisibility(VISIBLE);
        }
    }

    /**
     * 设置开关是否显示
     */
    public void setSwitchOn(boolean b) {
        mOnOff.setChecked(b);
    }

    /**
     * 设置开关是否显示
     */
    @SuppressLint("ClickableViewAccessibility")
    public void setSwitchClickEnable(final boolean b) {
        mOnOff.setClickable(b);
        mOnOff.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return !b;
            }
        });
    }

    /**
     * 获取开关状态
     */
    public Boolean getSwitchOn() {
        return mOnOff.isChecked();
    }
}
