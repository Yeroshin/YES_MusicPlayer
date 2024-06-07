package com.yes.alarmclockfeature.presentation.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.viewbinding.ViewBinding
import com.yes.alarmclockfeature.databinding.AlarmsListScreenBinding
import com.yes.alarmclockfeature.di.components.AlarmClockComponent
import com.yes.alarmclockfeature.domain.usecase.AddAlarmUseCase
import com.yes.alarmclockfeature.domain.usecase.DeleteAlarmUseCase
import com.yes.alarmclockfeature.domain.usecase.SetAlarmUseCase
import com.yes.alarmclockfeature.domain.usecase.SetNearestAlarmUseCase
import com.yes.alarmclockfeature.domain.usecase.SubscribeAlarmsUseCase
import com.yes.alarmclockfeature.presentation.contract.AlarmClockContract.*
import com.yes.alarmclockfeature.presentation.mapper.MapperUI
import com.yes.alarmclockfeature.presentation.model.AlarmUI
import com.yes.alarmclockfeature.presentation.vm.AlarmClockViewModel
import com.yes.core.presentation.ui.BaseViewModel


import com.yes.core.presentation.ui.ItemTouchHelperCallback
import com.yes.core.presentation.ui.UiState
import kotlinx.coroutines.launch

class AlarmsScreen : Fragment() {
    // interface DependencyResolver : BaseFragment.DependencyResolver

    interface DependencyResolver {
        fun getAlarmsScreenComponent(): AlarmClockComponent
    }

    private lateinit var binding: ViewBinding
    private val component by lazy {
        (requireActivity().application as DependencyResolver)
            .getAlarmsScreenComponent()
    }
    private val dependency: Dependency by lazy {
        component.getDependency()
    }
    private val viewModel: BaseViewModel<Event, State, Effect> by viewModels {
        dependency.factory
    }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setupView()
    }
    private fun checkBatteryOptimizations(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent();
            val packageName = context?.packageName;
            val pm = context?.getSystemService(Context.POWER_SERVICE) as PowerManager
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:$packageName"))
                startActivity(intent)
            }

        }
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

    private fun createBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        return AlarmsListScreenBinding.inflate(inflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = createBinding(inflater, container)
        return binding.root
    }
    private var alarmClockDialog: AlarmClockDialog? = null
    private fun setupView() {
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
        val deleteIconColor = ContextCompat.getColor(requireContext(), com.yes.coreui.R.color.branded_tint_72)
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
            checkBatteryOptimizations()
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

    private fun renderUiState(state: UiState) {
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

    private fun showEffect() {
        TODO("Not yet implemented")
    }

    class Dependency(
        val factory: ViewModelProvider.Factory
    )

    class Factory(
        private val mapper: MapperUI,
        private val addAlarmUseCase: AddAlarmUseCase,
        private val subscribeAlarmsUseCase: SubscribeAlarmsUseCase,
        private val deleteAlarmUseCase: DeleteAlarmUseCase,
        private val setAlarmUseCase: SetAlarmUseCase,
        private val setNearestAlarmUseCase: SetNearestAlarmUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AlarmClockViewModel(
                mapper,
                addAlarmUseCase,
                subscribeAlarmsUseCase,
                deleteAlarmUseCase,
                setAlarmUseCase,
                setNearestAlarmUseCase
            ) as T
        }
    }

}