package com.bignerdranch.android.composition.domain.repository

import com.bignerdranch.android.composition.domain.entity.GameSettings
import com.bignerdranch.android.composition.domain.entity.Level
import com.bignerdranch.android.composition.domain.entity.Question

interface GameRepository {

    fun GenerateQuestion(maxSumValuse:Int, countOfOptions:Int): Question

    fun getGameSettings(level: Level):GameSettings

}