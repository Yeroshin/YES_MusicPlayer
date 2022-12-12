package com.yes.trackdiialogfeature.presentation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yes.coreui.UniversalDialog
import com.yes.trackdiialogfeature.R
import com.yes.trackdiialogfeature.databinding.TrackDialogBinding
import com.yes.trackdiialogfeature.di.components.DaggerTrackDialogComponent
import com.yes.trackdiialogfeature.di.module.TrackDialogModule
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class TrackDialog : UniversalDialog() {
    // private val viewModel: TrackDialogViewModel by viewModels()
    @Inject
    lateinit var viewModelFactory: TrackDialogViewModelFactory
    private lateinit var viewModel: TrackDialogViewModel

    @Inject
    lateinit var adapter: TrackDialogAdapter
    override var layout: Int = R.layout.track_dialog
    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerTrackDialogComponent.builder()
            .trackDialogModule(TrackDialogModule(getContext() as Activity))
            .build()
            .inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TrackDialogBinding.inflate(inflater)
        super.onCreateView(inflater, container, savedInstanceState)
        /////////////////////
        viewModel = ViewModelProvider(this, viewModelFactory)[TrackDialogViewModel::class.java]

        /////////////////////
        initAdapter()

        return binding.root
    }

    private fun initAdapter() {
        adapter.setViewModel(viewModel)
        val layoutManager = LinearLayoutManager(context)

        (binding as TrackDialogBinding).recyclerViewContainer.recyclerView.layoutManager =
            layoutManager
        (binding as TrackDialogBinding).recyclerViewContainer.recyclerView.adapter = adapter
        /////////////////

        //adapter.setItems(items)
        initObserver()
        viewModel.getMenu()
        // viewModel.getMenuItemContent(Menu("",""))
        /////////////////


        /////////////////
        (binding as TrackDialogBinding).buttons.cancelBtn.setOnClickListener {
            dismiss()
        }
        (binding as TrackDialogBinding).buttons.okBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun initObserver() {
        lifecycleScope.launchWhenStarted {
           /* viewModel.uiState.collectLatest { state ->
                when (state) {
                    is TrackDialogViewModelState.Success -> {
                        (binding as TrackDialogBinding).recyclerViewContainer.playlist.text =
                            state.menu.name
                        adapter.setItems(state.menu)
                    }
                    is TrackDialogViewModelState.IsLoading -> {}
                }
            }*/
             viewModel.uiState.onEach {
                 state->handleState(state)
             }
        }
    }
    private fun handleState(state:TrackDialogViewModelState){
        when (state) {
            is TrackDialogViewModelState.Success -> {
                (binding as TrackDialogBinding).recyclerViewContainer.playlist.text =
                    state.menu.name
                adapter.setItems(state.menu)
            }
            is TrackDialogViewModelState.IsLoading -> {}
            is TrackDialogViewModelState.Init -> {}
            }
        }
    }




