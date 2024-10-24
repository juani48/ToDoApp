package com.juani48.todoapp.items.categories

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.juani48.todoapp.R
import com.juani48.todoapp.application.TaskCategory

class CategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvCategoryName: TextView = view.findViewById(R.id.tv_CategoryName)
    private val divider: View = view.findViewById(R.id.v_Divider)
    private val cvCategroy: CardView = view.findViewById(R.id.cv_Category)

    public fun render(taskCategory: TaskCategory) {

        when (taskCategory) {
            TaskCategory.Daily -> {
                this.tvCategoryName.text = ContextCompat.getString(
                    this.tvCategoryName.context,
                    R.string.task_category_daily
                )
                this.divider.setBackgroundColor(
                    ContextCompat.getColor(
                        this.divider.context,
                        R.color.my_daily_color
                    )
                )
            }

            TaskCategory.Other -> {
                this.tvCategoryName.text = ContextCompat.getString(
                    this.tvCategoryName.context,
                    R.string.task_category_other
                )
                this.divider.setBackgroundColor(
                    ContextCompat.getColor(
                        this.divider.context,
                        R.color.my_other_color
                    )
                )
            }

            TaskCategory.Personal -> {
                this.tvCategoryName.text = ContextCompat.getString(
                    this.tvCategoryName.context,
                    R.string.task_category_personal
                )
                this.divider.setBackgroundColor(
                    ContextCompat.getColor(
                        this.divider.context,
                        R.color.my_personal_color
                    )
                )
            }

            TaskCategory.Weekly -> {
                this.tvCategoryName.text = ContextCompat.getString(
                    this.tvCategoryName.context,
                    R.string.task_category_weekly
                )
                this.divider.setBackgroundColor(
                    ContextCompat.getColor(
                        this.divider.context,
                        R.color.my_weekly_color
                    )
                )
            }
        }
        if (taskCategory.selected) {
            this.cvCategroy.setCardBackgroundColor(
                ContextCompat.getColor(
                    this.cvCategroy.context,
                    R.color.my_task_category_selected_color
                )
            )
        } else {
            this.cvCategroy.setCardBackgroundColor(
                ContextCompat.getColor(
                    this.cvCategroy.context,
                    R.color.my_task_category_default_color
                )
            )
        }
    }

}