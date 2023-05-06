package com.yes.trackdialogfeature.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.yes.core.presentation.IBaseViewModel
import com.yes.coreui.BaseDialog
import com.yes.trackdialogfeature.R
import com.yes.trackdialogfeature.api.Dependency
import com.yes.trackdialogfeature.databinding.TrackDialogBinding
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.model.MenuUi
import kotlinx.coroutines.launch




class TrackDialog(
    dependency: Dependency
) : BaseDialog() {
    private val binder by lazy {
        binding as TrackDialogBinding
    }
    override val layout: Int = R.layout.track_dialog
    private val trackDialogDependency = dependency as TrackDialogDependency
//val t=R.layout.
    ///////////////////////////

    // private val viewModelFactory: ViewModelProvider.Factory=trackDialogDependency.viewModelFactory
    private val adapter: TrackDialogAdapter = trackDialogDependency.adapter

    ///////////////////////////
    /* @Inject
     lateinit var viewModelFactory: TrackDialogViewModel.Factory*/
    /*  private val viewModel: TrackDialogViewModel by viewModels {
          TrackDialogViewModel.Factory(
              trackDialogDependency.getChildMenuUseCase,
              trackDialogDependency.menuUiDomainMapper
          )
      }*/
    private val viewModel = trackDialogDependency.viewModel

    /*  @Inject
      lateinit var adapter: TrackDialogAdapter*/
    //  override var layout: Int = R.layout.track_dialog
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
        //_binding = TrackDialogBinding.inflate(inflater)
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
        val myTextView = R.id.recyclerViewContainer
        val y = com.yes.coreui.R.id.dialogTitle
        val t = 10
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)


        binder.recyclerViewContainer.recyclerView.layoutManager = layoutManager
        binder.recyclerViewContainer.recyclerView.adapter = adapter
        binder.buttons.cancelBtn.setOnClickListener {
            dismiss()
        }
        binder.buttons.okBtn.setOnClickListener {
            dismiss()
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
                        is TrackDialogContract.Effect.UnknownException -> {
                            showError(com.yes.coreui.R.string.UnknownException )
                        }
                    }
                }
            }

        }
    }
    fun renderUiState(state:TrackDialogContract.State){
        when (state.trackDialogState) {
            is TrackDialogContract.TrackDialogState.Success -> {
                dataLoaded(
                    state.trackDialogState.title,
                    state.trackDialogState.menu.items
                )
            }
            is TrackDialogContract.TrackDialogState.Loading -> {
                showLoading()
            }
            is TrackDialogContract.TrackDialogState.Idle -> {
                idleView()
            }
        }
    }
    private fun idleView() {
        binder.recyclerViewContainer.dialogTitle.text = ""
        binder.recyclerViewContainer.progressBar.visibility = GONE
        binder.recyclerViewContainer.disableView.visibility = GONE
    }

    private fun showLoading() {
        binder.recyclerViewContainer.progressBar.visibility = VISIBLE
        binder.recyclerViewContainer.disableView.visibility = VISIBLE
    }

    private fun dataLoaded(title: String, items: List<MenuUi.MediaItem>) {

        binder.recyclerViewContainer.dialogTitle.text = title
        adapter.setItems(items)
        binder.recyclerViewContainer.progressBar.visibility = GONE
        binder.recyclerViewContainer.disableView.visibility = GONE
    }

    private fun showError(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    class TrackDialogDependency(
        val viewModel: IBaseViewModel<TrackDialogContract.Event, TrackDialogContract.State, TrackDialogContract.Effect>,
        val adapter: TrackDialogAdapter
    ) : Dependency

}




