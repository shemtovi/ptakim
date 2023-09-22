package com.idanroey.ptakim_2.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.idanroey.ptakim_2.utils.Constants.WORDS_TABLE


@Entity(
    tableName = WORDS_TABLE,
    foreignKeys = [ForeignKey(
        entity = CategoryEntity::class,
        parentColumns = arrayOf("category_id"),
        childColumns = arrayOf("category_id"),
    )]
)
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "word_id")
    val wordId: Int,
    @ColumnInfo(name = "word_text")
    val wordText: String,
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    @ColumnInfo(name = "is_active", defaultValue = "1")
    val isActive: Int,
)

data class WordInfo(
    @ColumnInfo(name = "word_id")
    val wordId: Int,
    @ColumnInfo(name = "word_text")
    val wordText: String,
    @ColumnInfo(name = "is_active", defaultValue = "1")
    val isActive: Int,
)
