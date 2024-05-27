package com.yes.playlistdialogfeature.presentation.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.yes.core.presentation.ui.BaseDialog
import com.yes.core.presentation.ui.BaseViewModel
import com.yes.core.presentation.ui.ItemTouchHelperCallback
import com.yes.playlistdialogfeature.R
import com.yes.playlistdialogfeature.databinding.PlaylistDialogBinding
import com.yes.playlistdialogfeature.di.component.PlayListDialogComponent
import com.yes.playlistdialogfeature.presentation.contract.PlayListDialogContract
import com.yes.playlistdialogfeature.presentation.model.ItemUi
import com.yes.playlistdialogfeature.presentation.vm.PlayListDialogViewModel
import kotlinx.coroutines.launch


class PlayListDialog: BaseDialog(),SwipeToDeleteCallback.Callback{
    interface DependencyResolver {
        fun getPlayListDialogComponent(): PlayListDialogComponent
    }
    private val component by lazy {
        (requireActivity().application as DependencyResolver)
            .getPlayListDialogComponent()
    }
    private val dependency: Dependency by lazy {
        component.getDependency()
    }
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
     /*   val swipeToDeleteCallback = SwipeToDeleteCallback(this)*/
        val onSwipeCallback: (position: Int) -> Unit = { position ->
            binder.recyclerViewContainer.recyclerView.layoutManager?.removeViewAt(position)
            viewModel.setEvent(
                PlayListDialogContract.Event.OnDeletePlaylist(
                    adapter.getItems()[position]
                )
            )


        }
        val deleteIconDrawable = ContextCompat.getDrawable(
            requireContext(),
            com.yes.coreui.R.drawable.trash_can_outline,
        )
        val deleteIconColor = ContextCompat.getColor(requireContext(), com.yes.coreui.R.color.branded_tint_72)
        val backgroundColor = ContextCompat.getColor(
            requireContext(),
            com.yes.coreui.R.color.button_centerColor_pressed
        )
        val itemTouchHelperCallback = ItemTouchHelperCallback(
            enableSwipeToDelete = true,
            enableDragAndDrop = false,
            onSwipeCallback = onSwipeCallback,
            deleteIconDrawable = deleteIconDrawable,
            deleteIconColor = deleteIconColor,
            backgroundColor = backgroundColor,
        )
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
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
            hideKeyboardFrom(requireContext(), binder.playlistName)
        }
    }
    private fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
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

                        is PlayListDialogContract.Effect.PlaylistsSizeLimit -> {
                            showError(com.yes.coreui.R.string.PlaylistsSizeLimit)
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
        binder.recyclerViewContainer.dialogTitle.text=items.find { it.current }?.name
        binder.recyclerViewContainer.progressBar.visibility = GONE
        binder.recyclerViewContainer.disableView.visibility = INVISIBLE
    }
    private fun showLoading() {
        binder.recyclerViewContainer.progressBar.visibility = VISIBLE
        binder.recyclerViewContainer.disableView.visibility = VISIBLE
    }
    private fun idleView() {
        binder.recyclerViewContainer.progressBar.visibility = GONE
        binder.recyclerViewContainer.disableView.visibility = INVISIBLE
    }
    private fun showError(message: Int) {
        binder.recyclerViewContainer.progressBar.visibility = GONE
        binder.recyclerViewContainer.disableView.visibility = INVISIBLE
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    class Dependency(
        val factory: PlayListDialogViewModel.Factory,
    )

    override fun deleteItem(position: Int) {
        viewModel.setEvent(
            PlayListDialogContract.Event.OnDeletePlaylist(
                adapter.getItems()[position]
            )
        )

    }
}
