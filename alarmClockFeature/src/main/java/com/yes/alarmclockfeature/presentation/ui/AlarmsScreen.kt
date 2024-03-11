package com.yes.alarmclockfeature.presentation.ui

import android.content.Intent
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.viewbinding.ViewBinding
import com.yes.alarmclockfeature.databinding.AlarmsListScreenBinding
import com.yes.alarmclockfeature.presentation.contract.AlarmClockContract
import com.yes.alarmclockfeature.presentation.contract.AlarmClockContract.*
import com.yes.alarmclockfeature.presentation.model.AlarmUI
import com.yes.core.data.dataSource.PlayerDataSource

import com.yes.core.presentation.BaseFragment
import com.yes.core.presentation.ItemTouchHelperCallback
import com.yes.core.presentation.UiState

class AlarmsScreen : BaseFragment() {
    interface DependencyResolver : BaseFragment.DependencyResolver


    private val binder by lazy {
        binding as AlarmsListScreenBinding
    }
    private val adapter by lazy {
        AlarmsScreenAdapter { alarm ->
            viewModel.setEvent(
                Event.OnAlarmEnabled(
                    alarm
                )
            )
        }


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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
        }

        val onSwipeCallback: (position: Int) -> Unit = { position ->
            val deletedItem = adapter.getItem(position)
            binder.alarmsList.layoutManager?.removeViewAt(position)
            viewModel.setEvent(
                Event.OnDeleteAlarm(
                    deletedItem
                )
            )


        }
        val onItemMove: (fromPosition: Int, toPosition: Int) -> Unit = { fromPosition, toPosition ->
            adapter.moveItem(fromPosition, toPosition)
        }
        val deleteIconDrawable = ContextCompat.getDrawable(
            requireContext(),
            com.yes.coreui.R.drawable.trash_can_outline,
        )
        val deleteIconColor = ContextCompat.getColor(requireContext(), com.yes.coreui.R.color.tint)
        val backgroundColor = ContextCompat.getColor(
            requireContext(),
            com.yes.coreui.R.color.button_centerColor_pressed
        )


        val itemTouchHelperCallback = ItemTouchHelperCallback(
            enableSwipeToDelete = true,
            enableDragAndDrop = true,
            onSwipeCallback = onSwipeCallback,
            onItemMove = onItemMove,
            deleteIconDrawable = deleteIconDrawable,
            deleteIconColor = deleteIconColor,
            backgroundColor = backgroundColor,
            onDraggedItemDrop = null
        )

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(
            binder.alarmsList
        )
        val snapHelper = LinearSnapHelper()
        // snapHelper.attachToRecyclerView(binder.alarmsList)
        binder.alarmsList.layoutManager = LinearLayoutManager(context)
        binder.alarmsList.adapter = adapter
        binder.addAlarmButton.setOnClickListener {
            alarmClockDialog = AlarmClockDialog(
                { date, repeating ->
                    viewModel.setEvent(
                        Event.OnAddAlarm(
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
        alarmClockDialog?.dismiss()
        alarmClockDialog = null
    }

    override fun renderUiState(state: UiState) {
        when (val alarmClockState = (state as State).alarmClockState) {
            is AlarmClockState.Success -> {
                setItems(
                    alarmClockState.items
                )
            }

            is AlarmClockState.Idle -> {}
        }
    }

    private fun setItems(items: List<AlarmUI>) {
        adapter.setItems(items)
    }

    override fun showEffect() {
        TODO("Not yet implemented")
    }

}