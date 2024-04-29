package com.yes.settings.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.yes.core.presentation.BaseDependency
import com.yes.core.presentation.BaseFragment
import com.yes.core.presentation.UiState
import com.yes.settings.databinding.SettingsBinding

class SettingsScreen: BaseFragment() {
    interface DependencyResolver {
        fun resolveSettingsDependency(): BaseDependency
    }
    override val dependency by lazy {
        (requireActivity().application as DependencyResolver)
            .resolveSettingsDependency()
    }
    private val binder by lazy {
        binding as SettingsBinding
    }

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        return  SettingsBinding.inflate(inflater, container, false)
    }

    override fun setUpView() {

    }

    override fun renderUiState(state: UiState) {
        TODO("Not yet implemented")
    }

    override fun showEffect() {

    }
}