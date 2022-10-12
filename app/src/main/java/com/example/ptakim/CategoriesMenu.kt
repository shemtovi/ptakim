package com.example.ptakim

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ptakim.Game

class CategoriesMenu : AppCompatActivity() {
    protected var NumOfWorld: Int? = null
    protected var NumOfCategories = 0
    protected var Categories = BooleanArray(8)
    protected var place: Button? = null
    protected var movie: Button? = null
    protected var company: Button? = null
    protected var `object`: Button? = null
    protected var culture_leaders: Button? = null
    protected var sport: Button? = null
    protected var nature: Button? = null
    protected var scientists: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories_menu)
        NumOfWorld = intent.getIntExtra("NumOfWorlds", 20)
        place = findViewById(R.id.place)
        movie = findViewById(R.id.movie)
        company = findViewById(R.id.company)
        `object` = findViewById(R.id.`object`)
        culture_leaders = findViewById(R.id.culture_leaders)
        sport = findViewById(R.id.sport)
        nature = findViewById(R.id.nature)
        scientists = findViewById(R.id.scientists)
        for (i in 0..7) Categories[i] = false
    }

    fun place(view: View?) {
        if (Categories[0] == false) {
            Categories[0] = true
            place!!.setBackgroundColor(Color.parseColor("#ff669900"))
            NumOfCategories++
        } else {
            Categories[0] = false
            place!!.setBackgroundColor(Color.parseColor("#B00020"))
            NumOfCategories--
        }
    }

    fun movie(view: View?) {
        if (Categories[1] == false) {
            Categories[1] = true
            movie!!.setBackgroundColor(Color.parseColor("#ff669900"))
            NumOfCategories++
        } else {
            Categories[1] = false
            movie!!.setBackgroundColor(Color.parseColor("#B00020"))
            NumOfCategories--
        }
    }

    fun company(view: View?) {
        if (Categories[2] == false) {
            Categories[2] = true
            company!!.setBackgroundColor(Color.parseColor("#ff669900"))
            NumOfCategories++
        } else {
            Categories[2] = false
            company!!.setBackgroundColor(Color.parseColor("#B00020"))
            NumOfCategories--
        }
    }

    fun `object`(view: View?) {
        if (Categories[3] == false) {
            Categories[3] = true
            `object`!!.setBackgroundColor(Color.parseColor("#ff669900"))
            NumOfCategories++
        } else {
            Categories[3] = false
            `object`!!.setBackgroundColor(Color.parseColor("#B00020"))
            NumOfCategories--
        }
    }

    fun culture_leaders(view: View?) {
        if (Categories[4] == false) {
            Categories[4] = true
            culture_leaders!!.setBackgroundColor(Color.parseColor("#ff669900"))
            NumOfCategories++
        } else {
            Categories[4] = false
            culture_leaders!!.setBackgroundColor(Color.parseColor("#B00020"))
            NumOfCategories--
        }
    }

    fun sport(view: View?) {
        if (Categories[5] == false) {
            Categories[5] = true
            sport!!.setBackgroundColor(Color.parseColor("#ff669900"))
            NumOfCategories++
        } else {
            Categories[5] = false
            sport!!.setBackgroundColor(Color.parseColor("#B00020"))
            NumOfCategories--
        }
    }

    fun nature(view: View?) {
        if (Categories[6] == false) {
            Categories[6] = true
            nature!!.setBackgroundColor(Color.parseColor("#ff669900"))
            NumOfCategories++
        } else {
            Categories[6] = false
            nature!!.setBackgroundColor(Color.parseColor("#B00020"))
            NumOfCategories--
        }
    }

    fun scientists(view: View?) {
        if (Categories[7] == false) {
            Categories[7] = true
            scientists!!.setBackgroundColor(Color.parseColor("#ff669900"))
            NumOfCategories++
        } else {
            Categories[7] = false
            scientists!!.setBackgroundColor(Color.parseColor("#B00020"))
            NumOfCategories--
        }
    }

    fun Start(view: View?) {
        if (NumOfCategories != 0) {
            val intent = Intent(this, Game::class.java)
            intent.putExtra("NumOfWorlds", NumOfWorld)
            intent.putExtra("NumOfCategories", NumOfCategories)
            intent.putExtra("Categories", Categories)
            startActivity(intent)
        }
    }

    fun return_button(view: View?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}