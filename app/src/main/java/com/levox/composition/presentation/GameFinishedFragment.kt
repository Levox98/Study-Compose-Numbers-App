package com.levox.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.levox.composition.R
import com.levox.composition.databinding.FragmentGameFinishedBinding
import com.levox.composition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRetry.setOnClickListener { retryGame() }
        bindViews()
    }

    private fun bindViews() {
        with(binding) {
            if (args.result.isWinner) {
                emojiResult.setImageResource(R.drawable.ic_happy)
            } else {
                emojiResult.setImageResource(R.drawable.ic_sad)
            }

            tvRequiredAnswers.text = String.format(getString(R.string.required_score), args.result.gameSettings.minCorrectAnswerCount)
            tvScoreAnswers.text =  String.format(getString(R.string.score_answers), args.result.correctAnswers)
            tvRequiredPercentage.text = String.format(getString(R.string.required_percentage), args.result.gameSettings.minCorrectAnswerPercentage)
            tvScorePercentage.text = String.format(getString(R.string.score_percentage), getPercentageOfCorrectAnswers())
        }
    }

    private fun getPercentageOfCorrectAnswers() = with(args.result) {
        if (questionCount == 0) {
            0
        } else {
            ((correctAnswers / questionCount.toDouble()) * 100).toInt()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }
}