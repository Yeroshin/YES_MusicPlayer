package com.yes.core.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseActivity: AppCompatActivity() {



    abstract val dependency: ActivityDependency
    protected val viewModel: BaseViewModel<UiEvent,
            UiState,
            UiEffect> by viewModels {
        dependency.viewModelFactory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        observeViewModel()
        setUpView()
        super.onCreate(savedInstanceState)

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
    protected abstract fun renderUiState(state: UiState)
    protected abstract fun showEffect()
    protected abstract fun setUpView()
}