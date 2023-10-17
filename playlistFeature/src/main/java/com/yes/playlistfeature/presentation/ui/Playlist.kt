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
import com.yes.core.presentation.BaseViewModel
import com.yes.core.presentation.ItemTouchHelperCallback
import com.yes.playlistfeature.R
import com.yes.playlistfeature.databinding.PlaylistBinding
import com.yes.playlistfeature.presentation.contract.PlaylistContract
import com.yes.playlistfeature.presentation.model.TrackUI
import com.yes.playlistfeature.presentation.vm.PlaylistViewModel
import kotlinx.coroutines.launch


class Playlist(
    dependency: Dependency
) : Fragment() {
    interface MediaChooserManager {
        fun showMediaDialog()
    }

    interface PlayModeSelector {
        fun switchMode()
    }

    interface PlaylistManager {
        fun showPlaylistDialog()
    }

    private lateinit var binding: ViewBinding
    private val binder by lazy {
        binding as PlaylistBinding
    }

    private val mediaChooserManager: MediaChooserManager by lazy {
        activity as MediaChooserManager
    }
    private val playlistManager: PlaylistManager by lazy {
        activity as PlaylistManager
    }

    private val viewModel: BaseViewModel<PlaylistContract.Event,
            PlaylistContract.State,
            PlaylistContract.Effect> by viewModels {
        dependency.factory
    }
    private val adapter =dependency.adapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = PlaylistBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupView()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    renderUiState(it)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect {
                    when (it) {
                        is PlaylistContract.Effect.UnknownException -> {
                            showError(com.yes.coreui.R.string.UnknownException)
                        }
                    }
                }
            }
        }
    }

    private fun setupView() {
        binder.btnMedia.setOnClickListener {
            mediaChooserManager.showMediaDialog()
        }
        binder.btnPlaylist.setOnClickListener {
            playlistManager.showPlaylistDialog()
        }
///////////////
        val deleteIconDrawable = ContextCompat.getDrawable(requireContext(), com.yes.coreui.R.drawable.trash_can_outline)
        val itemTouchHelperCallback = ItemTouchHelperCallback(
            enableSwipeToDelete = true,
            enableDragAndDrop = true,
            onSwipeCallback = { position ->
                // Обработка свайпа элемента
                // Например, удаление элемента из списка
                viewModel.setEvent(
                    PlaylistContract.Event.OnDeleteTrack(
                        adapter.getItem(position)
                    )
                )
            },
            onDragAndDropCallback = { fromPosition, toPosition ->
                // Обработка перетаскивания элемента
                // Например, изменение позиции элемента в списке
                adapter.moveItem(fromPosition, toPosition)
                true
            },
            deleteIconDrawable = deleteIconDrawable
        )

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(
            binder.playList
        )
        ///////////////
        val layoutManager = LinearLayoutManager(context)
        binder.playList.layoutManager = layoutManager
        binder.playList.adapter = adapter


    }

    private fun renderUiState(state: PlaylistContract.State) {
        when (state.playlistState) {
            is PlaylistContract.PlaylistState.Success -> {
                dataLoaded(
                    state.playlistState.tracks,
                )
            }

            is PlaylistContract.PlaylistState.Loading -> {
                showLoading()
            }

            is PlaylistContract.PlaylistState.Idle -> {
                idleView()
            }

        }
    }
    private fun dataLoaded(tracks: List<TrackUI>) {
        adapter.setItems(tracks)
      /*  binder.recyclerViewContainer.progressBar.visibility = View.GONE
        binder.recyclerViewContainer.disableView.visibility = View.GONE*/
    }
    private fun idleView() {
        // binder.recyclerViewContainer.dialogTitle.text = ""
        /*  binder.recyclerViewContainer.progressBar.visibility = View.GONE
          binder.recyclerViewContainer.disableView.visibility = View.GONE*/
    }

    private fun showLoading() {
        /*  binder.recyclerViewContainer.progressBar.visibility = View.VISIBLE
          binder.recyclerViewContainer.disableView.visibility = View.VISIBLE*/
    }

    private fun showError(message: Int) {
        /* binder.recyclerViewContainer.progressBar.visibility = View.GONE
         binder.recyclerViewContainer.disableView.visibility = View.GONE*/
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    class Dependency(
        val factory: PlaylistViewModel.Factory,
        val adapter: PlaylistAdapter
    )
}