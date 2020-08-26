package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L

        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L

        // This is the time for one guess
        const val COUNTDOWN_TIME = 60000L
    }

    // Timer to track time
    private val timer: CountDownTimer

    private val _timerText = MutableLiveData<String>()

    val timerText: LiveData<String>
        get() = _timerText

    // The current word
    private val _word = MutableLiveData<String>()

    val word: LiveData<String>
        get() = _word

    // The current score
    private val _score = MutableLiveData<Int>()

    val score: LiveData<Int>
        get() = _score

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    // To check if the game has finished
    private val _eventGameFinish = MutableLiveData<Boolean>()

    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    init {
        Log.i("GameViewModel : ", "GameViewModel instance created!")

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                val secondsUntilFinished = millisUntilFinished / ONE_SECOND
                _timerText.value = "Time Remaining : " + DateUtils.formatElapsedTime(secondsUntilFinished)
            }

            override fun onFinish() {
                _timerText.value = "Time Up!"
                nextWord()
            }
        }

        timer.start()

        _word.value = ""
        _score.value = 0
        _eventGameFinish.value = false
        resetList()
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Log.i("GameViewModel : ", "GameViewModel instance destroyed!")
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            _eventGameFinish.value = true
        } else {
            _word.value = wordList.removeAt(0)
            timer.start()
        }
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = _score.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = _score.value?.plus(1)
        nextWord()
    }

    fun onGameFinishEventComplete() {
        _eventGameFinish.value = false
    }
}