package com.juani48.todoapp.application.entitys

import com.juani48.todoapp.repository.entity.TaskEntity

data class Task(
    val id: Int = 0,
    var name: String,
    var category: TaskCategory,
    var selected: Boolean = false
)
fun TaskEntity.toDomain() = Task(id = id,name = name, category = parseCategory(category), selected = selected)

private fun parseCategory(string: String): TaskCategory {
    return when(string){
        "Personal" -> TaskCategory.Personal
        "Weekly" -> TaskCategory.Weekly
        "Daily" -> TaskCategory.Daily
        else -> TaskCategory.Other
    }
}

