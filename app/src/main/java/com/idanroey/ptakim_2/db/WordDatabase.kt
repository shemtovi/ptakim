package com.idanroey.ptakim_2.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WordEntity::class, CategoryEntity::class], version = 2)
abstract class WordDatabase : RoomDatabase(){
    abstract fun dao(): WordDao
}