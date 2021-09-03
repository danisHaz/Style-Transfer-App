package com.example.styletransferapp.representation.main.prev_pictures_screen.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.styletransferapp.R
import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.representation.main.prev_pictures_screen.PrevPicturesViewModel
import javax.inject.Inject

class GalleryRecyclerViewAdapter
@Inject
constructor(
    private val viewModel: PrevPicturesViewModel,
    private val sessionManager: SessionManager,
) : RecyclerView.Adapter<GalleryRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryRecyclerViewHolder
        = GalleryRecyclerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.gallery_recycler_view_viewholder, parent, false))

    override fun onBindViewHolder(holder: GalleryRecyclerViewHolder, position: Int) {
        
    }

    override fun getItemCount(): Int
        = viewModel.gallery?.size ?: 0
}