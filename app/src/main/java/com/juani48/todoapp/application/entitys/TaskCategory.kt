package com.juani48.todoapp.application.entitys


sealed class TaskCategory(public var selected: Boolean = false) {
    data object Personal: TaskCategory()
    data object Daily: TaskCategory() //Categoria diaria
    data object Weekly: TaskCategory() //Categoira semanal
    data object Other: TaskCategory()
}