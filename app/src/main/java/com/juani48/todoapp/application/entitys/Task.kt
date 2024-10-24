package com.juani48.todoapp.application.entitys

import com.juani48.todoapp.application.TaskCategory

data class Task(val name: String, val category: TaskCategory, var selected: Boolean = false) {
}