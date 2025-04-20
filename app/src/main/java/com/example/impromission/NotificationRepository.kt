package com.example.impromission

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class NotificationRepository(private val dao: NotificationDao) {

    val allNotification: LiveData<List<NotificationEntity>> = dao.getAll()

    suspend fun save(notification: NotificationEntity){
        dao.insert(notification)
    }

}