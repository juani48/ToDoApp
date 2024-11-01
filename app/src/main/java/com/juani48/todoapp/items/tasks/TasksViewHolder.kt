package com.juani48.todoapp.items.tasks

import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.juani48.todoapp.R
import com.juani48.todoapp.application.entitys.TaskCategory
import com.juani48.todoapp.application.entitys.Task

class TasksViewHolder(
    view: View,
    private val onTaskSelected: (Int) -> Unit,
    private val onDeleteSelected: (Int) -> Unit
) :
    RecyclerView.ViewHolder(view) {

    private var tvTaskName: TextView = view.findViewById(R.id.tv_TaskName)
    private var cbTask: CheckBox = view.findViewById(R.id.cb_TaskColor)
    private var ivDeleteImage: ImageView = view.findViewById(R.id.iv_DeleteImage)

    fun render(task: Task) {
        this.itemView.setOnClickListener { this.onTaskSelected(layoutPosition) }
        this.ivDeleteImage.setOnClickListener { this.onDeleteSelected(layoutPosition) }
        this.tvTaskName.text = task.name

        if (task.selected) {
            tvTaskName.paintFlags = tvTaskName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvTaskName.paintFlags = tvTaskName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        val color = when (task.category) {
            TaskCategory.Daily -> R.color.my_daily_color
            TaskCategory.Personal -> R.color.my_personal_color
            TaskCategory.Weekly -> R.color.my_weekly_color
            else -> R.color.my_other_color
        }

        this.cbTask.isChecked = task.selected
        this.cbTask.buttonTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this.cbTask.context, color))
        this.cbTask.setOnClickListener { this.onTaskSelected(layoutPosition) }
    }

}