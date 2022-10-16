package com.idanroey.ptakim_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import android.widget.TextView

class Game : AppCompatActivity() {

    private var roundNumber = 1
    private lateinit var game: Ptakim
    private lateinit var team1: Team
    private lateinit var team2: Team
    private lateinit var player1Score: TextView
    private lateinit var player2Score: TextView
    private lateinit var word: TextView
    private lateinit var timerView: TextView

    private val timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(timeLeftInMillis: Long) {
                timerView.text = (timeLeftInMillis / 1000).toString()
            }

            override fun onFinish() {
                game.teamSwitch()
                startTimer()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val numberOfWords = this.intent.getIntExtra("numberOfWords", 20)
        val selectedCategories = this.intent.getIntArrayExtra("filteredCategories")!!

        timerView = findViewById(R.id.timer)

        team1 = Team(1)
        team2 = Team(2)
        game = Ptakim(selectedCategories, numberOfWords, team1, team2)

        player1Score = findViewById(R.id.player1_ScoreBoard)
        player2Score = findViewById(R.id.player2_ScoreBoard)
        word = findViewById(R.id.word)

        game.startRound()
        word.text = game.drawPetek()

        startTimer()

        findViewById<ImageButton>(R.id.right_button).setOnClickListener {
            game.rightGuess()
            if (game.currentTeam === team1) {
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
            game.wrongGuess()
            if (game.currentTeam === team1) {
                player1Score.text = team1.getScore().toString()
            } else {
                player2Score.text = team2.getScore().toString()
            }
            word.text = game.drawPetek()
        }
    }

    private fun nextRound() {
        if (roundNumber < 3) {
            roundNumber++
            timer.cancel()
            game.teamSwitch()
            game.startRound()
            word.text = game.drawPetek()
            timer.start()
        } else {
            this.finish()
        }
    }

    fun startTimer() {
     timer.start()
    }
}