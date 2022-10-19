package com.idanroey.ptakim_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.TextView


class howMuchCards : AppCompatActivity() {
    private var numOfWords = 20
    private var timePerRound: Long = 60000
    private lateinit var textView: TextView
    private lateinit var timeView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_much_cards)
        textView = findViewById(R.id.numOfWords)
        timeView = findViewById(R.id.time)
    }

    fun minusWords (view: View?) {
        view!!.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        if (numOfWords > 5) {
            numOfWords -= 5
            textView.text = numOfWords.toString()
        }
    }

    fun minusTime (view: View?) {
        view!!.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        if (timePerRound > 30000) {
            timePerRound -= 30000
            timeView.text = (timePerRound / 1000).toString()
        }
    }

    fun plusWords (view: View?) {
        view!!.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        if (numOfWords < 50) {
            numOfWords += 5
            textView.text = numOfWords.toString()
        }
    }

    fun plusTime (view: View?) {
        view!!.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        if (timePerRound < 90000) {
            timePerRound += 30000
            timeView.text = (timePerRound / 1000).toString()
        }
    }

    fun start (view: View?) {
        view!!.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        val intent = Intent(this, CategoriesMenu::class.java)
        intent.putExtra("numberOfWords", numOfWords)
        intent.putExtra("timePerRound", timePerRound)
        startActivity(intent)
    }

}