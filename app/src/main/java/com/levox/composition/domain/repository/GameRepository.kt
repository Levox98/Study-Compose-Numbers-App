package com.levox.composition.domain.repository

import com.levox.composition.domain.entity.GameSettings
import com.levox.composition.domain.entity.Level
import com.levox.composition.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        optionsCount: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}