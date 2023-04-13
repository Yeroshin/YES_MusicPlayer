package com.yes.trackdialogfeature.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.yes.core.presentation.BaseViewModel
import com.yes.coreui.BaseDialog
import com.yes.trackdialogfeature.R
import com.yes.trackdialogfeature.api.Dependency

import com.yes.trackdialogfeature.databinding.TrackDialogBinding

import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.MenuUiDomainMapper
import kotlinx.coroutines.launch


class TrackDialog(
    dependency: Dependency
) : BaseDialog() {
    private val trackDialogDependency = dependency as TrackDialogDependency

    ///////////////////////////

   // private val viewModelFactory: ViewModelProvider.Factory=trackDialogDependency.viewModelFactory
    private val adapter: TrackDialogAdapter=trackDialogDependency.adapter
    ///////////////////////////
    /* @Inject
     lateinit var viewModelFactory: TrackDialogViewModel.Factory*/
  /*  private val viewModel: TrackDialogViewModel by viewModels {
        TrackDialogViewModel.Factory(
            trackDialogDependency.getChildMenuUseCase,
            trackDialogDependency.menuUiDomainMapper
        )
    }*/
    private val viewModel= trackDialogDependency.viewModel

    /*  @Inject
      lateinit var adapter: TrackDialogAdapter*/
    override var layout: Int = R.layout.track_dialog
    /* override fun onAttach(context: Context) {
         super.onAttach(context)
        /* DaggerTrackDialogComponent.builder()
             .trackDialogModule(TrackDialogModule(getContext() as Activity))
             .build()
             .inject(this)*/

     }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TrackDialogBinding.inflate(inflater)
        super.onCreateView(inflater, container, savedInstanceState)
        /////////////////////
       // viewModel = ViewModelProvider(this, viewModelFactory)[TrackDialogViewModel::class.java]
       // val a=viewModel.test(1)
        /////////////////////
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupRecyclerView()
        viewModel.setEvent(TrackDialogContract.Event.OnItemClicked(0, ""))
    }
    private fun setupRecyclerView() {
        //adapter.setViewModel(viewModel)
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
                        is TrackDialogContract.TrackDialogState.Success -> {
                            (binding as TrackDialogBinding).recyclerViewContainer.playlist.text =
                                it.trackDialogState.title
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect {
                    when (it) {
                        is TrackDialogContract.Effect.UnknownException -> {
                            showToast("Unknown Exception")
                        }
                    }
                }
            }

        }
    }
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    class TrackDialogDependency(
       // val getChildMenuUseCase: GetChildMenuUseCase,
        val viewModel: BaseViewModel<TrackDialogContract.Event, TrackDialogContract.State, TrackDialogContract.Effect>,
        val menuUiDomainMapper: MenuUiDomainMapper,
       // val viewModelFactory: ViewModelProvider.Factory,
        val adapter: TrackDialogAdapter
    ):Dependency

}




