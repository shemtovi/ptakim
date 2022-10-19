package com.idanroey.ptakim_2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.*
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat

class Game : AppCompatActivity() {

    // Game elements
    private var roundNumber = 1
    private lateinit var game: Ptakim
    private lateinit var team1: Team
    private lateinit var team2: Team

    // Text views
    private lateinit var player1Score: TextView
    private lateinit var player2Score: TextView
    private lateinit var team1View: TextView
    private lateinit var team2View: TextView
    private lateinit var word: TextView
    private lateinit var leftNumOfWordsTextView: TextView


    //timer var
    private var START_TIME_IN_MILLIS = 10000L
    private var mTimerRunning = false
    private var mTimeLeftInMillis: Long = START_TIME_IN_MILLIS
    private lateinit var timerView: TextView
    private lateinit var timer: CountDownTimer

    // Resources
    private lateinit var vibrator: Vibrator
    private lateinit var bilder: AlertDialog.Builder
    private  lateinit var dialog: AlertDialog


    @SuppressLint("MissingInflatedId")
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
        leftNumOfWordsTextView = findViewById(R.id.leftWordsTextViewInt)
        leftNumOfWordsTextView.text = numberOfWords.toString()

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

        createRoundAlertDialog(roundNumber)

        findViewById<ImageButton>(R.id.right_button).setOnClickListener {
            if(mTimerRunning){
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
                leftNumOfWordsTextView.text = game.leftWords()
            }
        }

        findViewById<ImageButton>(R.id.wrong_button).setOnClickListener {
            if(mTimerRunning){
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

        findViewById<ImageButton>(R.id.stop_button).setOnClickListener{
            if(mTimerRunning) pauseTimer()
            createStopAlertDialog()
        }
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun nextRound() {
        if (roundNumber < 3) {
            roundNumber++
            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK))
            resetTimer()
            game.teamSwitch()
            updateTeamsViews()
            game.startRound()
            word.text = game.drawPetek()
        }
        //popup window
        createRoundAlertDialog(roundNumber)
    }

    fun updateTeamsViews() {
        val textColor: String = "#" + Integer.toHexString(ResourcesCompat.getColor(resources, R.color.text_color, null))
        team1View.typeface = if (game.currentTeam === team1) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        team1View.setTextColor(Color.parseColor(if (game.currentTeam === team1) "#5adbb5" else textColor))
        team2View.typeface = if (game.currentTeam === team2) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        team2View.setTextColor(Color.parseColor(if (game.currentTeam === team2) "#5adbb5" else textColor))
    }

    //timer functions
    fun startTimer() {
        timer = object : CountDownTimer(mTimeLeftInMillis, 100) {
            override fun onTick(millisUntilFinished: Long) {
                if((millisUntilFinished / 1000) != mTimeLeftInMillis ){
                    mTimeLeftInMillis = millisUntilFinished
                    timerView.text = (mTimeLeftInMillis / 1000).toString()
                }
            }

            override fun onFinish() {
                resetTimer()
                game.teamSwitch()
                updateTeamsViews()
                //popup window
                createStopAlertDialog()
            }
        }.start()
        mTimerRunning = true
    }

    fun resetTimer(){
        pauseTimer()
        mTimeLeftInMillis = START_TIME_IN_MILLIS
        timerView.text = (mTimeLeftInMillis / 1000).toString()
    }

    fun pauseTimer(){
        timer.cancel()
        mTimerRunning = false
    }

    //dialog functions
    fun createNewAlertDialog(){
        bilder =AlertDialog.Builder(this)
        val view = View.inflate(this@Game,R.layout.alert_dialog_stages,null)
        bilder.setView(view)
        dialog = bilder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
    }

    @SuppressLint("SetTextI18n")
    fun createStopAlertDialog(){
        createNewAlertDialog()
        dialog.findViewById<TextView>(R.id.upperText)?.text = "סיבוב מס: $roundNumber"
        dialog.findViewById<TextView>(R.id.lowerText)?.text = "תור של קבוצה מס: " + game.currentTeam.teamNumber.toString()

    }

    fun createRoundAlertDialog(round:Int){
        createNewAlertDialog()
        val upperText = dialog.findViewById<TextView>(R.id.upperText)
        var lowerText = dialog.findViewById<TextView>(R.id.lowerText)
        val dialogButton: Button? = dialog.findViewById(R.id.alertDialogButton)
        if(round == 1){
         //round 1 text
            if (upperText != null) {
                upperText.text = "שלב 1"
            }
        }
        else if(round == 2){
            //round 2 text
            if (upperText != null) {
                upperText.text = "שלב 2"
            }
        }
        else{
            //round 3 text + changing dialogButton function
            if (upperText != null) {
                upperText.text = "שלב 3"
            }
            if (dialogButton != null) {
                dialogButton.text = "משחק חדש"
                dialogButton.setOnClickListener{
                    //startNewGame()
                    this.finish()
                }
            }

        }


    }


    fun dialogButton(view: View){
        dialog.dismiss()
        startTimer()
    }

    fun startNewGame(){
        TODO()
    }

}