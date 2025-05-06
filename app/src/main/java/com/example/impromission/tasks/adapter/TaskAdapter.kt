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
    private val onEdit: (TaskEntity) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

    inner class TaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val text: TextView = itemView.findViewById(R.id.currentTask) // переделать
        init{
            itemView.setOnClickListener{
                onEdit(tasks[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tasks_item, parent, false))

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.text.text = tasks[position].text
    }

    override fun getItemCount() = tasks.size

    fun submitList(newList : List<TaskEntity>){
        tasks = newList
        notifyDataSetChanged()
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