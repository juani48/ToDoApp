package com.juani48.todoapp.application.usecase

import com.juani48.todoapp.application.entitys.Task
import com.juani48.todoapp.repository.TaskRepository
import java.util.Collections

class AddTaskUseCase(private val repository: TaskRepository) {
    public fun execute(newTask: Task){
        this.repository.addElement(newTask)
    }
}