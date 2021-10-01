package com.example.styletransferapp.representation.utils

sealed class RecyclerViewState {
    data class OnAdd(val positionToAddFrom: Int, val dataSize: Int) : RecyclerViewState()
    data class OnRemove(val positionToDeleteFrom: Int, val dataSize: Int) : RecyclerViewState()
    object OnShow : RecyclerViewState()
    object OnDestroy : RecyclerViewState()
}
