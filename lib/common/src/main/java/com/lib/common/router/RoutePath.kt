package com.lib.common.router

object RoutePath {
    const val PATH = "xcher"

    object Login {
        private const val LOGIN = "/login"
        const val SERVICE_LOGIN = "$LOGIN/login_service"
        const val ACTIVITY_LOGIN = "$LOGIN/login"
    }

    object Main {
        private const val MAIN = "/main"
        const val SERVICE_MAIN = "$MAIN/main_service"
        const val ACTIVITY_MAIN = "$MAIN/MainActivity"
    }

    object Progress {
        private const val Progress = "/progress"
        const val SERVICE_PROGRESS = "$Progress/progress_service"
        const val ACTIVITY_PROGRESS = "$Progress/ProgressActivity"
    }
}