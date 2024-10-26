package com.juani48.todoapp.application.usecase

import com.juani48.todoapp.application.entitys.Task
import com.juani48.todoapp.repository.TaskRepository

class DeleteTaskUseCase(private val repository: TaskRepository) {

    public fun execute(task: Task){
        this.repository.removeElement(task)
    }
}