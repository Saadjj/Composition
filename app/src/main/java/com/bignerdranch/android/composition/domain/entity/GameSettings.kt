package com.bignerdranch.android.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//релизуем parceble послкдьку он быстрее чем serializeble
@Parcelize
data class GameSettings(
    val maxSumValue:Int,
    val minCountOfRightAnswers:Int,
    val minPercentOfRightAnswers:Int,
    val gameTimeInSeconds:Int

) : Parcelable