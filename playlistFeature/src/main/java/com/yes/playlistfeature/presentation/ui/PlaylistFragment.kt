package com.yes.playlistfeature.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.yes.core.presentation.BaseViewModel
import com.yes.playlistfeature.databinding.FragmentPlaylistBinding
import com.yes.playlistfeature.presentation.contract.PlaylistContract
import com.yes.playlistfeature.presentation.vm.PlaylistViewModel
import kotlinx.coroutines.launch


class PlaylistFragment : Fragment() {
    interface MediaChooserManager {
        fun showMediaDialog()
    }

    interface PlaylistManager {
        fun showPlaylistDialog()
    }

    private lateinit var binding: FragmentPlaylistBinding

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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentPlaylistBinding.inflate(inflater)
        binding.btnMedia.setOnClickListener {
            mediaChooserManager.showMediaDialog()
        }
        binding.btnPlaylist.setOnClickListener {
            playlistManager.showPlaylistDialog()
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupRecyclerView()
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
    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)


        binder.recyclerViewContainer.recyclerView.layoutManager = layoutManager
        binder.recyclerViewContainer.recyclerView.adapter = adapter
        binder.buttons.cancelBtn.setOnClickListener {
            viewModel.setEvent(TrackDialogContract.Event.OnButtonCancelClicked)
        }
        binder.buttons.okBtn.setOnClickListener {
            viewModel.setEvent(
                TrackDialogContract.Event.OnButtonOkClicked(
                    if (binder.networkBtn.isChecked) {
                        adapter.getItems()
                    } else {
                        listOf(
                            MenuUi.ItemUi(
                                name = binder.networkPath.text.toString(),
                                selected = true
                            )
                        )
                    }

                )
            )
        }
        binder.networkBtn.setOnCheckedChangeListener { _, isChecked ->
            // write here your code for example ...
            if (isChecked) {
                binder.recyclerViewContainer.disableView.visibility = View.GONE
                binder.networkPath.isEnabled = false
            } else {
                binder.recyclerViewContainer.disableView.visibility = View.VISIBLE
                binder.networkPath.isEnabled = true
            }
        }
    }
    private fun renderUiState(state: PlaylistContract.State) {
        when (state.playlistState) {
            is PlaylistContract.TrackDialogState.Success -> {
                dataLoaded(
                    state.trackDialogState.menu.title,
                    state.trackDialogState.menu.items
                )
            }

            is PlaylistContract.TrackDialogState.Loading -> {
                showLoading()
            }

            is PlaylistContract.TrackDialogState.Idle -> {
                idleView()
            }

        }
    }



    class Dependency(
        val factory: PlaylistViewModel.Factory,

        )
}