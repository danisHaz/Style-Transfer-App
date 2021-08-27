package com.example.styletransferapp.representation.main.prev_pictures_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.styletransferapp.R
import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.business.services.AppRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GalleryRecyclerViewAdapter
@Inject
constructor(
    repo: AppRepository,
    sessionManager: SessionManager,
    coroutineContext: CoroutineContext
) : RecyclerView.Adapter<GalleryRecyclerViewHolder>() {

    private val _repo = repo
    private val _sessionManager = sessionManager
    private val _defaultContext = coroutineContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryRecyclerViewHolder
        = GalleryRecyclerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.gallery_recycler_view_viewholder, parent, false))

    override fun onBindViewHolder(holder: GalleryRecyclerViewHolder, position: Int) {
        
    }

    override fun getItemCount(): Int
        = _repo.getGallerySize(_sessionManager.sessionData.userId)
}