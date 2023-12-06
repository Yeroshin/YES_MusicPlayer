package com.yes.alarmclockfeature.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment.STYLE_NO_FRAME
import androidx.viewbinding.ViewBinding
import com.yes.alarmclockfeature.databinding.AlarmsListScreenBinding

import com.yes.core.presentation.BaseFragment
import com.yes.core.presentation.UiState

class AlarmsScreen: BaseFragment() {
    interface DependencyResolver:BaseFragment.DependencyResolver
    private val binder by lazy {
        binding as AlarmsListScreenBinding
    }
    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        return AlarmsListScreenBinding.inflate(inflater)
    }

    override fun setupView() {

        binder.alarmsList.adapter= AlarmsScreenAdapter()
        binder.addAlarmButton.setOnClickListener {
            AlarmClockDialog().show(childFragmentManager,null)
        }

    }

    override fun renderUiState(state: UiState) {

    }

    override fun showEffect() {
        TODO("Not yet implemented")
    }

}