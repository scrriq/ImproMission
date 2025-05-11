package com.example.impromission.tasks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.impromission.R
import com.example.impromission.tasks.db.TaskEntity
import javax.security.auth.callback.Callback

class TaskAdapter (
    private var tasks: List<TaskEntity>,
    private val onTaskClick: (Long) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

    inner class TaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.currentTask)
        val description: TextView = itemView.findViewById(R.id.currentTaskDescription)
        init{
            itemView.setOnClickListener{
                onTaskClick(tasks[adapterPosition].id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tasks_item, parent, false))

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.title.text = task.title.trimEllipsis(15)
        holder.description.text = task.description.trimEllipsis(35)
    }

    override fun getItemCount() = tasks.size

    fun submitList(newList : List<TaskEntity>){
        tasks = newList
        notifyDataSetChanged()
    }
    private fun String.trimEllipsis(maxLength: Int): String {
        return if(length > maxLength) substring(0, maxLength).trimEnd() + "..." else this
    }

    fun attachSwipeToDelete(recyclerView: RecyclerView, onDelete : (TaskEntity) -> Unit){
        val callback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                onDelete(tasks[pos])
            }
        }
        ItemTouchHelper(callback).attachToRecyclerView(recyclerView)
    }
}