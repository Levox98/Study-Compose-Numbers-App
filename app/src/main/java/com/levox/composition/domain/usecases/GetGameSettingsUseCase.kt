package com.levox.composition.domain.usecases

import com.levox.composition.domain.entity.GameSettings
import com.levox.composition.domain.entity.Level
import com.levox.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}