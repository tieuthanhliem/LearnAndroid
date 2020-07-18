package com.tieuthanhliem.android.geoquiz

import androidx.lifecycle.ViewModel


class QuizViewModel : ViewModel() {
    var currentIndex = 0
    var answeredQuestionNum = 0
    var isCheater = false
    var cheatLeft = 0

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    var userAnswer: Boolean?
        get() = questionBank[currentIndex].userAnswer
        set(value) {
            questionBank[currentIndex].userAnswer = value
        }

    val isDoneQuiz: Boolean
        get() = answeredQuestionNum == questionBank.size

    private fun calculateCorrectAnswers(): Int {
        return questionBank.count{
            it.userAnswer == it.answer
        }
    }

    fun calculateCorrectPercent(): Float {
        return  calculateCorrectAnswers() * 100.0f / questionBank.size
    }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrevious() {
        currentIndex -= 1
        if (currentIndex < 0)
            currentIndex += questionBank.size
    }

}