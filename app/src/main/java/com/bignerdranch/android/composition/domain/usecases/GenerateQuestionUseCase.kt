package com.bignerdranch.android.composition.domain.usecases

import com.bignerdranch.android.composition.domain.entity.Question
import com.bignerdranch.android.composition.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val repository: GameRepository
) {
    //переопределение этого оператора можно использовать когда есть только метод
    operator fun invoke(maxSumValue:Int): Question {
       return repository.GenerateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object{

        private const val COUNT_OF_OPTIONS=6
    }
}