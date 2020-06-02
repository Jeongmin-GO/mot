package com.example.mot.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mot.db.dao.CategoryDao
import com.example.mot.db.dao.MenuDao
import com.example.mot.db.entity.Category
import com.example.mot.db.entity.Menu


@Database(entities = [Menu::class, Category::class], version = 2)
abstract class MotDatabase: RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun menuDao() : MenuDao


    companion object{
        private var instance: MotDatabase? = null

        fun getInstance(context: Context): MotDatabase {
            return instance ?: synchronized(this){
                instance
                    ?: buildDatabase(
                        context
                    )
            }
        }

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }

        private fun buildDatabase(context: Context): MotDatabase {
            return Room.databaseBuilder(context.applicationContext, MotDatabase::class.java, "app_db")
                .addMigrations(MIGRATION_1_2)
                .build()
        }
    }

}