package com.levox.composition.domain.entity

data class GameSettings(
    val maxSumValue: Int,
    val minCorrectAnswerCount: Int,
    val minCorrectAnswerPercentage: Int,
    val gameTime: Int
)