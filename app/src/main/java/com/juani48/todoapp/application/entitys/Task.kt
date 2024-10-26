package com.juani48.todoapp.application.entitys

import com.juani48.todoapp.application.TaskCategory

class Task(
    private var name: String,
    private var category: TaskCategory,
    private var selected: Boolean = false
){
    public fun getName(): String = this.name
    public fun getCategory(): TaskCategory = this.category
    public fun getState(): Boolean = this.selected

    public fun setState(){
        this.selected = !this.selected
    }

    public fun equlas(task: Task): Boolean = this.getName() == task.getName() && this.getCategory() == task.getCategory()
}