package com.levox.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
    val maxSumValue: Int,
    val minCorrectAnswerCount: Int,
    val minCorrectAnswerPercentage: Int,
    val gameTime: Int
) : Parcelable