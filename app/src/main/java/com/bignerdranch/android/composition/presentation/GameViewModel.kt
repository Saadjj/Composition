package com.bignerdranch.android.composition.presentation

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.composition.data.GameRepositoryImpl
import com.bignerdranch.android.composition.domain.entity.GameSettings
import com.bignerdranch.android.composition.domain.entity.Level
import com.bignerdranch.android.composition.domain.entity.Question
import com.bignerdranch.android.composition.domain.usecases.GenerateQuestionUseCase
import com.bignerdranch.android.composition.domain.usecases.GetGameSettingsUseCase

class GameViewModel : ViewModel() {

    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level

    private val repository = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private var timer:CountDownTimer?=null

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime


    //установка первоначальных настроек из фрагмента
    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer()
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
    }

    /**
     * старт таймера
     */
    private fun startTimer() {
         timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun equals(other: Any?): Boolean {
                return super.equals(other)
            }

            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value=formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                gameFinished()
            }
        }
        timer?.start()
    }

    /**
     * метод преобразовывает оставшееся время в миллисекндах в читаемый формат
     */
    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished/ MILLIS_IN_SECONDS
        val minutes=seconds/ SECONDS_IN_MINUTES
        val leftSeconds=seconds-(minutes* SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes,leftSeconds)
    }


    override fun onCleared() {
        super.onCleared()
    }

    private fun gameFinished() {}



    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES=60
    }
}
