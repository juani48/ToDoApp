package com.juani48.todoapp.repository

import com.juani48.todoapp.application.TaskCategory
import com.juani48.todoapp.application.entitys.Task

class TaskRepository() {

    private var list: MutableList<Task> = mutableListOf(
        Task("Tarea 1", TaskCategory.Other),
        Task("Tarea 2", TaskCategory.Daily),
        Task("Tarea 3", TaskCategory.Personal),
        Task("Tarea 4", TaskCategory.Weekly),
        Task("Tarea 5", TaskCategory.Other)
    )

    public fun getList(): List<Task> {
        return this.list
    }

    public fun addElement(task: Task) {
        this.list.add(task)
    }

    public fun removeElement(task: Task) {
        this.list.remove(task)
    }
}