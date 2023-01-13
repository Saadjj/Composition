package com.bignerdranch.android.composition.presentation

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.composition.R
import com.bignerdranch.android.composition.data.GameRepositoryImpl
import com.bignerdranch.android.composition.domain.entity.GameResult
import com.bignerdranch.android.composition.domain.entity.GameSettings
import com.bignerdranch.android.composition.domain.entity.Level
import com.bignerdranch.android.composition.domain.entity.Question
import com.bignerdranch.android.composition.domain.usecases.GenerateQuestionUseCase
import com.bignerdranch.android.composition.domain.usecases.GetGameSettingsUseCase

//наследуемся от AndroidViewModel() для того чтобы можно было иcпользовать контекст(для извлечения строковых ресурсов)
class GameViewModel(
    //application это само приложение
    private val application: Application,
    private val level:Level
) : ViewModel() {

    private lateinit var gameSettings: GameSettings


    private val repository = GameRepositoryImpl
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)


    private var timer: CountDownTimer? = null
    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    //ФРАГМЕНТ БУДЕТ ОТОРАЖАТЬ СТРОКУ, ПОЭТОМУ И храним ее
    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    init{
        startGame()
    }
    //установка первоначальных настроек из фрагмента
   private fun startGame() {
        getGameSettings()
        startTimer()
        generateQuestion()
        updateProgress()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }


    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            application.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCount.value =
            countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercent.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (countOfQuestions == 0) {
            return 0
        }
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    /**
     * получение настроек игры(уровня)
     */
    private fun getGameSettings(){

        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
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
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                gameFinished()
            }
        }
        //начало таймера
        timer?.start()
    }

    /**
     * метод преобразовывает оставшееся время в миллисекндах в читаемый формат
     */
    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }


    override fun onCleared() {
        super.onCleared()
        ///остановка таймера
        timer?.cancel()
    }

    private fun gameFinished() {
        _gameResult.value = GameResult(
            enoughCount.value == true
                    && enoughPercent.value == true,
            countOfRightAnswers,
            countOfQuestions,
            gameSettings
        )
    }


    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }
}
