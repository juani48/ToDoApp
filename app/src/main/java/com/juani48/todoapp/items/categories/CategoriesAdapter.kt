package com.juani48.todoapp.items.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juani48.todoapp.R
import com.juani48.todoapp.application.TaskCategory

//"Pinta" cada elementos de la lista ´categories´
class CategoriesAdapter(
    private val categories: List<TaskCategory>,
    private val onCategorySelected: (Int) -> Unit)
    : RecyclerView.Adapter<CategoriesViewHolder>() {

    // Crea un View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task_category, parent, false)
        return CategoriesViewHolder(view)
    }

    //Cantidad de elementos a mostrar
    override fun getItemCount(): Int = this.categories.size

    // Agrega a la View de onCrateViewHolder la informacion para "pintarla"
    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.render(this.categories[position])
        holder.itemView.setOnClickListener{ this.onCategorySelected(position) }
    }
}