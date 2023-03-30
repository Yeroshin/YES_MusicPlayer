package com.yes.trackdialogfeature.presentation.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.yes.coreui.BaseDialog
import com.yes.trackdialogfeature.R
import com.yes.trackdialogfeature.databinding.TrackDialogBinding
import com.yes.trackdialogfeature.di.components.DaggerTrackDialogComponent

import com.yes.trackdialogfeature.di.module.TrackDialogModule
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class TrackDialog : BaseDialog() {

    @Inject
    lateinit var viewModelFactory: TrackDialogViewModel.Factory
    private lateinit var viewModel: TrackDialogViewModel

    @Inject
    lateinit var adapter: TrackDialogAdapter
    override var layout: Int = R.layout.track_dialog
    override fun onAttach(context: Context) {
        super.onAttach(context)
       /* DaggerTrackDialogComponent.builder()
            .trackDialogModule(TrackDialogModule(getContext() as Activity))
            .build()
            .inject(this)*/

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupRecyclerView()
        viewModel.setEvent(TrackDialogContract.Event.OnItemClicked(0,""))
    }



    private fun setupRecyclerView() {
        adapter.setViewModel(viewModel)
        val layoutManager = LinearLayoutManager(context)

        (binding as TrackDialogBinding).recyclerViewContainer.recyclerView.layoutManager =
            layoutManager
        (binding as TrackDialogBinding).recyclerViewContainer.recyclerView.adapter = adapter



        /////////////////
        (binding as TrackDialogBinding).buttons.cancelBtn.setOnClickListener {
            dismiss()
        }
        (binding as TrackDialogBinding).buttons.okBtn.setOnClickListener {
            dismiss()
        }
        /////////////////

        //adapter.setItems(items)

       // viewModel.onGetMenu()
        // viewModel.getMenuItemContent(Menu("",""))
        /////////////////
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it.trackDialogState) {
                        is TrackDialogContract.TrackDialogState.Success ->{
                            (binding as TrackDialogBinding).recyclerViewContainer.playlist.text = it.trackDialogState.title
                            adapter.setItems(it.trackDialogState.menu.items)
                        }
                        is TrackDialogContract.TrackDialogState.Loading -> {

                        }
                        is TrackDialogContract.TrackDialogState.Idle -> {

                        }
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.effect.collect {
                when (it) {
                    is TrackDialogContract.Effect.UnknownException -> {
                        showToast("Unknown Exception")
                    }
                }
            }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}




