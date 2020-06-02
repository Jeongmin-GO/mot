package com.example.mot.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.Completable

interface BaseDao<Entity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Entity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Array<Entity>): Completable

    @Update
    fun update(entity: Entity): Completable

    @Delete
    fun delete(entity: Entity): Completable
}