package com.example.styletransferapp.representation.main.prev_pictures_screen

sealed class PrevPicturesState {
    object OnGalleryShown: PrevPicturesState()
    object OnGalleryLoading: PrevPicturesState()
    object OnGalleryUpdate: PrevPicturesState()
    data class OnGalleryLoadingError(val message: String?): PrevPicturesState()
}
