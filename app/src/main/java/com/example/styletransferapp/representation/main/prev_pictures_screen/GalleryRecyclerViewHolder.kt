package com.example.styletransferapp.representation.main.prev_pictures_screen

import android.media.Image
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.styletransferapp.R
import com.google.android.material.card.MaterialCardView

class GalleryRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val card: MaterialCardView = itemView.findViewById(R.id.card)
    val galleryImg: Image = itemView.findViewById(R.id.gallery_img)
    val galleryImgName: TextView = itemView.findViewById(R.id.gallery_img_name)
    val galleryImgDate: TextView = itemView.findViewById(R.id.gallery_img_date)
}