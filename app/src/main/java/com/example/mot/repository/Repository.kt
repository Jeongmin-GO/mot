package com.example.mot.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mot.db.MotDatabase
import com.example.mot.db.dao.CategoryDao
import com.example.mot.db.dao.MenuDao
import com.example.mot.db.entity.Category
import com.example.mot.db.entity.Menu
import io.reactivex.Completable

class Repository (application: Application) {
    val db =  MotDatabase.getInstance(application)

    //CATEGORY
    private val categoryDao: CategoryDao by lazy {
        db.categoryDao()
    }

    private val categories: LiveData<MutableList<Category>> by lazy {
        categoryDao.getAllCategory()
    }

    fun getCategory() : LiveData<MutableList<Category>> = categories
    fun getCategoryIdByName(catName: String) : LiveData<Category> = categoryDao.getCategoryIdByName(catName)
    fun insertCategory(cat: Category): Completable = categoryDao.insert(cat)
    fun deleteCategory(cat: Category): Completable = categoryDao.delete(cat)

    //MENU
    private val menuDao: MenuDao by lazy { db.menuDao() }

    private val menus: LiveData<MutableList<Menu>> by lazy { menuDao.getAllMenu() }

    fun getAllMenu() : LiveData<MutableList<Menu>> = menus
    fun getMenuByCategory(cat: Long) : LiveData<MutableList<Menu>> = menuDao.getMenuByCategory(cat)
    fun getMenuById(menuID: Long) : LiveData<MutableList<Menu>> = menuDao.getMenuById(menuID)
    fun insertCategory(menu: Menu): Completable = menuDao.insert(menu)
    fun deleteCategory(menu: Menu): Completable = menuDao.insert(menu)


}