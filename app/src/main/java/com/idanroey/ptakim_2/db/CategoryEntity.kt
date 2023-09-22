package com.idanroey.ptakim_2.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.idanroey.ptakim_2.utils.Constants.CATEGORIES_TABLE

@Entity(tableName = CATEGORIES_TABLE)
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    @ColumnInfo(name = "category_name")
    val categoryName: String,
)
