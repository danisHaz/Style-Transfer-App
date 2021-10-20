package com.example.styletransferapp.representation.main.prev_pictures_screen

import com.example.styletransferapp.representation.utils.BaseState

sealed class PrevPicturesState : BaseState {
    object OnGalleryShown: PrevPicturesState()
    object OnGalleryLoading: PrevPicturesState()
    data class OnGalleryUpdate(val updateGalleryEventState: UpdateGalleryEventState): PrevPicturesState()
    data class OnGalleryLoadingError(val message: String?): PrevPicturesState()
}
