package com.juani48.todoapp.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.juani48.todoapp.application.entitys.Task

@Entity(tableName = "task_table")
data class TaskEntity (
    @PrimaryKey(autoGenerate = true) //id
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "selected") val selected: Boolean = false)

fun Task.toDataBase() = TaskEntity(id = id,name = name, category = category.toString(), selected = selected)