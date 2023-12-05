package com.yes.alarmclockfeature.presentation.ui

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.yes.alarmclockfeature.databinding.AlarmSetScreenBinding
import com.yes.alarmclockfeature.databinding.AlarmsListScreenBinding

class AlarmClockDialog: DialogFragment() {
    private lateinit var binding: ViewBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AlarmSetScreenBinding.inflate(inflater)
        dialog?.setContentView(binding.root)
        return binding.root
    }
}