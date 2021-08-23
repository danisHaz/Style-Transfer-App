package com.example.styletransferapp.representation

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity : AppCompatActivity(), UiListener {

    override fun setSnackbar(view: View, message: String?,
                             action: String?, toPerform: ((View) -> Unit)?) {
        Snackbar.make(
            view,
            message ?: Constants.PLEASE_TRY_AGAIN_MESSAGE,
            if (message != null) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT
        ).setAction(
            action ?: Constants.PLEASE_TRY_AGAIN_MESSAGE,
            toPerform
        ).show()
    }

    override fun setToast(context: Context, message: String?) {
        Toast.makeText(
            context,
            message ?: Constants.PLEASE_TRY_AGAIN_MESSAGE,
            if (message != null) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        ).show()
    }
}