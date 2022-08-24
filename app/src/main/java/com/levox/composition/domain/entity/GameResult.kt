package com.levox.composition.domain.entity

import java.io.Serializable

data class GameResult(
    val isWinner: Boolean,
    val correctAnswers: Int,
    val questionCount: Int,
    val gameSettings: GameSettings
) : Serializable