package com.juani48.todoapp.repository

import com.juani48.todoapp.application.entitys.Task
import com.juani48.todoapp.application.entitys.toDomain
import com.juani48.todoapp.repository.dao.TaskDAO
import com.juani48.todoapp.repository.entity.toDataBase
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDAO) {

    suspend fun getAllTask(): List<Task>{
        return this.taskDao.getAllTask().map { it.toDomain() }
    }

    suspend fun addTask(task: Task){
        this.taskDao.addTask(task.toDataBase())
    }

    suspend fun deleteTask(task: Task){
        this.taskDao.deleteTask(task.toDataBase())
    }

    suspend fun updateTask(task: Task){
        this.taskDao.updateTask(task.toDataBase())
    }
}