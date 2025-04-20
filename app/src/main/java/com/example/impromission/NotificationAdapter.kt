package com.example.impromission

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.impromission.databinding.NotificationItemBinding


// vh - view holder . В данном случае реализован как inner класс в RecyclerView.Adapter
class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.notificationHolder>() {
    private val notificationList = mutableListOf<NotificationEntity>()

    fun submitList(newList: List<NotificationEntity>){ // добавление нового элемента
        notificationList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notificationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return notificationHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.notificationHolder, position: Int) {
        holder.bind(notificationList[position])
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    inner class notificationHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = NotificationItemBinding.bind(view)
        fun bind(item: NotificationEntity) = with(binding){
            notificationName.text = item.title
            notificationInfo.text = item.text
//            itemView.tvText.text = java.text.DateFromat.getTimeInstance().format(item.timestamp) продумать момент со временем
        }
    }
}