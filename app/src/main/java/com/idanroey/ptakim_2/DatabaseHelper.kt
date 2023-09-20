package com.idanroey.ptakim_2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DatabaseHelper(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "words.db"
        private const val DATABASE_VERSION = 1

    }

    private val dbFile: File? = context.getDatabasePath(DATABASE_NAME)


    init {
        Log.d("conn", "start init")
        if (!(dbFile?.exists()!!)) {
            Log.d("conn", "true")
            try {
                Log.d("conn", "check file")
                val checkDB = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null)
                checkDB?.close()
                Log.d("conn", "start copy")
                copyDatabase(dbFile)
                Log.d("conn", "end copy")
            } catch (e: IOException) {
                throw RuntimeException("Error copying database")
            }
        } else {
            Log.d("conn", "file was found(?)")
        }
    }

    private fun copyDatabase(dbFile: File) {
        val origin = context.assets.open(DATABASE_NAME)
        val os = FileOutputStream(dbFile)

        val buffer = ByteArray(1024)
        while (origin.read(buffer) > 0) {
            os.write(buffer)
            Log.d("conn", "writing>>")
        }

        os.flush()
        os.close()
        origin.close()
        Log.d("conn", "completed..")
    }

    override fun onCreate(db: SQLiteDatabase) {}

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun getNWordsByCategory(categoryId: Int, limit: Int): List<String> {
        val wordList = mutableListOf<String>()
        val db = readableDatabase
        val selection = "category_id = ? AND is_active = 1"
        val selectionArgs = arrayOf(categoryId.toString())

        val cursor = db.query(
            "words", // Table name
            arrayOf("word_text"), // Columns to retrieve
            selection, // WHERE clause
            selectionArgs, // Values for the WHERE clause
            null,
            null,
            "RANDOM()",
            limit.toString()
        )

        cursor.use { cursor ->
            while (cursor.moveToNext()) {
                val wordText = cursor.getString(cursor.getColumnIndexOrThrow("word_text"))
                wordList.add(wordText)
            }
        }

        return wordList
    }
}
