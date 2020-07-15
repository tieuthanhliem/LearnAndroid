package com.tieuthanhliem.timefighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
  internal var score = 0

  internal lateinit var tapMeButton: Button
  internal lateinit var gameScoreTextView: TextView
  internal lateinit var timeLeftTextView: TextView

  internal var gameStarted = false

  internal lateinit var countDownTimer: CountDownTimer
  internal var initialCountDown: Long = 15_000
  internal var countDownInterval: Long = 1_000
  internal var timeLeftOnTimer: Long = initialCountDown

  companion object {
    private val TAG = MainActivity::class.java.simpleName
    private val SCORE_KEY = "SCORE_KEY"
    private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    Log.d(TAG, "onCreate called, Score is: $score")

    tapMeButton = findViewById(R.id.tapMeButton)
    gameScoreTextView = findViewById(R.id.gameScoreTextView)
    timeLeftTextView = findViewById(R.id.timeLeftTextView)

    if (savedInstanceState != null) {
      score = savedInstanceState.getInt(SCORE_KEY)
      timeLeftOnTimer = savedInstanceState.getLong(TIME_LEFT_KEY)
      restoreGame()
    } else {
      resetGame()
    }

    tapMeButton.setOnClickListener { _ ->
      if (!gameStarted) {
        startGame()
      }
      incrementScore()
    }
  }

  private fun startGame() {
    countDownTimer.start()
    gameStarted = true
  }

  private fun resetGame() {
    score = 0
    timeLeftOnTimer = initialCountDown
    reinitializeGame()

    gameStarted = false
  }

  private fun restoreGame() {
    reinitializeGame()
    startGame()
  }

  private fun reinitializeGame() {
    setScore(score)

    var timeLeft = timeLeftOnTimer / 1000
    setTimeLeft(timeLeft)
    countDownTimer = object : CountDownTimer(timeLeftOnTimer, countDownInterval) {
      override fun onTick(p0: Long) {
        timeLeftOnTimer = p0
        setTimeLeft(p0 / 1000)
      }

      override fun onFinish() {
        endGame()
      }
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)

    outState.putInt(SCORE_KEY, score)
    outState.putLong(TIME_LEFT_KEY, timeLeftOnTimer)
    countDownTimer.cancel()

    Log.d(TAG, "onSaveInstanceState, score = $score, timeLeftOnTimer = $timeLeftOnTimer")
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d(TAG, "onDestroy")
  }

  private fun setTimeLeft(timeLeft: Long) {
    timeLeftTextView.text = getString(R.string.timeLeft, timeLeft)
  }

  private fun incrementScore() {
    setScore(score + 1)
  }

  private fun setScore(score: Int) {
    this.score = score
    gameScoreTextView.text = getString(R.string.yourScore, score)
  }

  private fun endGame() {
    Toast.makeText(this, getString(R.string.gameOverMessage, score), Toast.LENGTH_LONG).show()
    resetGame()
  }
}