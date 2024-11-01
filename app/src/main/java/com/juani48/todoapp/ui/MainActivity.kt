package com.juani48.todoapp.ui

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.juani48.todoapp.R
import com.juani48.todoapp.databinding.ActivityMainBinding
import com.juani48.todoapp.items.categories.CategoriesAdapter
import com.juani48.todoapp.items.tasks.TasksAdapter
import com.juani48.todoapp.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // ViewModel
    @Inject lateinit var taskViewModel: TaskViewModel

    // RecyclerView CategoriesAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    // RecyclerView TasksAdapter
    private lateinit var tasksAdapter: TasksAdapter

    // Dynamic Binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(layoutInflater) // init binding
        enableEdgeToEdge()
        setContentView(binding.root) // root binding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.linearLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.taskViewModel.onCreate() // init ViewModel
        this.initUI()
        this.binding.fabAddTask.setOnClickListener { this.showDialog() } // Listener
        this.initObservers()
    }

    private fun initUI() {
        // RecyclerView Categories
            // init adapter
        this.categoriesAdapter =
            CategoriesAdapter(this.taskViewModel.getCategoriesList()) { position -> this.onCategorySelected(position) }
            // init RecyclerView
        this.binding.rvCategories.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            // set adapter in RecyclerView
        this.binding.rvCategories.adapter = this.categoriesAdapter

        // RecyclerView Tasks
            // init adapter
        this.tasksAdapter =
            TasksAdapter(
                this.taskViewModel.getTasksList(),
                { position -> this.onTaskSelected(position) },
                { position -> this.onDeleteSelected(position) }
            )
            // init RecyclerView
        this.binding.rvTask.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            // set adapter in RecyclerView
        this.binding.rvTask.adapter = this.tasksAdapter
    }

    private fun initObservers(){
        // if tasks list change -> upgrade task adapter
        this.taskViewModel.taskModel.observe(this, Observer
        { this.tasksAdapter.tasks = it; this.tasksAdapter.notifyDataSetChanged() })

        // if categories list change -> upgrade categories adapter
        this.taskViewModel.categoryModel.observe(this, Observer
        { this.categoriesAdapter.categories = it; this.categoriesAdapter.notifyDataSetChanged() })

        this.taskViewModel.isLoading.observe(this, Observer {
            this.binding.linearLayout.visibility = if(!it){View.VISIBLE}else{View.GONE}
            this.binding.progressBar.visibility = if(it){View.VISIBLE}else{View.GONE} })
    }

    // Funcion lambda de categorias
    private fun onCategorySelected(position: Int) {
        this.taskViewModel.onCategorySelected(position)
    }

    // Funcion lambda de tareas
    private fun onTaskSelected(position: Int) {
        this.taskViewModel.onTaskSelected(position)
    }

    // Funcion lambda de borrar tareas
    private fun onDeleteSelected(position: Int) {
        this.taskViewModel.onDeleteSelected(position)
    }

    // Listener de fob
    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_new_task)

        val btnAddTask: Button = dialog.findViewById(R.id.btn_AddTask)
        btnAddTask.setOnClickListener {
            this.taskViewModel.addTask(dialog)
        }
        dialog.show()
    }
}