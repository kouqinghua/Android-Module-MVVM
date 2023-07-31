package com.ktx.lib.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


/**
 * dialog管理器
 * author: 星辰CentOS
 * on 2019/12/05
 */

public class DialogManager {

    private XDialog mDialog;
    private Window mWindow;

    private DialogHelper viewHelper;

    public XDialog getDialog() {
        return mDialog;
    }

    private Window getWindow() {
        return mWindow;
    }

    public DialogManager(XDialog alertDialog, Window window) {
        this.mDialog = alertDialog;
        this.mWindow = window;
    }

    private void setViewHelper(DialogHelper viewHelper) {
        this.viewHelper = viewHelper;
    }

    public void setText(int resId, CharSequence text) {
        viewHelper.setText(resId, text);
    }

    public void setOnClickListener(int resId, View.OnClickListener listener) {
        viewHelper.setOnClickListener(resId, listener);
    }


    public <T extends View> T getView(int resId) {
        return viewHelper.getView(resId);
    }

    public static class AlertParams {

        public Context mContext;
        public int mThemeResId;
        public boolean mCancelable = true;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public View mView;
        public int mViewLayoutResId;

        public SparseArray<CharSequence> mViewMap = new SparseArray<>();
        //弱引用 一般点击事件 传入的是activity引用, 防止内存泄漏
        public SparseArray<View.OnClickListener> mListenerMap = new SparseArray<>();

        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mGravity = Gravity.CENTER;
        public int mAnimation = 0;

        public AlertParams(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        /**
         * 绑定和设置参数
         */
        public void bind(DialogManager mAlert) {
            DialogHelper viewHelper = null;
            if (mViewLayoutResId != 0) {
                viewHelper = new DialogHelper(mContext, mViewLayoutResId);
            }

            if (mView != null) {
                viewHelper = new DialogHelper(mView);
            }

            if (viewHelper == null) {
                throw new IllegalArgumentException("请调用setLayoutView()方法设置布局");
            }

            mAlert.setViewHelper(viewHelper);

            for (int j = 0; j < mViewMap.size(); j++) {
                mAlert.setText(mViewMap.keyAt(j), mViewMap.valueAt(j));
            }

            for (int k = 0; k < mListenerMap.size(); k++) {
                mAlert.setOnClickListener(mListenerMap.keyAt(k), mListenerMap.valueAt(k));
            }

            mAlert.getDialog().setContentView(viewHelper.getLayoutView());

            Window window = mAlert.getWindow();
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setGravity(mGravity);

            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            window.setAttributes(params);

            if (mAnimation != 0) {
                window.setWindowAnimations(mAnimation);
            }
        }
    }
}
