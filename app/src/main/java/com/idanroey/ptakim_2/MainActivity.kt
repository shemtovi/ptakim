package com.idanroey.ptakim_2

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.room.Room
import com.idanroey.ptakim_2.db.WordDatabase
import com.idanroey.ptakim_2.utils.Constants.WORDS_ASSET
import com.idanroey.ptakim_2.utils.Constants.WORDS_DATABASE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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