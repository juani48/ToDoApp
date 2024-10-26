package com.juani48.todoapp.application.usecase

import android.util.Log
import com.juani48.todoapp.application.TaskCategory
import com.juani48.todoapp.application.entitys.Task
import com.juani48.todoapp.repository.TaskRepository
import org.json.JSONObject

class GetAllTaskUseCase(private val repository: TaskRepository) {

    public fun execute(categoryList: List<TaskCategory>): List<Task> {
        val categoriesSelected: List<TaskCategory> = categoryList.filter { x -> x.selected }
        return if (categoriesSelected.isNotEmpty()) {
            this.repository.getList().filter { x -> categoriesSelected.contains(x.getCategory()) }.toMutableList()
        } else {
            this.repository.getList()
        }
    }
}