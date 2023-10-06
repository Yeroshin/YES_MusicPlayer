package com.yes.playlistdialogfeature.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.yes.core.presentation.BaseDialog
import com.yes.core.presentation.BaseViewModel
import com.yes.playlistdialogfeature.R
import com.yes.playlistdialogfeature.databinding.PlaylistDialogBinding
import com.yes.playlistdialogfeature.presentation.contract.PlayListDialogContract
import com.yes.playlistdialogfeature.presentation.model.ItemUi
import kotlinx.coroutines.launch

class PlayListDialog(
    dependency: Dependency
): BaseDialog(){
    override val layout = R.layout.playlist_dialog
    private val adapter = PlayListDialogAdapter()
    private val binder by lazy {
        binding as PlaylistDialogBinding
    }
    private val viewModel: BaseViewModel<PlayListDialogContract.Event,
            PlayListDialogContract.State,
            PlayListDialogContract.Effect> by viewModels {
        dependency.factory
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PlaylistDialogBinding.inflate(inflater)
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)


        binder.recyclerViewContainer.recyclerView.layoutManager = layoutManager
        binder.recyclerViewContainer.recyclerView.adapter = adapter
        val swipeToDeleteCallback = SwipeToDeleteCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(
            binder.recyclerViewContainer.recyclerView
        )

        binder.buttons.cancelBtn.setOnClickListener {
            viewModel.setEvent(PlayListDialogContract.Event.OnCancel)
        }
        binder.buttons.okBtn.setOnClickListener {
            viewModel.setEvent(
                PlayListDialogContract.Event.OnOk(
                    adapter.getItems()

                )
            )
        }
        binder.addPlaylistBtn.setOnClickListener {
            viewModel.setEvent(
                PlayListDialogContract.Event.OnAddPlaylist(
                    binder.playlistName.text.toString()
                )
            )
        }
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
                        is PlayListDialogContract.Effect.UnknownException -> {
                            showError(com.yes.coreui.R.string.UnknownException)
                        }
                    }
                }
            }
        }
    }
    private fun renderUiState(state: PlayListDialogContract.State) {
        when (state.playListDialogState) {
            is PlayListDialogContract.PlayListDialogState.Success -> {
                dataLoaded(
                    state.playListDialogState.items
                )
            }

            is PlayListDialogContract.PlayListDialogState.Loading -> {
                showLoading()
            }

            is PlayListDialogContract.PlayListDialogState.Idle -> {
                idleView()
            }

            is PlayListDialogContract.PlayListDialogState.Dismiss -> {
                dismiss()
            }
        }
    }
    private fun dataLoaded( items: List<ItemUi>) {
        adapter.setItems(items)
        binder.recyclerViewContainer.progressBar.visibility = View.GONE
        binder.recyclerViewContainer.disableView.visibility = View.GONE
    }
    private fun showLoading() {
        binder.recyclerViewContainer.progressBar.visibility = View.VISIBLE
        binder.recyclerViewContainer.disableView.visibility = View.VISIBLE
    }
    private fun idleView() {
        binder.recyclerViewContainer.progressBar.visibility = View.GONE
        binder.recyclerViewContainer.disableView.visibility = View.GONE
    }
    private fun showError(message: Int) {
        binder.recyclerViewContainer.progressBar.visibility = View.GONE
        binder.recyclerViewContainer.disableView.visibility = View.GONE
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    class Dependency(
        val factory: ViewModelProvider.Factory,
    )
}
