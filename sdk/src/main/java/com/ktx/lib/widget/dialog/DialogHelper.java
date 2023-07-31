package com.ktx.lib.widget.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * dialog helper
 * author: 星辰CentOS
 * on 2019/12/05
 */

class DialogHelper {

    private View mRootView = null;

    private SparseArray<WeakReference<View>> mViews;

    DialogHelper(Context context, int layoutResId) {
        mRootView = LayoutInflater.from(context).inflate(layoutResId, null);
        mViews = new SparseArray<>();
    }

    DialogHelper(View view) {
        this.mRootView = view;
        mViews = new SparseArray<>();
    }

    public void setText(int resId, CharSequence text) {
        TextView mTextView = getView(resId);
        if (mTextView != null) {
            mTextView.setText(text);
        }
    }

    void setOnClickListener(int resId, View.OnClickListener listener) {
        View view = getView(resId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    View getLayoutView() {
        return mRootView;
    }

    public <T extends View> T getView(int resId) {
        WeakReference<View> weakReference = mViews.get(resId);
        View view = null;
        if (weakReference != null) {
            view = weakReference.get();
        }
        if (view == null) {
            view = mRootView.findViewById(resId);
            if (view != null) {
                mViews.put(resId, new WeakReference<>(view));
            }
        }
        return (T) view;
    }
}
