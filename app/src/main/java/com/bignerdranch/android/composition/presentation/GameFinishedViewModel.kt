package com.bignerdranch.android.composition.presentation

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import com.bignerdranch.android.composition.domain.entity.GameResult
import com.bignerdranch.android.composition.domain.entity.GameSettings

class GameFinishedViewModel(application: Application) : AndroidViewModel(application){
    //так минимум 5 методов
    //1- необходимое колличество правильных овтетов
    //2 наш счет
    // 3необходимы процент правильных ответов
    // 4текущий процент правильных ответов
    //5 установка изображения win/loise

fun updateAmountOfRightAnswers(gameSettings: GameSettings){
val amountOfRightAnsers=gameSettings.minCountOfRightAnswers
}

    fun updateScore(gameResult: GameResult){
        val score=gameResult.countOfRightAnswers
    }

    fun updateMinPercentAmountOfRightAnswers(gameSettings:GameSettings){
        val minPercentAmountORightAnswers=gameSettings.minPercentOfRightAnswers
    }

    fun updatePercentOfRightAnswers(gameResult: GameResult){
val scoreInPercent=gameResult.
    }
    fun showPicture(gameResult: GameResult){
        if(gameResult.winner){
            //то кпоказываем одно
        }else{
            //показываем другое
        }
    }
}