package com.yes.playlistfeature.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.yes.core.presentation.BaseDependency
import com.yes.core.presentation.BaseFragment
import com.yes.core.presentation.BaseViewModel
import com.yes.core.presentation.ItemTouchHelperCallback
import com.yes.core.presentation.UiState
import com.yes.playlistfeature.databinding.PlaylistBinding
import com.yes.playlistfeature.di.component.PlaylistComponent
import com.yes.playlistfeature.presentation.contract.PlaylistContract
import com.yes.playlistfeature.presentation.model.TrackUI
import com.yes.playlistfeature.presentation.vm.PlaylistViewModel
import kotlinx.coroutines.launch


class PlaylistScreen : BaseFragment(){
    interface DependencyResolver {
        fun resolvePlaylistComponent(): BaseDependency
    }


    override val dependency by lazy {
        (requireActivity().application as DependencyResolver)
            .resolvePlaylistComponent()
    }

    interface MediaChooserManager {
        fun showMediaDialog()
    }


    interface PlaylistManager {
        fun showPlaylistDialog()
    }


    private val binder by lazy {
        binding as PlaylistBinding
    }

    private val mediaChooserManager by lazy {
        activity as MediaChooserManager
    }
    private val playlistManager by lazy {
        activity as PlaylistManager
    }


    private val adapter by lazy {
        PlaylistAdapter{
            position ->
            viewModel.setEvent(
                PlaylistContract.Event.OnPlayTrack(position)
            )

        }
    }

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        return PlaylistBinding.inflate(inflater)
    }

    override fun setUpView() {
        binder.btnMedia.setOnClickListener {
            mediaChooserManager.showMediaDialog()
        }
        binder.btnPlaylist.setOnClickListener {
            playlistManager.showPlaylistDialog()
        }
        binder.btnMode.setOnClickListener {
            viewModel.setEvent(PlaylistContract.Event.OnModeChange)
        }

///////////////
        val onSwipeCallback: (position: Int) -> Unit = { position ->

            //  adapter.removeItem(position)
            val deletedItem = adapter.getItem(position)
            binder.playList.layoutManager?.removeViewAt(position)
            viewModel.setEvent(
                PlaylistContract.Event.OnDeleteTrack(
                    deletedItem
                )
            )


        }
        val onItemMove: (fromPosition: Int, toPosition: Int) -> Unit = { fromPosition, toPosition ->
            adapter.moveItem(fromPosition, toPosition)
        }
        val deleteIconDrawable = ContextCompat.getDrawable(
            requireContext(),
            com.yes.coreui.R.drawable.trash_can_outline,
        )
        val deleteIconColor = ContextCompat.getColor(requireContext(), com.yes.coreui.R.color.tint)
        val backgroundColor = ContextCompat.getColor(
            requireContext(),
            com.yes.coreui.R.color.button_centerColor_pressed
        )


        val itemTouchHelperCallback = ItemTouchHelperCallback(
            enableSwipeToDelete = true,
            enableDragAndDrop = true,
            onSwipeCallback = onSwipeCallback,
            onItemMove = onItemMove,
            deleteIconDrawable = deleteIconDrawable,
            deleteIconColor = deleteIconColor,
            backgroundColor = backgroundColor,
            onDraggedItemDrop = { from, to ->
                viewModel.setEvent(
                    PlaylistContract.Event.OnMoveItemPosition(from, to)
                )
            }
        )

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(
            binder.playList
        )
        ///////////////
        binder.playList.layoutManager = LinearLayoutManager(context)
        binder.playList.adapter = adapter


    }

    override fun renderUiState(state: UiState) {
        state as PlaylistContract.State
        when (state.playlistState) {
            is PlaylistContract.PlaylistState.Success -> {
                state.mode?.let {
                    setMode(it)
                }
                state.tracks?.let {
                    setItemsToAdapter(
                        state.tracks
                    )
                }
                state.currentTrack?.let {
                    setAdaptersCurrentTrack(
                        state.currentTrack
                    )
                }
            }

            is PlaylistContract.PlaylistState.Loading -> {
                showLoading()
            }

            is PlaylistContract.PlaylistState.Idle -> {
                idleView()
            }

        }
    }

    override fun showEffect() {
        TODO("Not yet implemented")
    }

    private fun setAdaptersCurrentTrack(position:Int){
        adapter.setCurrent(position)
        binder.playList.layoutManager?.scrollToPosition(position)
    }

    private fun setMode(imageLevel: Int) {
        binder.btnMode.setImageLevel(imageLevel)
    }

    private fun setItemsToAdapter(tracks: List<TrackUI>) {
        binder.playList.layoutManager?.removeAllViews()
        adapter.setItems(tracks)

        binder.progressBar.visibility = View.GONE
        binder.disableView.visibility = View.GONE
    }

    private fun idleView() {
          binder.progressBar.visibility = View.GONE
          binder.disableView.visibility = View.GONE
    }

    private fun showLoading() {
          binder.progressBar.visibility = View.VISIBLE
          binder.disableView.visibility = View.VISIBLE
    }

    private fun showError(message: Int) {
         binder.progressBar.visibility = View.GONE
         binder.disableView.visibility = View.GONE
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}