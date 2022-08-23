package com.levox.composition.data

import com.levox.composition.domain.entity.GameSettings
import com.levox.composition.domain.entity.Level
import com.levox.composition.domain.entity.Question
import com.levox.composition.domain.repository.GameRepository
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, optionsCount: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = HashSet<Int>()
        val correctAnswer = sum - visibleNumber
        options.add(correctAnswer)
        val from = max(correctAnswer - optionsCount, MIN_ANSWER_VALUE)
        val to = min(maxSumValue, correctAnswer + optionsCount)
        while (options.size < optionsCount) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> GameSettings(
                10,
                3,
                10,
                10
            )
            Level.EASY -> GameSettings(
                10,
                10,
                70,
                60
            )
            Level.NORMAL -> GameSettings(
                20,
                20,
                80,
                60
            )
            Level.HARD -> GameSettings(
                30,
                30,
                90,
                40
            )
        }
    }
}