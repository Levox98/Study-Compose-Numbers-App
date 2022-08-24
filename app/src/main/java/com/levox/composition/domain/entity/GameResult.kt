package com.levox.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val isWinner: Boolean,
    val correctAnswers: Int,
    val questionCount: Int,
    val gameSettings: GameSettings
) : Parcelable