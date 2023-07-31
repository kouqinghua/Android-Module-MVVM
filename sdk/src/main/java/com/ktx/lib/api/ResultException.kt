package com.ktx.lib.api

import java.lang.RuntimeException

data class ResultException(
    val code: String,
    override val message: String?
) : RuntimeException() {
}