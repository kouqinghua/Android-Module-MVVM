package com.ktx.lib.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;


import com.ktx.lib.R;

import java.util.Objects;


/**
 * 通用dialog
 * author: 星辰CentOS
 * on 2019/12/05
 */

public class XDialog extends Dialog {

    private DialogManager mAlert;

    XDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mAlert = new DialogManager(this, getWindow());
    }

    public void setText(int resId, CharSequence text) {
        mAlert.setText(resId, text);
    }

    public void setOnClickListener(int resId, View.OnClickListener listener) {
        mAlert.setOnClickListener(resId, listener);
    }

    public <T extends View> T getView(int resId) {
        return mAlert.getView(resId);
    }

    public static class Builder {

        private final DialogManager.AlertParams P;

        public Builder(Context context) {
            this(context, R.style.dialog);
        }

        Builder(Context context, int themeResId) {
            P = new DialogManager.AlertParams(context, themeResId);
        }

        public Builder setLayoutView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        public Builder setLayoutView(int layoutResId) {
            P.mView = null;
            P.mViewLayoutResId = layoutResId;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder setText(int viewId, CharSequence text) {
            P.mViewMap.put(viewId, text);
            return this;
        }

        public Builder setOnClickListener(int viewId, View.OnClickListener listener) {
            P.mListenerMap.put(viewId, listener);
            return this;
        }

        public Builder fullScreen() {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        public Builder fromBottom(boolean isAnimation) {
            if (isAnimation) {
                P.mAnimation = R.style.dialog_from_bottom_anim;
            }
            P.mGravity = Gravity.BOTTOM;
            return this;
        }

        public Builder setWidthAndHeight(int width, int height) {
            P.mWidth = width;
            P.mHeight = height;
            return this;
        }

        public Builder defaultAnimation() {
            P.mAnimation = R.style.dialog_scale_anim;
            return this;
        }

        public Builder setAnimation(int styleAnimation) {
            P.mAnimation = styleAnimation;
            return this;
        }

        public XDialog create() {
            final XDialog dialog = new XDialog(P.mContext, P.mThemeResId);
            P.bind(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            dialog.setCanceledOnTouchOutside(P.mCancelable);
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        public XDialog show() {
            final XDialog dialog = create();
            dialog.show();

            return dialog;
        }

        public XDialog transparent() {
            final XDialog dialog = create();
            dialog.show();
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
            return dialog;
        }
    }
}
