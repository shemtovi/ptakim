package com.idanroey.ptakim_2

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.idanroey.ptakim_2.db.WordDatabase
import com.idanroey.ptakim_2.db.WordEntity
//import com.idanroey.ptakim_2.db.WordInfo
import com.idanroey.ptakim_2.utils.Constants.WORDS_ASSET
import com.idanroey.ptakim_2.utils.Constants.WORDS_DATABASE

class hideWords : AppCompatActivity() {
//    private var wordList: List<Triple<Int, String, Boolean>>? = null

    private val wordDb: WordDatabase by lazy {
        Room.databaseBuilder(this, WordDatabase::class.java, WORDS_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset(WORDS_ASSET)
            .build()
    }


    private val categoriesId = mapOf(
        "Places" to 1,
        "Movies" to 2,
        "Companies" to 3,
        "Objects" to 4,
        "Culture Leaders" to 5,
        "Sports" to 6,
        "Nature" to 7,
        "Scientists" to 8,
        "Hamisi Salof" to 9,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hide_words)

        // Initialize your buttons
        val placesButton = findViewById<Button>(R.id.places)
        val moviesButton = findViewById<Button>(R.id.movies)
        val companiesButton = findViewById<Button>(R.id.companies)
        val objectsButton = findViewById<Button>(R.id.objects)
        val cultureLeadersButton = findViewById<Button>(R.id.cultureLeaders)
        val sportsButton = findViewById<Button>(R.id.sports)
        val natureButton = findViewById<Button>(R.id.nature)
        val scientistsButton = findViewById<Button>(R.id.scientists)
        val hamisiSalofButton = findViewById<Button>(R.id.hamisiSalof)

        // Set click listeners for each button to show a pop-up dialog
        setButtonClickAction(placesButton, "Places")
        setButtonClickAction(moviesButton, "Movies")
        setButtonClickAction(companiesButton, "Companies")
        setButtonClickAction(objectsButton, "Objects")
        setButtonClickAction(cultureLeadersButton, "Culture Leaders")
        setButtonClickAction(sportsButton, "Sports")
        setButtonClickAction(natureButton, "Nature")
        setButtonClickAction(scientistsButton, "Scientists")
        setButtonClickAction(hamisiSalofButton, "Hamisi Salof")

        // Set click listener for the start button (You can implement your logic here)
        val startButton = findViewById<Button>(R.id.start)
        startButton.setOnClickListener {
            // Add your code for the start button here
        }
    }

    private fun setButtonClickAction(button: Button, category: String) {
        button.setOnClickListener {
            // Display a pop-up dialog with a list of words for the selected category
            showWordListDialog(category)
        }
    }

    private fun showWordListDialog(category: String) {
        // Replace the following with your actual list of words from the database
        val wordList = wordDb.dao().getAllWordsForCategory(categoriesId[category]!!)

        // Create a dialog builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Words in $category") // Set the dialog title

        val wordTexts = wordList.map { it.wordText }.toTypedArray()


        // Create an ArrayAdapter with a custom view to display the words with strikethrough for hidden words
        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wordTexts) {
            override fun getView(position: Int, convertView: View?, parent: android.view.ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val word = wordList[position]
                if (word.isActive == 0) {
                    // Apply strikethrough for hidden words
                    (view as TextView).paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    // Remove strikethrough for active words
                    (view as TextView).paintFlags = 0
                }
                return view
            }
        }


        val listView = ListView(this)
        listView.adapter = adapter

        // Set the dialog content as the ListView
        builder.setView(listView)

        val dialog = builder.create()
        dialog.show()

        // Set a click listener for the ListView items to handle word clicks
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedWord = wordList[position]
            Log.d("hidee", "sel: $selectedWord is_ac: ${selectedWord.isActive}")
            if (selectedWord.isActive == 1) {
                // Word is active, call the hideWord function with the word's ID
                hideWord(wordList, listView, selectedWord, position)
            } else {
                unhideWord(wordList, listView, selectedWord, position)
            }
        }

    }

    private fun hideWord(wordList: Array<WordEntity>, listView: ListView, word: WordEntity, position: Int) {
        Log.d("hidee", "hiding word ${word.wordText}")
//        wordDb.dao().hideWord(wordId)
        val newWord = WordEntity(word.wordId, word.wordText, word.categoryId, 0)
        Log.d("hidee", "new word: $newWord")
        wordDb.dao().updateWord(newWord)

        val updated = wordDb.dao().getWord(newWord.wordId)[0]
        Log.d("hidee", updated.toString())

        // Update the active status in the wordList
        wordList[position] = newWord

        // Notify the adapter that the data has changed, so it will update the view
        val adapter = (listView.adapter as? ArrayAdapter<String>)
        adapter?.notifyDataSetChanged()
    }

    private fun unhideWord(wordList: Array<WordEntity>, listView: ListView, word: WordEntity, position: Int) {
//        Log.d("hidee", "unhiding word $wordId")
//        wordDb.dao().unhideWord(wordId)
//        val newWord = word.copy(isActive = 1)
        Log.d("hidee", "unhiding word ${word.wordText}")
        val newWord = WordEntity(word.wordId, word.wordText, word.categoryId, 1)
        Log.d("hidee", "new word: $newWord")
        wordDb.dao().updateWord(newWord)


        val updated = wordDb.dao().getWord(newWord.wordId)[0]
        Log.d("hidee", updated.toString())


        // Update the active status in the wordList
        wordList[position] = wordList[position].copy(isActive = 1)

        // Notify the adapter that the data has changed, so it will update the view
        val adapter = (listView.adapter as? ArrayAdapter<String>)
        adapter?.notifyDataSetChanged()
    }

    fun returnButton(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    // Add other methods and logic as needed for your activity
}