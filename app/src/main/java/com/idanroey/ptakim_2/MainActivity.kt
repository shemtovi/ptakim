package com.idanroey.ptakim_2

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.HapticFeedbackConstants
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("conn", "main call start")
            val databaseHelper = DatabaseHelper(this@MainActivity)
            Log.d("conn", "main call end")
        }

    }

    fun start (view: View?) {
        view!!.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        val intent = Intent(this, howMuchCards::class.java)
        startActivity(intent)
    }
}