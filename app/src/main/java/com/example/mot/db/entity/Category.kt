package com.example.mot.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey val id : Long = -1,
    val name : String? = ""
)