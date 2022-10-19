package com.idanroey.ptakim_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.Button

class CategoriesMenu : AppCompatActivity(), View.OnClickListener  {

    private val categories = mutableMapOf(
        R.id.places to false,
        R.id.movies to false,
        R.id.companies to false,
        R.id.objects to false,
        R.id.culture_leaders to false,
        R.id.sports to false,
        R.id.nature to false,
        R.id.scientists to false,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories_menu)

        val places = findViewById<Button>(R.id.places)
        val movies = findViewById<Button>(R.id.movies)
        val companies = findViewById<Button>(R.id.companies)
        val objects = findViewById<Button>(R.id.objects)
        val culture_leaders = findViewById<Button>(R.id.culture_leaders)
        val sports = findViewById<Button>(R.id.sports)
        val nature = findViewById<Button>(R.id.nature)
        val scientists = findViewById<Button>(R.id.scientists)

        places.setOnClickListener(this)
        movies.setOnClickListener(this)
        companies.setOnClickListener(this)
        objects.setOnClickListener(this)
        culture_leaders.setOnClickListener(this)
        sports.setOnClickListener(this)
        nature.setOnClickListener(this)
        scientists.setOnClickListener(this)

    }

    private fun updateCategories(view: View) {
        categories[view.id] = !categories[view.id]!!
        view.isActivated = !view.isActivated
    }

    fun returnButton(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    fun start(view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        if (categories.values.count { it } > 0) {
            val numberOfWords = this.intent.getIntExtra("numberOfWords", 20)
            val timePerRound = this.intent.getLongExtra("timePerRound", 60000)
            val filteredCategories = categories.filter { (_, bool) -> bool}.keys.toIntArray()
            val intent = Intent(this, Game::class.java)
            intent.putExtra("filteredCategories", filteredCategories)
            intent.putExtra("numberOfWords", numberOfWords)
            intent.putExtra("timePerRound", timePerRound)
            startActivity(intent)
        }
    }

    override fun onClick(view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        if (view.id in categories.keys) updateCategories(view)
        findViewById<Button>(R.id.start).isEnabled = categories.values.count { it } > 0
    }
}