package com.yes.alarmclockfeature.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment.STYLE_NO_FRAME
import androidx.viewbinding.ViewBinding
import com.yes.alarmclockfeature.databinding.AlarmsListScreenBinding
import com.yes.alarmclockfeature.presentation.contract.AlarmClockContract
import com.yes.alarmclockfeature.presentation.ui.datepicker.DatePickerManager
import com.yes.alarmclockfeature.presentation.vm.AlarmClockViewModel
import com.yes.core.presentation.BaseDependency

import com.yes.core.presentation.BaseFragment
import com.yes.core.presentation.MusicService
import com.yes.core.presentation.UiState

class AlarmsScreen : BaseFragment() {
    interface DependencyResolver : BaseFragment.DependencyResolver


    private val binder by lazy {
        binding as AlarmsListScreenBinding
    }
    private val alarmsScreenAdapter by lazy {
        AlarmsScreenAdapter()
    }

    /* private val alarmClockDialog by lazy {
         AlarmClockDialog(
             { date, repeating ->
                 viewModel.setEvent(
                     AlarmClockContract.Event.OnAddAlarm(
                         date,
                         repeating
                     )
                 )
                 dismissAlarmClockDialog()
             },
             {
                 dismissAlarmClockDialog()
             }
         )
     }*/

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        return AlarmsListScreenBinding.inflate(inflater)
    }

    private var alarmClockDialog: AlarmClockDialog? = null
    override fun setupView() {

        binder.alarmsList.adapter = alarmsScreenAdapter
        binder.addAlarmButton.setOnClickListener {
            alarmClockDialog = AlarmClockDialog(
                { date, repeating ->
                    viewModel.setEvent(
                        AlarmClockContract.Event.OnAddAlarm(
                            date,
                            repeating
                        )
                    )
                    dismissAlarmClockDialog()
                },
                {
                    dismissAlarmClockDialog()
                }
            )
            alarmClockDialog?.show(childFragmentManager, null)
        }

    }

    private fun dismissAlarmClockDialog() {
        // alarmClockDialog.dismiss()
        alarmClockDialog?.dismiss()
        alarmClockDialog = null
    }

    override fun renderUiState(state: UiState) {

    }

    override fun showEffect() {
        TODO("Not yet implemented")
    }

}