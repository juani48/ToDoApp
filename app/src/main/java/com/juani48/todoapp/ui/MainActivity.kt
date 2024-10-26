package com.juani48.todoapp.ui

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.juani48.todoapp.R
import com.juani48.todoapp.application.TaskCategory
import com.juani48.todoapp.application.entitys.Task
import com.juani48.todoapp.application.usecase.AddTaskUseCase
import com.juani48.todoapp.application.usecase.DeleteTaskUseCase
import com.juani48.todoapp.application.usecase.GetAllTaskUseCase
import com.juani48.todoapp.items.categories.CategoriesAdapter
import com.juani48.todoapp.items.tasks.TasksAdapter
import com.juani48.todoapp.repository.TaskRepository

class MainActivity : AppCompatActivity() {

   // MutableList
    // Repository
    private val repository = TaskRepository()

    // Usecases
    private val getAllTask = GetAllTaskUseCase(this.repository)
    private val deleteTask = DeleteTaskUseCase(this.repository)
    private val addTask = AddTaskUseCase(this.repository)

    // Lista de categorias
    private lateinit var rvCategories: RecyclerView
    private lateinit var categoriesAdapter: CategoriesAdapter

    //
    private val categoryList = listOf(
        TaskCategory.Personal,
        TaskCategory.Daily,
        TaskCategory.Weekly,
        TaskCategory.Other
    )

    // Lista de tareas
    private lateinit var rvTask: RecyclerView
    private lateinit var tasksAdapter: TasksAdapter

    //
    private lateinit var fobAddTask: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.initComponents()
        this.initUI()
        this.initListeners()
    }

    private fun initComponents() {
        this.rvCategories = findViewById(R.id.rv_Categories)
        this.rvTask = findViewById(R.id.rv_Task)
        this.fobAddTask = findViewById(R.id.fab_AddTask)
    }

    private fun initUI() {
        // RecyclerView Categories
        this.categoriesAdapter =
            CategoriesAdapter(this.categoryList) { position -> this.onCategorySelected(position) }
        this.rvCategories.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        this.rvCategories.adapter = this.categoriesAdapter

        // RecyclerView Tasks
        this.tasksAdapter =
            TasksAdapter(
                this.repository.getList(),
                { position -> this.onTaskSelected(position) },
                { position -> this.onDeleteSelected(position) })
        this.rvTask.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        this.rvTask.adapter = this.tasksAdapter
    }

    private fun initListeners() {
        this.fobAddTask.setOnClickListener { this.showDialog() }
    }

    // Funcion lambda de categorias
    private fun onCategorySelected(position: Int) {
        this.categoryList[position].selected = !this.categoryList[position].selected
        this.categoriesAdapter.notifyItemChanged(position)
        this.updateTasks()
    }

    // Funcion lambda de tareas
    private fun onTaskSelected(position: Int) {
        this.getAllTask.execute(this.categoryList)[position].setState()
        this.tasksAdapter.notifyItemChanged(position)
        this.updateTasks()
    }

    // Funcion lambda de borrar tareas
    private fun onDeleteSelected(position: Int) {
        this.deleteTask.execute(this.getAllTask.execute(this.categoryList)[position])
        tasksAdapter.tasks = this.getAllTask.execute(this.categoryList)
        this.tasksAdapter.notifyDataSetChanged()
    }

    // Listener de fob
    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_new_task)

        val btnAddTask: Button = dialog.findViewById(R.id.btn_AddTask)
        val etTaskName: EditText = dialog.findViewById(R.id.et_NameTask)
        val radioGroup: RadioGroup = dialog.findViewById(R.id.Radio_Group)

        btnAddTask.setOnClickListener {
            if (etTaskName.text.toString() != "") {
                val idSelected = radioGroup.checkedRadioButtonId
                val selectedRadioButton: RadioButton = radioGroup.findViewById(idSelected)
                val taskCategory: TaskCategory = when (selectedRadioButton.text) {
                    getString(R.string.task_category_personal) -> TaskCategory.Personal
                    getString(R.string.task_category_daily) -> TaskCategory.Daily
                    getString(R.string.task_category_weekly) -> TaskCategory.Weekly
                    else -> TaskCategory.Other
                }

                this.addTask.execute(
                    Task(etTaskName.text.toString(), taskCategory)
                )
                this.updateTasks()
                dialog.hide()
            }
        }
        dialog.show()
    }

    // Actualiza el listado de tasks
    private fun updateTasks() {
        tasksAdapter.tasks = this.getAllTask.execute(this.categoryList)
        this.tasksAdapter.notifyDataSetChanged()
    }

}