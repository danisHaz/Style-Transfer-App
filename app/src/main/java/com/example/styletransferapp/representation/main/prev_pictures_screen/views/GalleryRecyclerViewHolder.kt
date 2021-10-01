package com.example.styletransferapp.representation.main.prev_pictures_screen.views

import android.content.Context
import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.example.styletransferapp.R
import com.example.styletransferapp.business.domain.utils.ImageDataHolder
import com.google.android.material.card.MaterialCardView

class GalleryRecyclerViewHolder(
    itemView: View,
    private val context: Context,
) : RecyclerView.ViewHolder(itemView) {
    val card: MaterialCardView = itemView.findViewById(R.id.card)
    val galleryImg: ImageView = itemView.findViewById(R.id.gallery_img)
    val galleryImgName: TextView = itemView.findViewById(R.id.gallery_img_name)
    val galleryImgDescription: TextView = itemView.findViewById(R.id.gallery_img_description)
    val galleryImgDate: TextView = itemView.findViewById(R.id.gallery_img_date)

    fun bind(
        imageDataHolder: ImageDataHolder?,
    ) {
        Glide.with(context)
            .load(imageDataHolder?.imageUrl)
            .transition(withCrossFade())
            .into(galleryImg)
        galleryImgDate.text = imageDataHolder?.dateUpdated ?: "No date provided"
        galleryImgName.text = imageDataHolder?.dateUpdated ?: "No name provided"
        galleryImgDescription.text = imageDataHolder?.description ?: "No description provided"
    }
}