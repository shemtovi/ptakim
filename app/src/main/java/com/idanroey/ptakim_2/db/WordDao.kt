package com.idanroey.ptakim_2.db

import android.util.Log
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.idanroey.ptakim_2.utils.Constants.WORDS_TABLE

@Dao
interface WordDao {
    @Query("""
        SELECT word_text 
        FROM $WORDS_TABLE
        WHERE category_id = :categoryId
         AND is_active = 1
        ORDER BY RANDOM()
        LIMIT :limit
    """)
    fun getNWordsByCategory(categoryId: Int, limit: Int): Array<String>

    @Query("""
        SELECT *
        FROM $WORDS_TABLE
        WHERE category_id = :categoryId
        ORDER BY word_text
    """)
    fun getAllWordsForCategory(categoryId: Int): Array<WordEntity>

    @Query("""
        SELECT *
        FROM $WORDS_TABLE
        WHERE word_id = :wordId
    """)
    fun getWord(wordId: Int): Array<WordEntity>

    @Update(entity = WordEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun updateWord(word: WordEntity)
}