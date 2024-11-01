package com.juani48.todoapp.viewmodel

import android.app.Dialog
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juani48.todoapp.R
import com.juani48.todoapp.application.entitys.TaskCategory
import com.juani48.todoapp.application.entitys.Task
import com.juani48.todoapp.application.usecase.AddTaskUseCase
import com.juani48.todoapp.application.usecase.DeleteTaskUseCase
import com.juani48.todoapp.application.usecase.GetAllTaskUseCase
import com.juani48.todoapp.application.usecase.UpdateTaskListUseCase
import com.juani48.todoapp.application.usecase.UpdateTaskUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskViewModel @Inject constructor(
    private val getAllTaskUseCase: GetAllTaskUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTasksListUseCase: UpdateTaskListUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : ViewModel() {

    // Models
    val taskModel = MutableLiveData<List<Task>>()
    val categoryModel = MutableLiveData<List<TaskCategory>>()

    val isLoading = MutableLiveData<Boolean>()

    fun onCreate() {

        this.categoryModel.postValue(
            listOf(
                TaskCategory.Personal,
                TaskCategory.Daily,
                TaskCategory.Weekly,
                TaskCategory.Other
            )
        )
        CoroutineScope(Dispatchers.IO).launch {
            isLoading.postValue(true)
            taskModel.postValue(getAllTaskUseCase.execute())
            isLoading.postValue(false)
        }

    }

    // Getters
    public fun getCategoriesList(): List<TaskCategory> = this.categoryModel.value.orEmpty()
    public fun getTasksList(): List<Task> = this.taskModel.value.orEmpty()

    // "Use case update data"
    private fun updateTaskList() {
        CoroutineScope(Dispatchers.IO).launch {
            isLoading.postValue(true)
            taskModel.postValue(updateTasksListUseCase.execute(getCategoriesList()))
            isLoading.postValue(false)
        }
    }

    // Use case add task
    fun addTask(dialog: Dialog) {
        val etTaskName: EditText = dialog.findViewById(R.id.et_NameTask)
        if (etTaskName.text.toString() != "") {
            val taskCategory: TaskCategory = this.parseCategory(dialog)
            CoroutineScope(Dispatchers.IO).launch {
                isLoading.postValue(true)
                addTaskUseCase.execute(Task(0,etTaskName.text.toString(), taskCategory))
                taskModel.postValue(getAllTaskUseCase.execute())
                isLoading.postValue(false)
            }
        }
        dialog.hide()
    }

    // extends add task
    private fun parseCategory(dialog: Dialog): TaskCategory {
        val radioGroup: RadioGroup = dialog.findViewById(R.id.Radio_Group)
        val idSelected = radioGroup.checkedRadioButtonId
        val selectedRadioButton: RadioButton = radioGroup.findViewById(idSelected)
        return when (selectedRadioButton.text) {
            ContextCompat.getString(
                dialog.context,
                R.string.task_category_personal
            ) -> TaskCategory.Personal

            ContextCompat.getString(
                dialog.context,
                R.string.task_category_daily
            ) -> TaskCategory.Daily

            ContextCompat.getString(
                dialog.context,
                R.string.task_category_weekly
            ) -> TaskCategory.Weekly

            else -> TaskCategory.Other
        }
    }

    // Use case delete task
    fun onDeleteSelected(position: Int){
        val list = this.getTasksList().toMutableList()
        CoroutineScope(Dispatchers.IO).launch {
            isLoading.postValue(true)
            deleteTaskUseCase.execute(list[position])
            list.remove(list[position])
            taskModel.postValue(list)
            isLoading.postValue(false)
        }
    }

    // Funcion lambda de categorias
    fun onCategorySelected(position: Int) {
        val list = this.getCategoriesList()
        list[position].selected = !list[position].selected
        this.categoryModel.postValue(list)
        this.updateTaskList()
    }

    // Funcion lambda de tareas
    fun onTaskSelected(position: Int) {
        val task = this.getTasksList()[position]
        CoroutineScope(Dispatchers.IO).launch {
            isLoading.postValue(true)
            updateTaskUseCase.execute(task)
            updateTaskList()
            isLoading.postValue(false)
        }

    }
}