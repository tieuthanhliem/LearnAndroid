package com.tieuthanhliem.android.geoquiz

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "CurrentIndex"
private const val REQUEST_CODE_CHEAT = 0
private const val MAX_CHEAT = 3


class MainActivity : AppCompatActivity() {
    private lateinit var trueButton : Button
    private lateinit var falseButton: Button
    private lateinit var cheatButton: Button
    private lateinit var cheatLeftTextView: TextView
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        setContentView(R.layout.activity_main)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        cheatButton = findViewById(R.id.cheat_button)
        cheatLeftTextView = findViewById(R.id.cheat_left)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.previous_button)
        questionTextView = findViewById(R.id.question_text_view)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener{
            checkAnswer(false)
        }

        cheatButton.setOnClickListener { view ->
            val answer = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answer)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val options = ActivityOptions.makeClipRevealAnimation(
                    view, 0, 0, view.width, view.height
                )
                startActivityForResult(intent, REQUEST_CODE_CHEAT, options.toBundle())
            } else {
                startActivityForResult(intent, REQUEST_CODE_CHEAT)
            }
        }

        nextButton.setOnClickListener {
            showNextQuestion()
        }

        prevButton.setOnClickListener {
            showPreviousQuestion()
        }


        questionTextView.setOnClickListener{
            showNextQuestion()
        }

        updateQuestion()
        quizViewModel.cheatLeft = MAX_CHEAT
        updateCheatLeft()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != REQUEST_CODE_CHEAT) {
            return
        }

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        quizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        if (quizViewModel.isCheater && quizViewModel.cheatLeft > 0) {
            quizViewModel.cheatLeft -= 1
        }
        updateCheatLeft()
    }

    private fun updateCheatLeft() {
        val cheatLeft = quizViewModel.cheatLeft
        cheatLeftTextView.text = getString(R.string.cheat_left, cheatLeft)
        if (cheatLeft == 0) {
            cheatButton.isEnabled = false
        }
    }

    private fun showNextQuestion() {
        quizViewModel.moveToNext()
        updateQuestion()
    }

    private fun showPreviousQuestion() {
        quizViewModel.moveToPrevious()
        updateQuestion()
    }

    private fun updateQuestion() {
        if (quizViewModel.userAnswer != null) {
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        } else {
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
        quizViewModel.isCheater = false
        questionTextView.setText(quizViewModel.currentQuestionText)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        quizViewModel.userAnswer = userAnswer
        trueButton.isEnabled = false
        falseButton.isEnabled = false
        quizViewModel.answeredQuestionNum += 1

        val questionResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.toast_correct
            else -> R.string.toast_incorrect
        }

        Toast.makeText(this, questionResId, Toast.LENGTH_SHORT).show()
        if (quizViewModel.isDoneQuiz) {
            val percent = quizViewModel.calculateCorrectPercent()
            Toast.makeText(
                this,
                this.getString(R.string.correct_percent, percent),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}