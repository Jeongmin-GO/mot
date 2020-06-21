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

    @Query("SELECT * from menu WHERE dicKor LIKE  '%' || :name || '%'")
    fun getMenuByKor(name: String) : LiveData<MutableList<Menu>>

    @Query("SELECT * from menu WHERE dicEn LIKE '%' ||  :name || '%'")
    fun getMenuByEng(name: String) : LiveData<MutableList<Menu>>

    @Query("SELECT * from menu WHERE dicChb LIKE '%' || :name || '%' or dicChg LIKE '%' || :name || '%'")
    fun getMenuByCha(name: String) : LiveData<MutableList<Menu>>

    @Query("SELECT * from menu WHERE dicJpe LIKE '%' || :name || '%' or dicJph LIKE '%' || :name || '%'")
    fun getMenuByJp(name: String) : LiveData<MutableList<Menu>>

    @Query("SELECT * from menu WHERE id = :menuId")
    fun getMenuById(menuId: Long) : LiveData<Menu>
}