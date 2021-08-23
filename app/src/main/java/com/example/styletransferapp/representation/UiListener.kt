package com.example.styletransferapp.representation

import android.content.Context
import android.view.View

interface UiListener {

    fun setProgressBar()
    fun hideProgressbar()

    fun setToast(context: Context, message: String?)

    fun setSnackbar(view: View, message: String?,
                    action: String?, toPerform: ((View) -> Unit)?)

    fun setKeyBoard()
    fun hideKeyBoard()
}