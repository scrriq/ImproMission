package com.example.impromission.tasks.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.impromission.notification.db.NotificationEntity

@Dao
interface TaskDao{
    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAll() : LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getById(id: Long): LiveData<TaskEntity>

    @Insert
    suspend fun insert(task: TaskEntity): Long

    @Update
    suspend fun update(task: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)
}