package com.example.ptakim

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Game : AppCompatActivity() {
    private var textView: TextView? = null
    private var player1_score = 0
    private var player2_score = 0
    private var player1_ScoreBoard: TextView? = null
    private var player2_ScoreBoard: TextView? = null
    private var word: TextView? = null
    private var WhoPlay = 1
    private var stage = 1
    protected var FirstMaxHeap: PriorityQueue<Node>? = null
    protected var SecondMaxHeap: PriorityQueue<Node>? = null
    protected var ThirdMaxHeap: PriorityQueue<Node>? = null
    protected var CurrentMaxHeap: PriorityQueue<Node>? = null
    protected var NumOfWords = 0
    protected var NumOfCategories = 0
    protected var Categories: BooleanArray? = null
    private val Comparator = NodeComparator()
    protected var bank: WordBank? = null
    private var correct_button: ImageButton? = null
    private var error_button: ImageButton? = null

    //popup window elements
    private var dialogBuilder: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null
    private var MainText: TextView? = null
    private var dagim: Button? = null

    //timer elements
    private var timer: TextView? = null
    private var stop_button: ImageButton? = null
    private var mCountDownTimer: CountDownTimer? = null
    private var mTimerRunning = false
    private var mTimeleftinmillis = START_TIME_IN_MILLIS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)
        textView = findViewById(R.id.textView)
        NumOfWords = intent.getIntExtra("NumOfWorlds", 20)
        NumOfCategories = intent.getIntExtra("NumOfCategories", 1)
        Categories = intent.getBooleanArrayExtra("Categories")
        bank = WordBank(NumOfWords, NumOfCategories, Categories)
        FirstMaxHeap = PriorityQueue(NumOfWords, Comparator)
        SecondMaxHeap = PriorityQueue(NumOfWords, Comparator)
        ThirdMaxHeap = PriorityQueue(NumOfWords, Comparator)
        CurrentMaxHeap = FirstMaxHeap
        timer = findViewById(R.id.timer)
        player1_ScoreBoard = findViewById(R.id.player1_ScoreBoard)
        player2_ScoreBoard = findViewById(R.id.player2_ScoreBoard)
        word = findViewById(R.id.word)
        correct_button = findViewById(R.id.correct_button)
        error_button = findViewById(R.id.error_button)
        stop_button = findViewById(R.id.stop_button)
        CreatingMaxHeaps()
        CreateNewPopUpWindow()
    }

    fun CreatingMaxHeaps() {
        val selectedWords = WordBank.WordsOfCategory()
        for (t in 0..7) {
            if (Categories!![t]) {
                for (str in selectedWords[t]) {
                    FirstMaxHeap!!.add(Node(str))
                    SecondMaxHeap!!.add(Node(str))
                    ThirdMaxHeap!!.add(Node(str))
                }
            }
        }
    }

    //popup window
    fun CreateNewPopUpWindow() {
        dialogBuilder = AlertDialog.Builder(this)
        val PopUpView = layoutInflater.inflate(R.layout.popup, null)
        MainText = PopUpView.findViewById(R.id.MainText)
        dagim = PopUpView.findViewById(R.id.dagim)
        dialogBuilder!!.setView(PopUpView)
        dialog = dialogBuilder!!.create()
        dialog!!.show()
        dialog!!.setCanceledOnTouchOutside(false)
        /*
        final PopupWindow popUpReject = new PopupWindow(popuplayoutReject, WindowManager.LayoutParams.FILL_PARENT,
                            WindowManager.LayoutParams.FILL_PARENT, false);
                    popUpReject.setOutsideTouchable(false);
         */
    }

    fun dagim_start(view: View?) {
        dialog!!.dismiss()
        startTimer()
        nextWord()
    }

    fun Home_Button(view: View?) {
        //go back to home activity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun stop_button(view: View?) {
        //stop -> popup show
        CreateNewPopUpWindow()
        MainText!!.text = "שלב מס':$stage             והתור של קבוצה מס':$WhoPlay"
        stopTimer()
    }

    //timer function
    fun startTimer() {
        mCountDownTimer = object : CountDownTimer(mTimeleftinmillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeleftinmillis = millisUntilFinished
                updateCountDownTimer()
            }

            override fun onFinish() {
                mTimerRunning = false
                resetTimer()
                CreateNewPopUpWindow()
                changeWhoIsPlaying()
                MainText!!.text = "שלב מס':$stage             והתור של קבוצה מס':$WhoPlay"
            }
        }.start()
        mTimerRunning = true
    }

    fun stopTimer() {
        if (mTimerRunning) {
            mCountDownTimer!!.cancel()
            mTimerRunning = false
        }
    }

    fun resetTimer() {
        mTimeleftinmillis = START_TIME_IN_MILLIS
        updateCountDownTimer()
    }

    private fun updateCountDownTimer() {
        val seconds = (mTimeleftinmillis / 1000).toInt() % 60
        timer!!.text = seconds.toString()
    }

    fun correct_ans(view: View?) {
        if (WhoPlay == 1) {
            player1_score = player1_score + 2
            player1_ScoreBoard!!.text = player1_score.toString()
        } else {
            player2_score = player2_score + 2
            player2_ScoreBoard!!.text = player2_score.toString()
        }
        nextWord()
    }

    fun false_ans(view: View?) {
        if (WhoPlay == 1) {
            player1_score = player1_score - 1
            player1_ScoreBoard!!.text = player1_score.toString()
        } else {
            player2_score = player2_score - 1
            player2_ScoreBoard!!.text = player2_score.toString()
        }
        val false_word = word!!.text.toString()
        if (!CurrentMaxHeap!!.isEmpty()) {
            CurrentMaxHeap!!.add(
                Node(
                    false_word,
                    CurrentMaxHeap!!.peek().num
                )
            ) //insure that the word wont came out again immediately
        } else {
            CurrentMaxHeap!!.add(Node(false_word))
        }
        nextWord()
    }

    private fun nextWord() {
        var str = ""
        if (!CurrentMaxHeap!!.isEmpty()) {
            val node = CurrentMaxHeap!!.poll()
            str = node.str
            textView!!.text = node.num.toString()
        } else { //if the part done
            changeMaxHeap()
        }
        word!!.text = str
    }

    private fun changeMaxHeap() {
        if (!SecondMaxHeap!!.isEmpty()) { //if stage 2 do not began it
            CurrentMaxHeap = SecondMaxHeap
            stage = 2
            stopTimer()
            resetTimer()
            changeWhoIsPlaying()
            CreateNewPopUpWindow()
            MainText!!.text =
                "שלב שני מתחיל:    מילה אחת בלבד לתאור.                 הקבוצה המתחילה היא קבוצה מס': $WhoPlay"


            //popup stage 2 begins
        } else if (!ThirdMaxHeap!!.isEmpty()) { //if stage 3 do not began it
            CurrentMaxHeap = ThirdMaxHeap
            stage = 3
            stopTimer()
            resetTimer()
            changeWhoIsPlaying()
            CreateNewPopUpWindow()
            MainText!!.text =
                "שלב שלישי מתחיל:                  פנטומימה בלבד לתאור.             הקבוצה המתחילה היא קבוצה מס': $WhoPlay"

            //popup stage 3 begins
        } else { //stage 3 finished
            //popup winner is...
            correct_button!!.isEnabled = false
            error_button!!.isEnabled = false
            stopTimer()
            resetTimer()
            CreateNewPopUpWindow()
            dagim!!.setOnClickListener { view -> StartNewGame(view) }
            dagim!!.text = "משחק חדש"
            if (player1_score - player2_score > 0) MainText!!.text =
                "הקבוצה המנצחת היא: קבוצה מס' 1!!!!" else if (player1_score - player2_score < 0) MainText!!.text =
                "הקבוצה המנצחת היא: קבוצה מס' 2!!!!" else MainText!!.text =
                "דבר כזה לא קרה מעולם המנצח הוא זה הנהנה מהדרך"
        }
    }

    fun StartNewGame(view: View?) {
        val intent = intent
        finish()
        startActivity(intent)
    }

    private fun changeWhoIsPlaying() {
        WhoPlay = if (WhoPlay == 1) 2 else 1
    }

    companion object {
        private const val START_TIME_IN_MILLIS: Long = 60000
    }
}