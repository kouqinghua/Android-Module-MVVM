package com.lib.common.dialog

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import java.lang.ref.WeakReference

class DialogHelper {

    private var mRootView: View? = null

    private var mViews: SparseArray<WeakReference<View>>? = null

    constructor(view: View?) {
        mRootView = view
        mViews = SparseArray()
    }

    constructor(context: Context?, layoutResId: Int) {
        mRootView = LayoutInflater.from(context).inflate(layoutResId, null)
        mViews = SparseArray()
    }

    fun setText(resId: Int, text: CharSequence?) {
        getView<TextView>(resId).text = text
    }

    fun setOnClickListener(resId: Int, listener: View.OnClickListener?) {
        val view = getView<View>(resId)
        view.setOnClickListener(listener)
    }

    fun getLayoutView(): View? {
        return mRootView
    }

    fun <T : View?> getView(resId: Int): T {
        val weakReference = mViews!![resId]
        var view: View? = null
        if (weakReference != null) {
            view = weakReference.get()
        }
        if (view == null) {
            view = mRootView!!.findViewById(resId)
            if (view != null) {
                mViews!!.put(resId, WeakReference(view))
            }
        }
        return view as T
    }
}