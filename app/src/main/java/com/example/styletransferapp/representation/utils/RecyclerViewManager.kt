package com.example.styletransferapp.representation.utils

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewManager<Adapter : RecyclerView.Adapter<VH>, VH : RecyclerView.ViewHolder>(
    private var recyclerView: RecyclerView?,
    private var adapter: Adapter,
    private val layoutManager: RecyclerView.LayoutManager,
) {
    private val currentState: MutableLiveData<RecyclerViewState>
        = MutableLiveData()

    fun resetAdapter(adapter: Adapter) {
        this.adapter = adapter
    }

    fun build() {
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = layoutManager
    }

    fun changeState(state: RecyclerViewState) {
        when (state) {
            is RecyclerViewState.OnAdd -> {
                onAdd(state)
            }
            is RecyclerViewState.OnRemove -> {
                onRemove(state)
            }
            is RecyclerViewState.OnShow -> {}
            is RecyclerViewState.OnHide -> {}
            is RecyclerViewState.OnDestroy -> onDestroy()
        }
    }

    //-- setting updates for new elements
    private fun onAdd(state: RecyclerViewState.OnAdd) {
        adapter.notifyItemRangeInserted(state.positionToAddFrom, state.dataSize)
    }

    private fun onRemove(state: RecyclerViewState.OnRemove) {
        adapter.notifyItemRangeRemoved(state.positionToDeleteFrom, state.dataSize)
    }

    private fun onDestroy() {
        recyclerView = null
        currentState.value = RecyclerViewState.OnDestroy
    }
}