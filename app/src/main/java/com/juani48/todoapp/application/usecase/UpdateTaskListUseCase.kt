package com.juani48.todoapp.application.usecase

import com.juani48.todoapp.application.entitys.TaskCategory
import com.juani48.todoapp.application.entitys.Task
import com.juani48.todoapp.application.entitys.toDomain
import com.juani48.todoapp.repository.TaskRepository
import com.juani48.todoapp.repository.dao.TaskDAO
import javax.inject.Inject

class UpdateTaskListUseCase @Inject constructor( private val repository: TaskRepository) {

    suspend fun execute(categories: List<TaskCategory>): List<Task> {
        val categoriesSelected: List<TaskCategory> = categories.filter { x -> x.selected }
        return if (categoriesSelected.isNotEmpty()) {
             this.repository.getAllTask().filter { x -> categoriesSelected.contains(x.category) }
        }
        else { this.repository.getAllTask() }
    }

}