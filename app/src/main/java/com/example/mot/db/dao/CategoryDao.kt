package com.example.mot.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.mot.db.entity.Category

@Dao
interface CategoryDao: BaseDao<Category> {

    @Query("SELECT * from category")
    fun getAllCategory() : LiveData<MutableList<Category>>

    @Query("SELECT * from category WHERE name = :catName")
    fun getCategoryIdByName(catName: String) : LiveData<Category>
}