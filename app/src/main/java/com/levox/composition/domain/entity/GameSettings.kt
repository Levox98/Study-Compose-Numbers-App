package com.levox.composition.domain.entity

import java.io.Serializable

data class GameSettings(
    val maxSumValue: Int,
    val minCorrectAnswerCount: Int,
    val minCorrectAnswerPercentage: Int,
    val gameTime: Int
) : Serializable