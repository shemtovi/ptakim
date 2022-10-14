package com.idanroey.ptakim_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class Game : AppCompatActivity() {

    private lateinit var game: Ptakim
    private lateinit var team1: Team
    private lateinit var team2: Team
    private lateinit var player1Score: TextView
    private lateinit var player2Score: TextView
    private lateinit var word: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val numberOfWords = this.intent.getIntExtra("numberOfWords", 20)
        val selectedCategories = this.intent.getIntArrayExtra("filteredCategories")!!

        game = Ptakim(selectedCategories, numberOfWords)
        team1 = Team(1)
        team2 = Team(2)
        var currentTeam = if ((1..2).random() == 1) team1 else team2

        player1Score = findViewById(R.id.player1_ScoreBoard)
        player2Score = findViewById(R.id.player2_ScoreBoard)
        word = findViewById<TextView>(R.id.word)

        game.startRound()
        word.text = game.drawPetek()

        findViewById<ImageButton>(R.id.right_button).setOnClickListener {
            game.rightGuess(currentTeam)
            if (currentTeam === team1) {
                player1Score.text = team1.getScore().toString()
            } else {
                player2Score.text = team2.getScore().toString()
            }
            if (!game.noMoreCards) {
                word.text = game.drawPetek()
            } else {
                nextRound()
            }
        }

            findViewById<ImageButton>(R.id.wrong_button).setOnClickListener {
            game.wrongGuess(currentTeam)
            if (currentTeam === team1) {
                player1Score.text = team1.getScore().toString()
            } else {
                player2Score.text = team2.getScore().toString()
            }
            word.text = game.drawPetek()
        }
    }

    private fun nextRound() {
        game.startRound()
        word.text = game.drawPetek()
    }

    fun startTimer() {

    }
}