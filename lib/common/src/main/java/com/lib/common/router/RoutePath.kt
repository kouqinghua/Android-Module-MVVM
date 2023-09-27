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

    object Wifi {
        private const val Wifi = "/wifi"
        const val SERVICE_WIFI = "$Wifi/wifi_service"
        const val ACTIVITY_WIFI = "$Wifi/WifiActivity"
    }

    object Grid {
        private const val Grid = "/grid"
        const val SERVICE_GRID = "$Grid/grid_service"
        const val ACTIVITY_GRID = "$Grid/GridActivity"
    }

    object Progress {
        private const val Progress = "/progress"
        const val SERVICE_PROGRESS = "$Progress/progress_service"
        const val ACTIVITY_PROGRESS = "$Progress/ProgressActivity"
    }
}