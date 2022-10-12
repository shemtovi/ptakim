package com.example.ptakim

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var NumOfWords = 20
    private var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.NumOfWorlds)
    }

    operator fun minus(view: View?) {
        if (NumOfWords > 20) {
            NumOfWords = NumOfWords - 5
            textView!!.text = NumOfWords.toString()
        }
    }

    fun plas(view: View?) {
        if (NumOfWords <= 35) {
            NumOfWords = NumOfWords + 5
            textView!!.text = NumOfWords.toString()
        }
    }

    fun Start(view: View?) {
        val intent = Intent(this, CategoriesMenu::class.java)
        intent.putExtra("NumOfWorlds", NumOfWords)
        startActivity(intent)
    }
}