package com.example.styletransferapp.utils

import android.util.Log

object Constants {
    const val baseUrl: String = "http://192.168.43.241:5000"
    const val ApiUrl: String = "https://cataas.com"
    const val PLEASE_TRY_AGAIN_MESSAGE: String
        = "Please try again later"

    val errorReason: Map<Int, String> = mapOf(
        16 to "Wrong query to database",
        17 to "Invalid data passed to body",
        18 to "User is already registered",
        19 to "User is not registered",
        20 to "User is already logged in"
    )
}

fun logUncaughtException(errorCode: Int): String {
    Log.e("Uncaught exception", "Uncaught exception code=[$errorCode]")
    return "Uncaught exception"
}