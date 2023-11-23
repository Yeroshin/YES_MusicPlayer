package com.yes.player.presentation.ui


import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.yes.core.presentation.BaseViewModel
import com.yes.player.databinding.PlayerBinding
import com.yes.player.di.components.PlayerFeatureComponent
import com.yes.player.presentation.contract.PlayerContract
import com.yes.player.presentation.model.PlayerStateUI
import com.yes.player.presentation.vm.PlayerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PlayerFragment : Fragment() {
    interface DependencyResolver {
        fun getPlayerFragmentComponent(): PlayerFeatureComponent
    }

    private val component by lazy {
        (requireActivity().application as DependencyResolver)
            .getPlayerFragmentComponent()
    }
    private val dependency by lazy {
        component.getDependency()
    }

    private lateinit var binding: ViewBinding
    private val binder by lazy {
        binding as PlayerBinding
    }
    private val viewModel: BaseViewModel<PlayerContract.Event,
            PlayerContract.State,
            PlayerContract.Effect> by viewModels {
        dependency.factory
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PlayerBinding.inflate(inflater, container, false)
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
                        is PlayerContract.Effect.UnknownException -> {
                            showError(com.yes.coreui.R.string.UnknownException)
                        }
                    }
                }
            }
        }
    }

    private fun setUpView() {

        binder.btnPlay.setOnClickListener {
            viewModel.setEvent(PlayerContract.Event.OnPlay)
        }
        binder.btnRew.setOnClickListener {
            viewModel.setEvent(PlayerContract.Event.OnSeekToPrevious)
        }
        binder.btnFwd.setOnClickListener {
            viewModel.setEvent(PlayerContract.Event.OnSeekToNext)
        }
        binder.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    viewModel.setEvent(PlayerContract.Event.OnSeek(progress))
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        }
        )
    }


    private fun renderUiState(state: PlayerContract.State) {
        when (state.playerState) {
            is PlayerContract.PlayerState.Success -> {
                dataLoaded(
                    state.playerState.info,
                )
            }

            is PlayerContract.PlayerState.Idle -> {
                idleView()
            }

        }
    }

    private fun dataLoaded(playerState: PlayerStateUI) {
        binder.durationCounter.gravity = Gravity.CENTER_HORIZONTAL
        binder.durationCounter.gravity = Gravity.CENTER_VERTICAL
        if (playerState.stateBuffering) {
            showBuffering()
        } else {
            hideBuffering()
            playerState.playListName?.let { binder.playListName.text = playerState.playListName }
            playerState.trackTitle?.let {
                binder.trackTitle.text = playerState.trackTitle
            }
            playerState.durationCounter?.let {
                binder.durationCounter.text = playerState.durationCounter
            }
            playerState.duration?.let { binder.duration.text = playerState.duration }
            playerState.progress?.let {
                binder.seekBar.progress = playerState.progress
            }
            playerState.durationInt?.let {
                binder.seekBar.max = playerState.durationInt
            }
            playerState.visualizerData?.let {
                binder.composeView.setContent {
                 /* AdaptiveGrid(playerState.visualizerData)*/
                   /* EqualizerView(
                        playerState.visualizerData
                    )*/
                    DimensionLayout(
                        values = playerState.visualizerData,
                        mainContent = { MainContent() },
                        dependentContent = { maxSize,values -> DependentContent(maxSize,values) } // Передаем DependentContent в качестве dependentContent
                    )
                }
            }
        }


    }

    private fun idleView() {

    }


    private fun showError(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private val animateTextJob = Job()
    private val animateTextcoroutineScope =
        CoroutineScope(lifecycleScope.coroutineContext + animateTextJob)

    private fun showBuffering() {

        binder.durationCounter.gravity = Gravity.LEFT
        binder.durationCounter.gravity = Gravity.CENTER_VERTICAL
        val initialText = resources.getString(com.yes.coreui.R.string.buffering)
        val dots = "..."
        val animationDuration = 500L // Длительность анимации в миллисекундах

        val animation = AlphaAnimation(1.0f, 1.0f).apply {
            duration = animationDuration / 2
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
        }

        animateTextcoroutineScope.launch {
            binder.durationCounter.startAnimation(animation)
            var dotCount = 0
            while (true) {
                val text = dots.substring(0, dotCount) + " ".repeat(dots.length - dotCount)
                binder.durationCounter.text = initialText + text
                binder.durationCounter.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    resources.getDimension(com.yes.coreui.R.dimen.font_size_small)
                )
                dotCount = (dotCount + 1) % (dots.length + 1)
                delay(animationDuration)
            }
        }
    }

    private fun hideBuffering() {
        animateTextJob.cancel()
    }

    class Dependency(
        val factory: PlayerViewModel.Factory,

    )
}
