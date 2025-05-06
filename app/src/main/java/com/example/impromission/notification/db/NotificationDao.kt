package com.example.impromission.notification.db
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotificationDao {
    @Insert
    suspend fun insert(notification: NotificationEntity)

    // notification имя таблицы (entity)
//    @Query("SELECT * FROM notifications ORDER BY timestamp DESC")
    @Query("SELECT * FROM notifications")
    fun getAll(): LiveData<List<NotificationEntity>>
}