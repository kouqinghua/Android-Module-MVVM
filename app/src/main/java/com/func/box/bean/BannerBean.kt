package com.func.box.bean

class BannerBean : ArrayList<BannerBeanItem>()

data class BannerBeanItem(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)