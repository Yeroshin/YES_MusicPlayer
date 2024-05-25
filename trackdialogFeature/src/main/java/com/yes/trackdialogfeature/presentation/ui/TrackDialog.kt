package com.yes.trackdialogfeature.presentation.ui

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.yes.core.presentation.ui.BaseDialog
import com.yes.core.presentation.ui.BaseViewModel
import com.yes.trackdialogfeature.R
import com.yes.trackdialogfeature.databinding.TrackDialogBinding
import com.yes.trackdialogfeature.di.component.TrackDialogComponent
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.model.MenuUi
import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import kotlinx.coroutines.launch


class TrackDialog : BaseDialog() {
    interface DependencyResolver {
        fun getTrackDialogComponent(): TrackDialogComponent
    }

    private val component by lazy {
        (requireActivity().application as DependencyResolver)
            .getTrackDialogComponent()
    }
    private val dependency: Dependency by lazy {
        component.getDependency()
    }
    private val binder by lazy {
        binding as TrackDialogBinding
    }
  //  override val layout: Int = R.layout.track_dialog
    private val adapter: TrackDialogAdapter = TrackDialogAdapter()
    private val viewModel: BaseViewModel<TrackDialogContract.Event,
            TrackDialogContract.State,
            TrackDialogContract.Effect> by viewModels {
        dependency.factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TrackDialogBinding.inflate(inflater)
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupRecyclerView()
        viewModel.setEvent(
            TrackDialogContract.Event.OnItemClicked()
        )
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
            if (!isChecked) {
                binder.recyclerViewContainer.disableView.visibility = GONE
                binder.networkPath.isEnabled = false
                binder.buttons.okBtn.isClickable= true
                binder.buttons.okBtn.isPressed = false
            } else {
                binder.recyclerViewContainer.disableView.visibility = VISIBLE
                binder.networkPath.isEnabled = true
                binder.buttons.okBtn.isClickable= false
                binder.buttons.okBtn.isPressed = true
                viewModel.setEvent(
                    TrackDialogContract.Event.OnCheckPathIsAvailable(binder.networkPath.text.toString())
                )
            }
        }
        binder.networkPath.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    val i = 0
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    viewModel.setEvent(
                        TrackDialogContract.Event.OnCheckPathIsAvailable(binder.networkPath.text.toString())
                    )
                }
            }
        )
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
                            showError(com.yes.coreui.R.string.UnknownException)
                        }

                        TrackDialogContract.Effect.UnknownException -> TODO()
                    }
                }
            }
        }
    }

    private fun renderUiState(state: TrackDialogContract.State) {
        when (state.trackDialogState) {
            is TrackDialogContract.TrackDialogState.Success -> {
                dataLoaded(
                    state.trackDialogState.menu.title,
                    state.trackDialogState.menu.items
                )
            }

            is TrackDialogContract.TrackDialogState.Loading -> {
                showLoading()
            }

            is TrackDialogContract.TrackDialogState.Idle -> {
                idleView()
            }

            is TrackDialogContract.TrackDialogState.Dismiss -> {
                dismiss()
            }

            is TrackDialogContract.TrackDialogState.NetworkPathAvailable -> setNetworkPathStatus(
                state.trackDialogState.status
            )
        }
    }
    fun tmp(){
        val screeSize1 = Point()
        val disp: Display = (requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        disp.getSize(screeSize1)
        binding.root.minimumWidth = (screeSize1.x * 0.9f).toInt()
        binding.root.minimumHeight = (screeSize1.y * 0.9f).toInt()
    }

    private fun setNetworkPathStatus(status: Boolean) {
        binder.buttons.okBtn.isClickable= status
        binder.buttons.okBtn.isPressed = !status
        binder.networkPath.isActivated=status
        tmp()
    }

    private fun idleView() {
        binder.recyclerViewContainer.progressBar.visibility = GONE
        binder.recyclerViewContainer.disableView.visibility = GONE
        tmp()
    }

    private fun showLoading() {
        println("loading")
        binder.recyclerViewContainer.progressBar.visibility = VISIBLE
        binder.recyclerViewContainer.disableView.visibility = VISIBLE
        tmp()
    }

    private fun dataLoaded(title: String, items: List<MenuUi.ItemUi>) {
        println("loaded")

        binder.recyclerViewContainer.progressBar.visibility = GONE
        binder.recyclerViewContainer.disableView.visibility = GONE
        binder.recyclerViewContainer.dialogTitle.text = title
        adapter.setItems(items)
        tmp()
    }

    private fun showError(message: Int) {
        binder.recyclerViewContainer.progressBar.visibility = GONE
        binder.recyclerViewContainer.disableView.visibility = GONE
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }

    class Dependency(
        val factory: TrackDialogViewModel.Factory,
    )
}




