package com.yes.musicplayer.equalizer.presentation.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.yes.core.presentation.BaseViewModel
import com.yes.musicplayer.equalizer.databinding.EqualizerBinding
import com.yes.musicplayer.equalizer.di.components.EqualizerComponent
import com.yes.musicplayer.equalizer.presentation.contract.EqualizerContract
import com.yes.musicplayer.equalizer.presentation.model.EqualizerStateUI
import com.yes.musicplayer.equalizer.presentation.vm.EqualizerViewModel
import kotlinx.coroutines.launch

class EqualizerScreen : Fragment() {
    interface DependencyResolver {
        fun getEqualizerScreenComponent(): EqualizerComponent
    }

    private val component by lazy {
        (requireActivity().application as DependencyResolver)
            .getEqualizerScreenComponent()
    }
    private val dependency by lazy {
        component.getDependency()
    }

    private lateinit var binding: ViewBinding
    private val binder by lazy {
        binding as EqualizerBinding
    }
    private val viewModel: BaseViewModel<EqualizerContract.Event,
            EqualizerContract.State,
            EqualizerContract.Effect> by viewModels {
        dependency.factory
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EqualizerBinding.inflate(inflater, container, false)
        //migration

        ///////////////////////
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        observeViewModel()
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
                        is EqualizerContract.Effect.UnknownException -> {
                            showError(com.yes.coreui.R.string.UnknownException)
                        }
                    }
                }
            }
        }
    }

    private fun setUpView() {

       /* binder.btnPlay.setOnClickListener {
            viewModel.setEvent(PlayerContract.Event.OnPlay)
        }*/

    }


    private fun renderUiState(state: EqualizerContract.State) {
        when (state.equalizer) {

            is EqualizerContract.EqualizerState.Idle -> {
                idleView()
            }

        }
    }

    private fun dataLoaded(playerState: EqualizerStateUI) {


    }

    private fun idleView() {

    }


    private fun showError(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }



    private fun hideBuffering() {

    }

    class Dependency(
        val factory: EqualizerViewModel.Factory,
        )
}
