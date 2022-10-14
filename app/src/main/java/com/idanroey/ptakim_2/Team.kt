package com.idanroey.ptakim_2

class Team(val teamNumber: Int, val teamName: String? = null) {
    private var score = 0
    fun rightGuess() {
        score += 2
    }
    fun wrongGuess() {
        if (score > 5) score--
    }

    fun getScore(): Int {
        return score
    }
}