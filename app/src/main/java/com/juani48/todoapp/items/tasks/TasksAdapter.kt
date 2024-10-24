package com.juani48.todoapp.items.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juani48.todoapp.R
import com.juani48.todoapp.application.entitys.Task

class TasksAdapter(public var tasks: List<Task>, private val onTaskSelected: (Int) -> Unit) :
    RecyclerView.Adapter<TasksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TasksViewHolder(view, onTaskSelected)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.render(this.tasks[position])
    }

    override fun getItemCount(): Int = this.tasks.size
}