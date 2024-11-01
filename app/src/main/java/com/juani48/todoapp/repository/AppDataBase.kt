package com.juani48.todoapp.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juani48.todoapp.repository.dao.TaskDAO
import com.juani48.todoapp.repository.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getTaskDao(): TaskDAO
}