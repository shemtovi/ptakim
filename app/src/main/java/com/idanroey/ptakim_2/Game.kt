package com.idanroey.ptakim_2

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.HapticFeedbackConstants
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class Game : AppCompatActivity() {

    private var roundNumber = 1
    private lateinit var game: Ptakim
    private lateinit var team1: Team
    private lateinit var team2: Team
    private lateinit var player1Score: TextView
    private lateinit var player2Score: TextView
    private lateinit var word: TextView
    private lateinit var timerView: TextView
    private lateinit var team1View: TextView
    private lateinit var team2View: TextView
    private lateinit var vibrator: Vibrator

    private val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(timeLeftInMillis: Long) {
                timerView.text = (timeLeftInMillis / 1000).toString()
            }

            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onFinish() {
                game.teamSwitch()
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK))
                updateTeamsViews()
                startTimer()
            }
        }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val numberOfWords = this.intent.getIntExtra("numberOfWords", 20)
        val selectedCategories = this.intent.getIntArrayExtra("filteredCategories")!!

        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager= getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator

        } else {
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        timerView = findViewById(R.id.timer)

        team1 = Team(1)
        team2 = Team(2)
        game = Ptakim(selectedCategories, numberOfWords, team1, team2)

        player1Score = findViewById(R.id.player1_ScoreBoard)
        player2Score = findViewById(R.id.player2_ScoreBoard)
        word = findViewById(R.id.word)
        team1View = findViewById(R.id.player_1)
        team2View = findViewById(R.id.player_2)

        game.startRound()
        updateTeamsViews()
        word.text = game.drawPetek()

        startTimer()

        findViewById<ImageButton>(R.id.right_button).setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
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
            val handler = Handler(Looper.getMainLooper())
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            handler.postDelayed(
                {it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)},
                100
            )
            game.wrongGuess()
            if (game.currentTeam === team1) {
                player1Score.text = team1.getScore().toString()
            } else {
                player2Score.text = team2.getScore().toString()
            }
            word.text = game.drawPetek()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun nextRound() {
        if (roundNumber < 3) {
            roundNumber++
            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK))
            timer.cancel()
            game.teamSwitch()
            updateTeamsViews()
            game.startRound()
            word.text = game.drawPetek()
            timer.start()
        } else {
            this.finish()
        }
    }

    fun updateTeamsViews() {
        val textColor: String = "#" + Integer.toHexString(ResourcesCompat.getColor(resources, R.color.text_color, null))
        team1View.typeface = if (game.currentTeam === team1) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        team1View.setTextColor(Color.parseColor(if (game.currentTeam === team1) "#5adbb5" else textColor))
        team2View.typeface = if (game.currentTeam === team2) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        team2View.setTextColor(Color.parseColor(if (game.currentTeam === team2) "#5adbb5" else textColor))
    }

    fun startTimer() {
     timer.start()
    }
}