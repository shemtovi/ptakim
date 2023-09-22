package com.idanroey.ptakim_2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.idanroey.ptakim_2.utils.Constants.WORDS_ASSET
import com.idanroey.ptakim_2.utils.Constants.WORDS_DATABASE

@Database(entities = [WordEntity::class, CategoryEntity::class], version = 1, exportSchema = true)
abstract class WordsDatabase : RoomDatabase(){
    abstract fun dao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: WordsDatabase? = null
        fun getDatabase(context: Context): WordsDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context,WordsDatabase::class.java, WORDS_DATABASE)
                            .allowMainThreadQueries()
                            .createFromAsset(WORDS_ASSET)
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}