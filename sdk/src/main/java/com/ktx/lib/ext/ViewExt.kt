package com.ktx.lib.ext

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ktx.lib.R
import com.ktx.lib.sdk.BaseActivity

inline fun <reified T> Fragment.start() {
    startActivity(Intent(requireActivity(), T::class.java))
    requireActivity().overridePendingTransition(R.anim.from_right_in, R.anim.from_right_out)
}

fun onClick(view: View, action: (View) -> Unit) {
    view.setOnClickListener {
        action(it)
    }
}

fun glide(resid: Int, image: ImageView) {
    image.setTag(R.id.image_uri, resid)
    val tag = image.getTag(R.id.image_uri) as Int
    if (image.getTag(R.id.image_uri) != null && tag == resid) {
        Glide.with(image.context).load(resid).into(image)
    }
}

fun glide(url: String, image: ImageView) {
    image.setTag(R.id.image_uri, url)
    val tag = image.getTag(R.id.image_uri) as Int
    if (image.getTag(R.id.image_uri) != null && tag.equals(url)) {
        Glide.with(image.context).load(url).into(image)
    }
}

fun text(text: TextView): String {
    return text.text.toString()
}

fun View.background(color: String) {
    setBackgroundColor(Color.parseColor(color))
}

fun <B : ViewDataBinding> BaseActivity.generateDialogBinding(resId: Int): B {
    return DataBindingUtil.inflate(
        LayoutInflater.from(this),
        resId,
        null,
        false
    )
}