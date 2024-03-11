package com.yes.musicplayer.equalizer.presentation.ui

import android.content.Context
import android.media.AudioManager
import android.media.audiofx.AudioEffect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.RadioGroup.OnCheckedChangeListener
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.yes.core.presentation.BaseViewModel
import com.yes.musicplayer.equalizer.R
import com.yes.musicplayer.equalizer.databinding.EqualizerBinding
import com.yes.musicplayer.equalizer.di.components.EqualizerComponent
import com.yes.musicplayer.equalizer.presentation.contract.EqualizerContract
import com.yes.musicplayer.equalizer.presentation.model.EqualizerUI
import com.yes.musicplayer.equalizer.presentation.vm.EqualizerViewModel
import kotlinx.coroutines.launch

class EqualizerScreen : Fragment() ,CircularSeekBar.OnProgressChangeListener{
    interface DependencyResolver {
        fun getEqualizerScreenComponent(): EqualizerComponent
    }

    private val component by lazy {
        (requireActivity().application as DependencyResolver)
            .getEqualizerScreenComponent()
    }
    private val dependency by lazy {
        component.getDependency()
    }

    private lateinit var binding: ViewBinding
    private val binder by lazy {
        binding as EqualizerBinding
    }
    private val viewModel: BaseViewModel<EqualizerContract.Event,
            EqualizerContract.State,
            EqualizerContract.Effect> by viewModels {
        dependency.factory
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EqualizerBinding.inflate(inflater, container, false)
        //migration

        ///////////////////////
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        observeViewModel()
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
                    when (it) {
                        is EqualizerContract.Effect.UnknownException -> {
                            showError(com.yes.coreui.R.string.UnknownException)
                        }
                    }
                }
            }
        }
    }
    private val verticalSeekBarChangeListener=object :VerticalSeekBar.OnVerticalSeekBarChangeListener{
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            if (fromUser) {
                viewModel.setEvent(
                    EqualizerContract.Event.OnEqualizerValue(
                        seekBar.tag as Int,
                        progress,
                        seekBar.max
                    )
                )
            }
        }
    }

    private fun setUpView() {
        //////////////////
        val audioManager = requireActivity().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val effects = AudioEffect.queryEffects()
        for (effectDescriptor in effects) {
            if (effectDescriptor.type == AudioEffect.EFFECT_TYPE_EQUALIZER) {
                Log.d("Equalizer", "Equalizer is available")
                // Здесь можно выполнить дополнительные действия, если эквалайзер доступен
            }
        }
        /////////////////
        binder.one.tag=0
        binder.one.setOnVerticalSeekBarChangeListener(verticalSeekBarChangeListener)
       // binder.one.setOnSeekBarChangeListener(seekBarChangeListener)
        binder.two.tag=1
        binder.two.setOnVerticalSeekBarChangeListener(verticalSeekBarChangeListener)
        // binder.two.setOnSeekBarChangeListener(seekBarChangeListener)
        binder.three.tag=2
        binder.three.setOnVerticalSeekBarChangeListener(verticalSeekBarChangeListener)
        //binder.three.setOnSeekBarChangeListener(seekBarChangeListener)
        binder.four.tag=3
        binder.four.setOnVerticalSeekBarChangeListener(verticalSeekBarChangeListener)
      //  binder.four.setOnSeekBarChangeListener(seekBarChangeListener)
        binder.five.tag=4
        binder.five.setOnVerticalSeekBarChangeListener(verticalSeekBarChangeListener)
      //  binder.five.setOnSeekBarChangeListener(seekBarChangeListener)

        binder.equalizerSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setEvent(
                EqualizerContract.Event.OnEqualizerEnabled(
                    isChecked
                )
            )
        }
        binder.circularSeekBar.setMinValue(144)
        binder.circularSeekBar.setMaxValue(396)
     //   binder.circularSeekBar.setProgressValue(250)
        binder.loudnessSwitch.setOnCheckedChangeListener { _, isChecked ->
          //  binder.circularSeekBar.isEnabled=isChecked
            viewModel.setEvent(
                EqualizerContract.Event.OnLoudnessEnhancerEnabled(
                    isChecked
                )
            )
        }
        binder.circularSeekBar.setOnProgressChangeListener(this)

    }


    private fun renderUiState(state: EqualizerContract.State) {
        when (state.state) {

            is EqualizerContract.EqualizerState.Idle -> {
                idleView()
            }

            is EqualizerContract.EqualizerState.Success -> {
                dataInit(state.state.equalizer)
            }
        }
    }


    private val itemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            // val selectedItem = parent?.getItemAtPosition(position)
            viewModel.setEvent(
                EqualizerContract.Event.OnPresetSelected(
                    position.toShort()
                )
            )
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // Обработка события, когда ничего не выбрано
        }
    }

    private fun dataInit(equalizer: EqualizerUI) {
        equalizer.presetsNames?.let { presets ->
            ArrayAdapter(
                requireContext(),
                R.layout.item_presets_spinner,
                presets
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binder.presetsSpinner.adapter = adapter
                binder.presetsSpinner.onItemSelectedListener = itemSelectedListener
            }
        }
        equalizer.currentPreset?.let {
            binder.presetsSpinner.setSelection(it)
        }
        equalizer.equalizerValues?.let {
            binder.one.setValue(it[0])
            binder.two.setValue(it[1])
            binder.three.setValue(it[2])
            binder.four.setValue(it[3])
            binder.five.setValue(it[4])

            binder.oneValue.text = it[0].toString()
            binder.twoValue.text = it[1].toString()
            binder.threeValue.text = it[2].toString()
            binder.fourValue.text = it[3].toString()
            binder.fiveValue.text = it[4].toString()

        }
        equalizer.bandsLevelRange?.let {
            binder.one.max = it
            binder.two.max = it
            binder.three.max = it
            binder.four.max = it
            binder.five.max = it
        }
        equalizer.equalizerEnabled?.let {
            binder.equalizerSwitch.isChecked=it
            binder.one.isEnabled = it
            binder.two.isEnabled = it
            binder.three.isEnabled = it
            binder.four.isEnabled = it
            binder.five.isEnabled = it
        }

        equalizer.loudnessEnhancerEnabled?.let {
            binder.loudnessSwitch.isChecked=it
            binder.circularSeekBar.isEnabled=it
        }
        equalizer.loudnessEnhancerValue?.let {

            binder.circularSeekBar.setProgressValue(it)
        }
    }

    private fun idleView() {

    }


    private fun showError(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    private fun hideBuffering() {

    }

    class Dependency(
        val factory: EqualizerViewModel.Factory,
    )

    override fun onProgressChanged(progress: Int) {
        viewModel.setEvent(
            EqualizerContract.Event.OnLoudnessEnhancerTargetGain(
                progress
            )
        )
    }
}
