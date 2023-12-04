package com.yes.alarmclockfeature.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.yes.alarmclockfeature.databinding.AlarmsListScreenBinding

import com.yes.core.presentation.BaseFragment
import com.yes.core.presentation.UiState

class AlarmsScreen: BaseFragment() {
    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        return AlarmsListScreenBinding.inflate(inflater)
    }

    override fun setupView() {

    }

    override fun renderUiState(state: UiState) {
        TODO("Not yet implemented")
    }

    override fun showEffect() {
        TODO("Not yet implemented")
    }
}