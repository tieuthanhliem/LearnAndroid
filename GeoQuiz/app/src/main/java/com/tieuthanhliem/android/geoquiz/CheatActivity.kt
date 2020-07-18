package com.tieuthanhliem.android.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

const val EXTRA_ANSWER_SHOWN = "com.tieuthanhliem.android.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.tieuthanhliem.android.geoquiz.answer_is_true"
private  const val KEY_CHEATING = "CHEATING"

class CheatActivity : AppCompatActivity() {
    private lateinit var showAnswerButton: Button
    private lateinit var answerTextView: TextView
    private lateinit var apiLevelTextView: TextView

    private var answer: Boolean = false
    private var isCheated = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        showAnswerButton = findViewById(R.id.show_answer_button)
        answerTextView = findViewById(R.id.answer_text_view)
        apiLevelTextView = findViewById(R.id.api_level_text_view)

        answer = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        showAnswerButton.setOnClickListener{
            onClickShowAnswer()
        }

        apiLevelTextView.text = getString(R.string.api_level, Build.VERSION.SDK_INT)

        isCheated = savedInstanceState?.getBoolean(KEY_CHEATING) ?: false
        if (isCheated) {
            onClickShowAnswer()
        }
    }

    private fun onClickShowAnswer() {
        val answerText = when {
            answer -> R.string.true_button
            else -> R.string.false_button
        }

        answerTextView.setText(answerText)
        setAnswerShownResult(true)
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        isCheated = isAnswerShown
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_CHEATING, isCheated)
    }

    companion object {
        fun newIntent(packageContext: Context, answer: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answer)
            }
        }
    }
}