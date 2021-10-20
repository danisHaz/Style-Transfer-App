package com.example.styletransferapp.representation

import androidx.lifecycle.ViewModel
import com.example.styletransferapp.representation.utils.BaseState
import dagger.hilt.android.lifecycle.HiltViewModel

abstract class BaseViewModel<T : BaseState> : ViewModel() {

    abstract fun changeState(state: T)
}