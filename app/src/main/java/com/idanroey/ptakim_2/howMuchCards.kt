package com.idanroey.ptakim_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class howMuchCards : AppCompatActivity() {
    private var numOfWords = 20
    private var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_much_cards)
        textView = findViewById(R.id.numOfWords)
    }

    operator fun minus(view: View?) {
        if (numOfWords > 20) {
            numOfWords -= 5
            textView!!.text = numOfWords.toString()
        }
    }

    fun plus (view: View?) {
        if (numOfWords < 50) {
            numOfWords += 5
            textView!!.text = numOfWords.toString()
        }
    }

    fun start (view: View?) {
        val intent = Intent(this, CategoriesMenu::class.java)
        intent.putExtra("numberOfWords", numOfWords)
        startActivity(intent)
    }

}