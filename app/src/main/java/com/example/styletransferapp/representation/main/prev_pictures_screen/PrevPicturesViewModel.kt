package com.example.styletransferapp.representation.main.prev_pictures_screen

import android.util.Log
import com.example.styletransferapp.business.domain.utils.DataState
import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.business.interactors.GetImagesEvent
import com.example.styletransferapp.business.domain.utils.ImageDataHolder
import com.example.styletransferapp.business.interactors.UpdateGalleryEvent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlin.coroutines.CoroutineContext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class PrevPicturesViewModel(
    private val sessionManager: SessionManager,
    private val getImagesEvent: GetImagesEvent,
    private val updateGalleryEvent: UpdateGalleryEvent,
    private val coroutineContext: CoroutineContext,
): ViewModel() {

    companion object {
        val NAME = this::class.java.name
        const val UPDATE_ERROR = "Update error"
        const val INVALID_DATA_STATE = "Invalid data state provided"
    }

    val currentState: MutableLiveData<PrevPicturesState>
        = MutableLiveData(PrevPicturesState.OnGalleryLoading)
    private suspend fun MutableLiveData<PrevPicturesState>.changeState(state: PrevPicturesState) {
        withContext(Dispatchers.Main) {
            this@changeState.value = state
        }
    }

    private var currentGallery: MutableList<ImageDataHolder>? = null
    val gallery: MutableList<ImageDataHolder>?
        get() = currentGallery

    fun changeState(state: PrevPicturesState) {
        if (currentGallery == null)
            currentGallery = mutableListOf();

        when (state) {
            is PrevPicturesState.OnGalleryLoading ->
                loadGallery()

            is PrevPicturesState.OnGalleryShown ->
                showGallery()

            is PrevPicturesState.OnGalleryUpdate ->
                updateGallery(state.updateGalleryEventState)

            is PrevPicturesState.OnGalleryLoadingError -> {
                //-- actually this state should not be set externally
                //-- so exception should be thrown
            }
        }
    }

    private fun loadGallery() {
        CoroutineScope(coroutineContext).launch {
            getImagesEvent.execute().collect { imageDataHolder ->
                when (imageDataHolder) {
                    is DataState.Error -> {
                        currentState.changeState(
                            PrevPicturesState.OnGalleryLoadingError(imageDataHolder.message)
                        )
                        currentGallery = null
                    }

                    //-- TODO: add try/catch block if imageDataHolder.data is not ImageDataHolder
                    is DataState.Data<*> -> {
                        currentGallery?.add(imageDataHolder.data as ImageDataHolder)
                    }

                    is DataState.Loading -> {
                        currentState.changeState(PrevPicturesState.OnGalleryLoading)
                    }

                    is DataState.Success -> {
                        currentState.changeState(PrevPicturesState.OnGalleryShown)
                        changeState(PrevPicturesState.OnGalleryShown)
                    }
                }
            }

        }
    }

    private fun showGallery() {
        currentState.value = PrevPicturesState.OnGalleryShown
    }

    private fun updateGallery(state: UpdateGalleryEventState) {
        CoroutineScope(coroutineContext).launch {
            updateGalleryEvent.execute(state).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        currentState.changeState(
                            PrevPicturesState.OnGalleryShown
                        )
                    }
                    is DataState.Error -> {
                        val errorMessage = dataState.message ?: UPDATE_ERROR
                        Log.e(NAME, errorMessage)
                        currentState.changeState(
                            PrevPicturesState.OnGalleryLoadingError(errorMessage)
                        )
                    }
                    is DataState.Loading -> {
                        currentState.changeState(
                            PrevPicturesState.OnGalleryLoading
                        )
                    }
                    else -> {
                        Log.e(NAME, INVALID_DATA_STATE)
                    }
                }
            }
        }
    }

}
