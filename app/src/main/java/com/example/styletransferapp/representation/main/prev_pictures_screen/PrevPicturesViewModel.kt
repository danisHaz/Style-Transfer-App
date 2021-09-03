package com.example.styletransferapp.representation.main.prev_pictures_screen

import com.example.styletransferapp.business.domain.utils.DataState
import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.business.interactors.GetImagesEvent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.styletransferapp.business.domain.utils.ImageDataHolder

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlin.coroutines.CoroutineContext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class PrevPicturesViewModel(
    private val sessionManager: SessionManager,
    private val getImagesEvent: GetImagesEvent,
    private val coroutineContext: CoroutineContext,
): ViewModel() {

    val currentState: MutableLiveData<PrevPicturesState>
        = MutableLiveData(PrevPicturesState.OnGalleryLoading)

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
                updateGallery()

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
                        currentState.value =
                            PrevPicturesState.OnGalleryLoadingError(imageDataHolder.message)
                        currentGallery = null
                    }

                    //-- TODO: add try/catch block if imageDataHolder.data is not ImageDataHolder
                    is DataState.Data<*> -> {
                        currentGallery?.add(imageDataHolder.data as ImageDataHolder)
                    }

                    is DataState.Loading -> {
                        currentState.value = PrevPicturesState.OnGalleryLoading
                    }

                    is DataState.Success -> {
                        currentState.value = PrevPicturesState.OnGalleryShown
                        changeState(PrevPicturesState.OnGalleryShown)
                    }
                }
            }

        }
    }

    private fun showGallery() {
        currentState.value = PrevPicturesState.OnGalleryShown
    }

    private fun updateGallery() {
        TODO("needs Update object for different updates: remove, replace and etc.")
    }

}
