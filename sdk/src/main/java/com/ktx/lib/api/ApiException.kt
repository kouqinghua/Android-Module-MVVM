package com.ktx.lib.api

import java.lang.RuntimeException

data class ApiException(
    val code: Int,
    override val message: String?
) : RuntimeException() {
}