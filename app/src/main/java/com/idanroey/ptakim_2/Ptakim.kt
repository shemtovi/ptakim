package com.idanroey.ptakim_2

import android.content.Context
import android.util.Log
import com.idanroey.ptakim_2.db.WordDatabase

class Ptakim(db: WordDatabase, filteredCategories: IntArray, private val numberOfWords: Int, private val team1: Team, private val team2: Team) {

    var currentTeam: Team
    val wordsArray: Set<String>
    val noMoreCards: Boolean
        get() = numberOfWords - guessedRight.size == 0
    private var pointer = 0
    private var roundWordList = mutableListOf<String>()
    private var guessedRight = mutableSetOf<String>()

    init {
        val numberOfCategories = filteredCategories.count()

        // Initialize an array of ints for each selected category and the amount for each category
        val wordsPerCategory = IntArray(numberOfCategories)
        //  Select random amount and adjust to scale
        for (i in 0 until numberOfCategories) {
            wordsPerCategory[i] = (1..numberOfWords).random()
        }
        val sum = wordsPerCategory.sum()
        val scale = (numberOfWords.toDouble() - numberOfCategories) / sum.toDouble()
        for ((index, number) in wordsPerCategory.withIndex()) {
            wordsPerCategory[index] = (number * scale).toInt() + 1
        }
        // Add missing amount to random categories
        while (wordsPerCategory.sum() != numberOfWords) {
            wordsPerCategory[(0 until numberOfCategories).random()] += 1
        }

        val wordsArray = Array(numberOfWords) { "" }
        var i = 0
        for ((index, category) in filteredCategories.withIndex()) {
            val words = db.dao().getNWordsByCategory(category, wordsPerCategory[index])
            for (word in words) {
                wordsArray[i++] = word
            }
        }
        wordsArray.shuffle()
        this.wordsArray = wordsArray.toSet()

        currentTeam = if ((1..2).random() == 1) team1 else team2
    }

    fun startRound() {
        roundWordList = wordsArray.shuffled().toMutableList()
        guessedRight.removeAll { true }
        pointer = 0
    }

    fun drawPetek(): String {
        return roundWordList[pointer]
    }

    fun wrongGuess() {
        pointer = ++pointer % roundWordList.size
        currentTeam.wrongGuess()
    }

    fun teamSwitch() {
        currentTeam = if (currentTeam === team1) team2 else team1
        roundWordList.shuffle()
        pointer = 0
    }

    fun rightGuess() {
        guessedRight.add(roundWordList[pointer])
        roundWordList.removeAt(pointer)
        try {
            pointer %= roundWordList.size
        } catch (e: ArithmeticException) {
            pointer = 0
        }
        currentTeam.rightGuess()
    }

    fun leftWords(): Int {
        return roundWordList.size
    }

}
