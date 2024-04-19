package com.yes.musicplayer.equalizer.presentation.ui

import android.media.audiofx.AudioEffect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.yes.core.presentation.BaseDependency
import com.yes.core.presentation.BaseFragment
import com.yes.core.presentation.UiState
import com.yes.musicplayer.equalizer.R
import com.yes.musicplayer.equalizer.databinding.EqualizerBinding
import com.yes.musicplayer.equalizer.presentation.contract.EqualizerContract

class EqualizerScreen : BaseFragment(), CircularSeekBar.OnProgressChangeListener {
    interface DependencyResolver {
        fun resolveEqualizerScreenComponent(): BaseDependency
    }


    override val dependency by lazy {
        (requireActivity().application as DependencyResolver)
            .resolveEqualizerScreenComponent()
    }

    private val binder by lazy {
        binding as EqualizerBinding
    }


    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        return EqualizerBinding.inflate(inflater, container, false)

    }

    override fun showEffect() {
        TODO("Not yet implemented")
    }

    private val verticalSeekBarChangeListener =
        object : VerticalSeekBar.OnVerticalSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                println("onStartTrackingTouch")

                if (fromUser) {

                    val seekBarValues = IntArray(5)
                    seekBarValues[0] = binder.one.progress
                    seekBarValues[1] = binder.two.progress
                    seekBarValues[2] = binder.three.progress
                    seekBarValues[3] = binder.four.progress
                    seekBarValues[4] = binder.five.progress
                    viewModel.setEvent(
                        EqualizerContract.Event.OnEqualizerValue(
                            seekBar.tag as Int,
                            progress,
                            seekBar.max,
                            seekBarValues
                        )
                    )
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar, progress: Int) {
                println("onStopTrackingTouch")
                val seekBarValues = IntArray(5)
                seekBarValues[0] = binder.one.progress
                seekBarValues[1] = binder.two.progress
                seekBarValues[2] = binder.three.progress
                seekBarValues[3] = binder.four.progress
                seekBarValues[4] = binder.five.progress
                viewModel.setEvent(
                    EqualizerContract.Event.OnEqualizerValueSet(
                        seekBar.tag as Int,
                        progress,
                        seekBar.max,
                        seekBarValues
                    )
                )
            }
        }

    override fun setUpView() {
        //////////////////
        // val audioManager = requireActivity().getSystemService(Context.AUDIO_SERVICE) as AudioManager
      /*  val effects = AudioEffect.queryEffects()
        for (effectDescriptor in effects) {
            if (effectDescriptor.type == AudioEffect.EFFECT_TYPE_EQUALIZER) {
                Log.d("Equalizer", "Equalizer is available")
                // Здесь можно выполнить дополнительные действия, если эквалайзер доступен
            }
        }*/
        /////////////////
        binder.one.tag = 0
        binder.one.setOnVerticalSeekBarChangeListener(verticalSeekBarChangeListener)
        // binder.one.setOnSeekBarChangeListener(seekBarChangeListener)
        binder.two.tag = 1
        binder.two.setOnVerticalSeekBarChangeListener(verticalSeekBarChangeListener)
        // binder.two.setOnSeekBarChangeListener(seekBarChangeListener)
        binder.three.tag = 2
        binder.three.setOnVerticalSeekBarChangeListener(verticalSeekBarChangeListener)
        //binder.three.setOnSeekBarChangeListener(seekBarChangeListener)
        binder.four.tag = 3
        binder.four.setOnVerticalSeekBarChangeListener(verticalSeekBarChangeListener)
        //  binder.four.setOnSeekBarChangeListener(seekBarChangeListener)
        binder.five.tag = 4
        binder.five.setOnVerticalSeekBarChangeListener(verticalSeekBarChangeListener)
        //  binder.five.setOnSeekBarChangeListener(seekBarChangeListener)

        binder.equalizerSwitch.setOnCheckedChangeListener { _, isChecked ->
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
        //////////////////
        presetsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binder.presetsSpinner.adapter = presetsSpinnerAdapter
        binder.presetsSpinner.onItemSelectedListener = itemSelectedListener

    }


    override fun renderUiState(state: UiState) {
        state as EqualizerContract.State
        when (state.state) {

            is EqualizerContract.EqualizerState.Idle -> {
                idleView()
            }

            is EqualizerContract.EqualizerState.Success -> {
                dataInit(state)
            }
        }
    }
    private val presetsSpinnerAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            R.layout.item_presets_spinner,
            mutableListOf<String>()
        )
    }

    private var spinnerValueIsFromUser = true
    private val itemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
         //  if (spinnerValueIsFromUser) {
                viewModel.setEvent(
                    EqualizerContract.Event.OnPresetSelected(
                        position
                    )
                )
           // }
           // spinnerValueIsFromUser = true
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // Обработка события, когда ничего не выбрано
        }
    }


    private fun dataInit(state: EqualizerContract.State) {
        /* state.presetsNames?.let { presets ->
             ArrayAdapter(
                 requireContext(),
                 R.layout.item_presets_spinner,
                 presets
             ).also { adapter ->
                 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                 binder.presetsSpinner.adapter = adapter
                 binder.presetsSpinner.onItemSelectedListener = itemSelectedListener
             }
         }*/
        state.presetsNames?.let {
            presetsSpinnerAdapter.clear()
            presetsSpinnerAdapter.addAll(it)
        }
        state.currentPreset?.let {
          //  spinnerValueIsFromUser = false
            binder.presetsSpinner.setSelection(it)
        }
        state.bandsLevelRange?.let {
            binder.one.max = it
            binder.two.max = it
            binder.three.max = it
            binder.four.max = it
            binder.five.max = it
        }
        state.equalizerValues?.let {
            binder.one.setValue(it[0])
            binder.two.setValue(it[1])
            binder.three.setValue(it[2])
            binder.four.setValue(it[3])
            binder.five.setValue(it[4])
        }
        state.equalizerValuesInfo?.let {
            binder.oneValue.text = it.elementAt(0)
            binder.twoValue.text = it.elementAt(1)
            binder.threeValue.text = it.elementAt(2)
            binder.fourValue.text = it.elementAt(3)
            binder.fiveValue.text = it.elementAt(4)
        }

        state.equalizerEnabled?.let {
            binder.equalizerSwitch.isChecked = it
            binder.one.isEnabled = it
            binder.two.isEnabled = it
            binder.three.isEnabled = it
            binder.four.isEnabled = it
            binder.five.isEnabled = it
        }

        state.loudnessEnhancerEnabled?.let {
            binder.loudnessSwitch.isChecked = it
            binder.circularSeekBar.isEnabled = it
        }
        state.loudnessEnhancerValue?.let {

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

    override fun onStartTrackingTouch(progress: Int) {
        viewModel.setEvent(
            EqualizerContract.Event.OnLoudnessEnhancerTargetGain(
                progress
            )
        )
    }

    override fun onStopTrackingTouch(progress: Int) {
        viewModel.setEvent(
            EqualizerContract.Event.OnLoudnessEnhancerTargetGainSet(
                progress
            )
        )
    }
}
