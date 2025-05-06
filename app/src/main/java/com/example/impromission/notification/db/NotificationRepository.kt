package com.example.impromission.notification.db

import androidx.lifecycle.LiveData

class NotificationRepository(private val dao: NotificationDao) {

    val allNotification: LiveData<List<NotificationEntity>> = dao.getAll()

    suspend fun save(notification: NotificationEntity){
        dao.insert(notification)
    }

}