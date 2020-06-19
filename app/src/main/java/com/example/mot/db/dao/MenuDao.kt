package com.example.mot.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.mot.db.entity.Menu

@Dao
interface MenuDao : BaseDao<Menu> {

    @Query("SELECT * from menu")
    fun getAllMenu() : LiveData<MutableList<Menu>>

    @Query("SELECT * from menu WHERE category = :catNum")
    fun getMenuByCategory(catNum: Long) : LiveData<MutableList<Menu>>

    @Query("SELECT * from menu WHERE dicKor = :name")
    fun getMenuByKor(name: String) : LiveData<MutableList<Menu>>

    @Query("SELECT * from menu WHERE id = :menuId")
    fun getMenuById(menuId: Long) : LiveData<Menu>
}