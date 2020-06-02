package com.example.mot.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.mot.db.entity.Category

@Dao
interface CategoryDao: BaseDao<Category> {

    @Query("SELECT * from category")
    fun getAllCategory() : LiveData<MutableList<Category>>
}