package com.module.login.bean

class LoginBanner : ArrayList<LoginBannerItem>()

data class LoginBannerItem(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)