package com.example.styletransferapp.representation.main.prev_pictures_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.styletransferapp.R
import com.example.styletransferapp.business.domain.utils.SessionManager
import com.example.styletransferapp.representation.main.BaseFragment
import com.example.styletransferapp.representation.main.MainActivity
import com.example.styletransferapp.representation.main.prev_pictures_screen.views.GalleryRecyclerViewAdapter
import com.example.styletransferapp.representation.main.prev_pictures_screen.views.GalleryRecyclerViewHolder
import com.example.styletransferapp.representation.utils.RecyclerViewManager
import com.example.styletransferapp.representation.utils.RecyclerViewState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.NullPointerException

@AndroidEntryPoint
class PrevPicturesFragment : BaseFragment() {

    companion object {
        val NAME = this::class.java.name
    }

    private val viewModel: PrevPicturesViewModel by viewModels()

    private var parentActivity: MainActivity? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var galleryManager:
            RecyclerViewManager<GalleryRecyclerViewAdapter, GalleryRecyclerViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
        = inflater.inflate(R.layout.fragment_prev_pictures, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentActivity = (activity as MainActivity)
        recyclerView = view.findViewById(R.id.gallery_recycler_view)

        try {
            galleryManager = RecyclerViewManager(
                recyclerView,
                GalleryRecyclerViewAdapter(viewModel, SessionManager.manager, requireContext()),
                LinearLayoutManager(requireContext())
            )
        } catch (e: NullPointerException) {
            Log.e(NAME, "${e.message}")
        }
        swipeRefreshLayout = view.findViewById(R.id.prev_pictures_refresher)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.changeState(PrevPicturesState.OnGalleryLoading)
            //-- isRefreshing to false when gallery loading completed
        }

        subscribeObservers(view)
    }

    private fun subscribeObservers(view: View) {
        viewModel.currentState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PrevPicturesState.OnGalleryLoading -> parentActivity?.setProgressBar()
                is PrevPicturesState.OnGalleryLoadingError -> {
                    parentActivity?.setSnackbar(
                        view, state.message, null, null
                    )
                    swipeRefreshLayout.isRefreshing = false
                    swipeRefreshLayout.isEnabled = false
                }
                is PrevPicturesState.OnGalleryShown -> showGallery()
                is PrevPicturesState.OnGalleryUpdate -> {}
            }
        }
    }

    private fun showGallery() {
        galleryManager.changeState(
            RecyclerViewState.OnShow
        )

        //-- gallery loading completed
        swipeRefreshLayout.isRefreshing = false
        viewModel.changeState(PrevPicturesState.OnGalleryShown)
    }

    override fun onDetach() {
        galleryManager.changeState(RecyclerViewState.OnDestroy)
        parentActivity = null
        super.onDetach()
    }
}