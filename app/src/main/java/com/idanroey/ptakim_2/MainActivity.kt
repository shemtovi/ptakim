package com.idanroey.ptakim_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.HapticFeedbackConstants
import android.view.View

class MainActivity : AppCompatActivity() {
//    private val db: WordDatabase by lazy {
//        Room.databaseBuilder(this, WordDatabase::class.java, WORDS_DATABASE)
//            .allowMainThreadQueries()
//            .createFromAsset(WORDS_ASSET)
//            .build()
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun start (view: View?) {
        view!!.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        val intent = Intent(this, howMuchCards::class.java)
        startActivity(intent)
    }

    fun hideScreen (view: View?) {
        view!!.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        val intent = Intent(this, hideWords::class.java)
        startActivity(intent)
    }
}