package com.yes.settings.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.viewbinding.ViewBinding
import com.yes.core.presentation.model.Theme
import com.yes.core.presentation.ui.BaseDependency
import com.yes.core.presentation.ui.BaseFragment
import com.yes.core.presentation.ui.UiState
import com.yes.settings.databinding.SettingsBinding
import com.yes.settings.presentation.contract.SettingsContract


class SettingsScreen : BaseFragment() {
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
        return SettingsBinding.inflate(inflater, container, false)
    }

   /* private val themeRadioGroupOnCheckedChangeListener=
        RadioGroup.OnCheckedChangeListener { _, checkedId ->
            binder.themeRadioGroup.setOnCheckedChangeListener(null)
            when (checkedId) {

                binder.darkThemeRadioButton.id -> {
                    viewModel.setEvent(
                        SettingsContract.Event.OnSetTheme(Theme.DarkTheme)
                    )
                }

                binder.lightThemeRadioButton.id -> {
                    viewModel.setEvent(
                        SettingsContract.Event.OnSetTheme(Theme.LightTheme)
                    )
                }
            }
        }*/
    override fun setUpView() {

      /*  binder.themeRadioGroup.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {

                binder.darkThemeRadioButton.id -> {
                    viewModel.setEvent(
                        SettingsContract.Event.OnSetTheme(Theme.DarkTheme)
                    )
                }

                binder.lightThemeRadioButton.id -> {
                    renderUiState(
                        SettingsContract.State(
                            SettingsContract.SettingsState.Success,
                            Theme.LightTheme
                        )
                    )
                    viewModel.setEvent(
                        SettingsContract.Event.OnSetTheme(Theme.LightTheme)
                    )
                }
            }
        }*/
    }

    override fun renderUiState(state: UiState) {
        state as SettingsContract.State
        when (state.state) {
            is SettingsContract.SettingsState.Success -> {
                dataLoaded(state)
            }

            is SettingsContract.SettingsState.Idle -> {}
            is SettingsContract.SettingsState.Recreate -> {
                recreate()
            }
        }
    }
    private fun recreate(){
        viewModel.setEvent(
            SettingsContract.Event.OnIdle
        )
        activity?.recreate()
    }

    private fun dataLoaded(state: SettingsContract.State) {
        state.theme?.let {
            binder.themeRadioGroup.setOnCheckedChangeListener(null)

            when (it) {
                Theme.DarkTheme -> {
                     binder.darkThemeRadioButton.isChecked=true
                  //  activity?.setTheme(com.yes.coreui.R.style.Theme_YESActivityDark)
                  //  activity?.recreate()
                }

                Theme.LightTheme -> {
                      binder.lightThemeRadioButton.isChecked=true

                }
            }
            val themeRadioGroupOnCheckedChangeListener=
                RadioGroup.OnCheckedChangeListener { _, checkedId ->
                    when (checkedId) {

                        binder.darkThemeRadioButton.id -> {
                            viewModel.setEvent(
                                SettingsContract.Event.OnSetTheme(Theme.DarkTheme)
                            )
                        }

                        binder.lightThemeRadioButton.id -> {
                            viewModel.setEvent(
                                SettingsContract.Event.OnSetTheme(Theme.LightTheme)
                            )
                        }
                    }
                }
            binder.themeRadioGroup.setOnCheckedChangeListener(
                themeRadioGroupOnCheckedChangeListener
            )
        }

    }

    override fun showEffect() {

    }
}