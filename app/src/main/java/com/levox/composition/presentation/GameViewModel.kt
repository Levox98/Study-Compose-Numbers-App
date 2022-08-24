package com.levox.composition.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.levox.composition.R
import com.levox.composition.data.GameRepositoryImpl
import com.levox.composition.domain.entity.GameResult
import com.levox.composition.domain.entity.GameSettings
import com.levox.composition.domain.entity.Level
import com.levox.composition.domain.entity.Question
import com.levox.composition.domain.usecases.GenerateQuestionUseCase
import com.levox.composition.domain.usecases.GetGameSettingsUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level

    private val context = application
    private val repository = GameRepositoryImpl

    val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private var timer: CountDownTimer? = null

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private var _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _correctPercentage = MutableLiveData<Int>()
    val correctPercentage: LiveData<Int>
        get() = _correctPercentage

    private val _answersProgress = MutableLiveData<String>()
    val answerProgress: LiveData<String>
        get() = _answersProgress

    private val _enoughCorrectCount = MutableLiveData<Boolean>()
    val enoughCorrectCount: LiveData<Boolean>
        get() = _enoughCorrectCount

    private val _enoughCorrectPercentage = MutableLiveData<Boolean>()
    val enoughCorrectPercentage: LiveData<Boolean>
        get() = _enoughCorrectPercentage

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private var countOfCorrect = 0
    private var countOfQuestions = 0

    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer()
        generateQuestion()
        updateProgress()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress() {
        val percentage = calculateCorrectPercentage()
        _correctPercentage.value = percentage
        _answersProgress.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfCorrect,
            gameSettings.minCorrectAnswerPercentage
        )
        _enoughCorrectCount.value = countOfCorrect >= gameSettings.minCorrectAnswerCount
        _enoughCorrectPercentage.value = percentage >= gameSettings.minCorrectAnswerPercentage
    }

    private fun calculateCorrectPercentage(): Int {
        if (countOfQuestions == 0) return 0
        return ((countOfCorrect / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(number: Int) {
        val correctAnswer = question.value?.correctAnswer
        if (number == correctAnswer) {
            countOfCorrect++
        }
        countOfQuestions++
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minCorrectAnswerPercentage
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val secondsLeft = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, secondsLeft)
    }

    fun finishGame() {
        _gameResult.value = GameResult(
            enoughCorrectCount.value == true && enoughCorrectPercentage.value == true,
            countOfCorrect,
            countOfQuestions,
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }
}