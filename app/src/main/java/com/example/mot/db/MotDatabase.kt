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


@Database(entities = [Menu::class, Category::class], version = 3)
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

        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE category ADD COLUMN dicKor TEXT")
                database.execSQL("ALTER TABLE category ADD COLUMN dicChb TEXT")
                database.execSQL("ALTER TABLE category ADD COLUMN dicChg TEXT")
                database.execSQL("ALTER TABLE category ADD COLUMN dicEn TEXT")
                database.execSQL("ALTER TABLE category ADD COLUMN dicJpe TEXT")
                database.execSQL("ALTER TABLE category ADD COLUMN dicJph TEXT")
                database.execSQL("ALTER TABLE menu ADD COLUMN contents VARCHAR2(1000)")
                database.execSQL("ALTER TABLE menu ADD COLUMN ingredients VARCHAR2(1000)")
            }
        }

        private fun buildDatabase(context: Context): MotDatabase {
            return Room.databaseBuilder(context.applicationContext, MotDatabase::class.java, "app_db")
                .addMigrations(MIGRATION_2_3)
                .build()
        }
    }

}