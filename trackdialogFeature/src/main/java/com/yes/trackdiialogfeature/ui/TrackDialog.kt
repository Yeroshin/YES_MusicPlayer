package com.yes.trackdiialogfeature.ui

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
import javax.inject.Inject

class TrackDialog : UniversalDialog() {
    // private val viewModel: TrackDialogViewModel by viewModels()
    @Inject
    lateinit var vmFactory: TrackDialogViewModelFactory
    private lateinit var vm: TrackDialogViewModel

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
        vm = ViewModelProvider(this, vmFactory)[TrackDialogViewModel::class.java]
        adapter.setViewModel(vm)
        /////////////////////
        val layoutManager = LinearLayoutManager(context)

        (binding as TrackDialogBinding).recyclerViewContainer.recyclerView.layoutManager =
            layoutManager
        (binding as TrackDialogBinding).recyclerViewContainer.recyclerView.adapter = adapter
        /////////////////

        //adapter.setItems(items)
        initObserver()
        vm.getMenuItemContent(null)
        /////////////////


        /////////////////
        (binding as TrackDialogBinding).buttons.cancelBtn.setOnClickListener {
            dismiss()
        }
        (binding as TrackDialogBinding).buttons.okBtn.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    fun initObserver() {
        lifecycleScope.launchWhenStarted {
            vm.uiState.collectLatest {
                (binding as TrackDialogBinding).recyclerViewContainer.playlist.text = it.name
                adapter.setItems(it)
            }
        }

    }


}