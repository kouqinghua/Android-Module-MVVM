package com.lib.base.utils

import android.graphics.Color
import android.view.View
import android.widget.TextView


fun onClick(view: View, action: (View) -> Unit) {
    view.setOnClickListener {
        action(it)
    }
}

fun text(text: TextView): String {
    return text.text.toString()
}

fun View.background(color: String) {
    setBackgroundColor(Color.parseColor(color))
}