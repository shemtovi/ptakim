package com.idanroey.ptakim_2

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.content.Intent
import android.view.HapticFeedbackConstants
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun start (view: View?) {
        view!!.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        val intent = Intent(this, howMuchCards::class.java)
        startActivity(intent)
    }
}