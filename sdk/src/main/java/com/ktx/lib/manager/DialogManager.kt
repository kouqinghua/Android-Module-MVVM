package com.ktx.lib.manager

import android.content.Context
import android.view.View
import com.ktx.lib.widget.dialog.XDialog
import java.util.*

class DialogManager private constructor() {

    companion object {
        val instance: DialogManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DialogManager()
        }
    }

    /**
     * 中间弹框
     */
    fun center(view: View): XDialog {
        return XDialog.Builder(view.context).setLayoutView(view).fullScreen().defaultAnimation().create()
    }

    /**
     * 底部弹框
     */
    fun bottom(view: View): XDialog {
        return XDialog.Builder(view.context).setLayoutView(view).fullScreen().fromBottom(true).create()
    }
}