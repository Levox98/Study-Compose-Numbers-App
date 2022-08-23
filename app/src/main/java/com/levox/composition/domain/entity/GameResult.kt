package com.levox.composition.domain.entity

data class GameResult(
    val isWinner: Boolean,
    val correctAnswers: Int,
    val questionCount: Int,
    val gameSettings: GameSettings
)