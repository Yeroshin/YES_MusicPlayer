package com.yes.player.presentation.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.yes.core.presentation.BaseViewModel
import com.yes.player.databinding.PlayerBinding
import com.yes.player.presentation.model.InfoUI
import com.yes.player.presentation.contract.PlayerContract
import com.yes.player.presentation.vm.PlayerViewModel
import kotlinx.coroutines.launch


class PlayerFragment(
    dependency: Dependency
) : Fragment() {


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
        //////////////
       /* val textView = binder.trackTitle

// Устанавливаем фокус, чтобы текст начал прокручиваться
        textView.isFocusable = true
        textView.isFocusableInTouchMode = true

// Создаем анимацию смещения текста
        val animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 1f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f
        )

// Настраиваем параметры анимации
        animation.duration = 10000 // Длительность анимации (10 секунд)
        animation.repeatCount = Animation.INFINITE // Бесконечное повторение
        animation.interpolator = LinearInterpolator() // Линейный интерполятор

// Запускаем анимацию
        textView.startAnimation(animation)*/
        ///////////////
        binder.btnPlay.setOnClickListener {
            viewModel.setEvent(PlayerContract.Event.OnPlay)
        }
        binder.btnRew.setOnClickListener {
            viewModel.setEvent(PlayerContract.Event.OnSeekToPrevious)
        }
        binder.btnFwd.setOnClickListener {
            viewModel.setEvent(PlayerContract.Event.OnSeekToNext)
        }
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

    private fun dataLoaded(info: InfoUI) {
        info.playListName?.let {  binder.playListName.text = info.playListName}
        info.trackTitle?.let {
            binder.trackTitle.text = info.trackTitle}
        info.durationCounter?.let { binder.durationCounter.text = info.durationCounter}
        info.duration?.let { binder.duration.text = info.duration}
    }

    private fun idleView() {
    }



    private fun showError(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    class Dependency(
        val factory: PlayerViewModel.Factory,
    )
}