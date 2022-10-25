package com.idanroey.ptakim_2

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
    private lateinit var leftNumOfWords: TextView


    // Timer var
    private var START_TIME_IN_MILLIS = 60000L
    private var mTimerRunning = false
    private var mTimeLeftInMillis: Long = START_TIME_IN_MILLIS
    private lateinit var timerView: TextView
    private lateinit var timer: CountDownTimer

    // Resources
    private lateinit var vibrator: Vibrator
    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog

    private  var numberOfWords:  Int = 20
    private lateinit var selectedCategories: IntArray

    // Vibration effects
    private val repeat = -1
    @RequiresApi(Build.VERSION_CODES.O)
    private val timerEndVibrationEffect = VibrationEffect.createWaveform(
        longArrayOf(0, 75, 50, 75, 50, 75),
        intArrayOf(0, 50, 0, 50, 0, 50),
        repeat
    )
    @RequiresApi(Build.VERSION_CODES.O)
    private val roundEndVibrationEffect = VibrationEffect.createWaveform(
        longArrayOf(0, 150),
        intArrayOf(0, 255),
        repeat
    )
    @RequiresApi(Build.VERSION_CODES.O)
    private val rightGuessVibrationEffect = VibrationEffect.createWaveform(
        longArrayOf(0, 75),
        intArrayOf(0, 75),
        repeat
    )
    @RequiresApi(Build.VERSION_CODES.O)
    private val wrongGuessVibrationEffect = VibrationEffect.createWaveform(
        longArrayOf(0, 50, 50, 50),
        intArrayOf(0, 75, 0, 75),
        repeat
    )



    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

         numberOfWords = this.intent.getIntExtra("numberOfWords", 20)
         selectedCategories = this.intent.getIntArrayExtra("filteredCategories")!!

        START_TIME_IN_MILLIS = this.intent.getLongExtra("timePerRound", 60000)
        mTimeLeftInMillis = START_TIME_IN_MILLIS
        timerView = findViewById(R.id.timer)
        timerView.text = (START_TIME_IN_MILLIS / 1000).toString()

        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager= getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        leftNumOfWords = findViewById(R.id.left_words)
        leftNumOfWords.text = getString(R.string.left_words_text).format(numberOfWords)

        val roundView = findViewById<TextView>(R.id.round)
        roundView.text = String.format( getString(R.string.round_number),roundNumber)

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
                vibrator.vibrate(rightGuessVibrationEffect)
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
                    roundView.text = String.format( getString(R.string.round_number),roundNumber)
                }
                val t = getString(R.string.left_words_text)
                val i = game.leftWords()
                val s = t.format(i)
                leftNumOfWords.text = s
            }
        }

        findViewById<ImageButton>(R.id.wrong_button).setOnClickListener {
            if(mTimerRunning){
                vibrator.vibrate(wrongGuessVibrationEffect)
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
            vibrator.vibrate(roundEndVibrationEffect)
            resetTimer()
            game.teamSwitch()
            updateTeamsViews()
            game.startRound()
            word.text = game.drawPetek()
        }
        else roundNumber = 4
        //popup window
        resetTimer()
        createRoundAlertDialog(roundNumber)
    }

    fun updateTeamsViews() {
        val idleTextColor= "#" + Integer.toHexString(ResourcesCompat.getColor(resources, R.color.text_color, null))
        val currentTextColor = "#" + Integer.toHexString(ResourcesCompat.getColor(resources, R.color.category_button_selected, null))
        team1View.typeface = if (game.currentTeam === team1) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        team1View.setTextColor(Color.parseColor(if (game.currentTeam === team1) currentTextColor else idleTextColor))
        team2View.typeface = if (game.currentTeam === team2) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        team2View.setTextColor(Color.parseColor(if (game.currentTeam === team2) currentTextColor else idleTextColor))
    }

    //timer functions
    private fun startTimer() {
        timer = object : CountDownTimer(mTimeLeftInMillis, 100) {
            override fun onTick(millisUntilFinished: Long) {
                if((millisUntilFinished / 1000) != mTimeLeftInMillis ){
                    mTimeLeftInMillis = millisUntilFinished
                    timerView.text = (mTimeLeftInMillis / 1000).toString()
                }
            }


            @RequiresApi(Build.VERSION_CODES.O)
            override fun onFinish() {
                resetTimer()
                game.teamSwitch()
                updateTeamsViews()
                vibrator.vibrate(timerEndVibrationEffect)
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

    private fun pauseTimer(){
        timer.cancel()
        mTimerRunning = false
    }

    //dialog functions
    private fun createNewAlertDialog(){
        builder =AlertDialog.Builder(this)
        val view = View.inflate(this@Game,R.layout.alert_dialog_stages,null)
        builder.setView(view)
        dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
    }

    @SuppressLint("SetTextI18n")
    fun createStopAlertDialog(){
        createNewAlertDialog()
        dialog.findViewById<TextView>(R.id.upperText)?.text = String.format( getString(R.string.round_number),roundNumber)
        dialog.findViewById<TextView>(R.id.lowerText)?.text = String.format( getString(R.string.playing_team),game.currentTeam.teamNumber)

    }

    @SuppressLint("ResourceType")
    private fun createRoundAlertDialog(round:Int){
        createNewAlertDialog()
        val titleView = dialog.findViewById<TextView>(R.id.upperText)
        val descriptionView = dialog.findViewById<TextView>(R.id.lowerText)
        val dialogButton = dialog.findViewById<Button>(R.id.alertDialogButton)

        val title: String = when (roundNumber) {
            1 -> getString(R.string.level_one)
            2 -> getString(R.string.level_two)
            3 -> getString(R.string.level_three)
            else -> getString(R.string.game_over)
        }

        val description: String = when(roundNumber) {
            1 -> getString(R.string.round1text)
            2 -> getString(R.string.round2text)
            3 -> getString(R.string.round3text)
            else -> String.format( getString(R.string.endGameText), if (team1.getScore() > team2.getScore()) 1 else 2)
        }.format(game.currentTeam.teamNumber)


        titleView!!.text = title
        descriptionView!!.text = description

        if (round > 3) {
            // changing dialogButton function
            dialogButton!!.text = "התחל משחק חדש"
            dialogButton.setOnClickListener {
                val intent = Intent(this, CategoriesMenu::class.java)
                intent.putExtra("numberOfWords", numberOfWords)
                intent.putExtra("timePerRound", START_TIME_IN_MILLIS)
                startActivity(intent)
            }
        }
    }

    fun dialogButton(view: View){
        dialog.dismiss()
        startTimer()
    }

    fun startNewGame(view:View){
        view!!.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        val intent = Intent(this, CategoriesMenu::class.java)
        intent.putExtra("numberOfWords", numberOfWords)
        intent.putExtra("timePerRound", START_TIME_IN_MILLIS)
        startActivity(intent)
    }

}