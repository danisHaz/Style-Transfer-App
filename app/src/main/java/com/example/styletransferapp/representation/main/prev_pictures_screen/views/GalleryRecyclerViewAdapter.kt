package com.example.styletransferapp.representation.main.prev_pictures_screen.views

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.styletransferapp.R
import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.representation.main.prev_pictures_screen.PrevPicturesViewModel

class GalleryRecyclerViewAdapter(
    private val viewModel: PrevPicturesViewModel,
    private val sessionManager: SessionManager,
    private val context: Context,
) : RecyclerView.Adapter<GalleryRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryRecyclerViewHolder
        = GalleryRecyclerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.gallery_recycler_view_viewholder, parent, false), context)

    override fun onBindViewHolder(holder: GalleryRecyclerViewHolder, position: Int) {
        holder.bind(viewModel.gallery?.get(position))
    }

    override fun getItemCount(): Int
        = viewModel.gallery?.size ?: 0
}