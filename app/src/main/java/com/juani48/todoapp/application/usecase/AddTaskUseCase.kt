package com.juani48.todoapp.application.usecase

import com.juani48.todoapp.application.entitys.Task
import com.juani48.todoapp.repository.TaskRepository
import javax.inject.Inject


class AddTaskUseCase @Inject constructor(private val repository: TaskRepository) {

    suspend fun execute(task: Task) {
        this.repository.addTask(task)
    }
}