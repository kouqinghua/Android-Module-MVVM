package com.hts.superfoot.net

data class ResultState(
    var stateType: ResultStateType,
    var code: String = "100",
    var msg: String? = null,
    var key: String? = null
)