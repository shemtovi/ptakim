package com.idanroey.ptakim_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class Game : AppCompatActivity() {

    private var roundNumber = 1
    private lateinit var game: Ptakim
    private lateinit var team1: Team
    private lateinit var team2: Team
    private lateinit var player1Score: TextView
    private lateinit var player2Score: TextView
    private lateinit var word: TextView
    private  lateinit var leftNumOfWordsTextView: TextView


    //timer var
    private   var  START_TIME_IN_MILLIS: Long =10000
    private var mTimerRunning = false
    private var mTimeLeftInMillis: Long = START_TIME_IN_MILLIS
    private lateinit var timerView: TextView
    private lateinit var timer: CountDownTimer

    private lateinit var bilder: AlertDialog.Builder
    private  lateinit var dialog: AlertDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val numberOfWords = this.intent.getIntExtra("numberOfWords", 20)
        val selectedCategories = this.intent.getIntArrayExtra("filteredCategories")!!

        timerView = findViewById(R.id.timer)
        leftNumOfWordsTextView = findViewById(R.id.leftWordsTextViewInt)
        leftNumOfWordsTextView.text = numberOfWords.toString()

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
            if(mTimerRunning){
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
                leftNumOfWordsTextView.text = game.leftWords()
            }
        }

        findViewById<ImageButton>(R.id.wrong_button).setOnClickListener {
            if(mTimerRunning){
                game.wrongGuess()
                if (game.currentTeam === team1) {
                    player1Score.text = team1.getScore().toString()
                } else {
                    player2Score.text = team2.getScore().toString()
                }
                word.text = game.drawPetek()
            }
        }

        findViewById<ImageButton>(R.id.start_stop_button).setOnClickListener{
            CreateNewAlertDialog()
            if(mTimerRunning) pauseTimer()
            else startTimer()
        }
    }

    private fun nextRound() {
        if (roundNumber < 3) {
            roundNumber++
            pauseTimer()
            resetTimer()
            game.teamSwitch()
            game.startRound()
            word.text = game.drawPetek()
            //popup window
        } else {
            this.finish()
        }
    }

    fun startTimer() {
        timer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                timerView.text = (mTimeLeftInMillis / 1000).toString()

            }

            override fun onFinish() {
                resetTimer()
                game.teamSwitch()
                //popup window
            }
        }.start()
        mTimerRunning = true

    }

    fun resetTimer(){
        mTimeLeftInMillis = START_TIME_IN_MILLIS
        timerView.text = (mTimeLeftInMillis / 1000).toString()
        mTimerRunning = false

    }

    fun  pauseTimer(){
        timer.cancel()
        mTimerRunning = false
        //popup window
    }

    fun CreateNewAlertDialog(){
        bilder =AlertDialog.Builder(this)
        val view = View.inflate(this@Game,R.layout.alert_dialog_stages,null)
        bilder.setView(view)
        dialog = bilder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

}