package com.example.styletransferapp.representation

interface UiListener {

    fun setProgressBar()
    fun hideProgressbar()

    fun setToast()

    fun setSnackBar()

    fun setKeyBoard()
    fun hideKeyBoard()
}