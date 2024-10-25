package com.juani48.todoapp.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
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
import com.juani48.todoapp.items.categories.CategoriesAdapter
import com.juani48.todoapp.items.tasks.TasksAdapter

class MainActivity : AppCompatActivity() {

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
    private lateinit var tasksList: MutableList<Task>

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
        this.tasksList = mutableListOf(
            Task("Tarea 1", TaskCategory.Other),
            Task("Tarea 2", TaskCategory.Daily),
            Task("Tarea 3", TaskCategory.Personal),
            Task("Tarea 4", TaskCategory.Weekly),
            Task("Tarea 5", TaskCategory.Other)
        )
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
                this.tasksList,
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
        val newTasks: List<Task> = this.filterList()
        newTasks[position].selected = !newTasks[position].selected
        this.tasksAdapter.notifyItemChanged(position)
        this.updateTasks()
    }

    // Funcion lambda de borrar tareas
    private fun onDeleteSelected(position: Int) {
        val list = this.filterList()
        val deleteTask = list[position]
        this.tasksList.remove(deleteTask)
        list.remove(deleteTask)
        tasksAdapter.tasks = list
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
                this.tasksList.add(Task(etTaskName.text.toString(), taskCategory))
                this.updateTasks()
                dialog.hide()
            }
        }
        dialog.show()
    }

    // Actualiza el listado de tasks
    private fun updateTasks() {
        tasksAdapter.tasks = this.filterList()
        this.tasksAdapter.notifyDataSetChanged()
    }

    // Retorla la lista actual de tareas
    private fun filterList(): MutableList<Task> {
        val categoriesSelected: List<TaskCategory> = this.categoryList.filter { x -> x.selected }
        return if (categoriesSelected.isNotEmpty()) {
            tasksList.filter { x -> categoriesSelected.contains(x.category) }.toMutableList()
        }else {
            this.tasksList
        }
    }
}