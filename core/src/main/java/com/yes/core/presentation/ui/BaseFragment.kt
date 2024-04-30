package com.yes.core.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.launch

abstract class BaseFragment: Fragment() {

    protected lateinit var binding: ViewBinding
    abstract val dependency: BaseDependency
    protected val viewModel: BaseViewModel<UiEvent,
            UiState,
            UiEffect> by viewModels {
        dependency.viewModelFactory
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = createBinding(inflater, container)
        return binding.root
    }
    abstract fun createBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setUpView()
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
                    showEffect()
                }
            }
        }
    }
    protected abstract fun setUpView()
    protected abstract fun renderUiState(state: UiState)
    protected abstract fun showEffect()


}