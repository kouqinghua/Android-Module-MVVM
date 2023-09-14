package com.lib.common.router

object RoutePath {
    const val PATH = "johnson"

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
    object Camera {
        private const val Camera = "/camera"
        const val SERVICE_CAMERA = "$Camera/camera_service"
        const val ACTIVITY_CAMERA = "$Camera/CameraActivity"
    }
}