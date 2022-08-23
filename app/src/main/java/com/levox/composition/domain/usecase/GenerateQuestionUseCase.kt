package com.levox.composition.domain.usecase

import com.levox.composition.domain.entity.Question
import com.levox.composition.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, OPTIONS_COUNT)
    }

    private companion object {
        private const val OPTIONS_COUNT = 6
    }
}