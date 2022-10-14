package com.idanroey.ptakim_2

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private var numOfWords = 20
    private var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.numOfWords)
    }

    operator fun minus(view: View?) {
        if (numOfWords > 20) {
            numOfWords -= 5
            textView!!.text = numOfWords.toString()
        }
    }

    fun plus (view: View?) {
        if (numOfWords <= 35) {
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