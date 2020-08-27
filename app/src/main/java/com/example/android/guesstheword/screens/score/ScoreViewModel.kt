package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(score: Int) : ViewModel() {

    private var _finalScore = MutableLiveData<Int>()

    val finalScore: LiveData<Int>
        get() = _finalScore

    private var _eventPlayAgain = MutableLiveData<Boolean>()

    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    init {
        Log.i("ScoreViewModel : ", "Final Score is $score")
        _finalScore.value = score
        _eventPlayAgain.value = false
    }

    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }
}