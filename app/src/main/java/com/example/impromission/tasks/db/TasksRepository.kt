package com.example.impromission.tasks.db

import androidx.lifecycle.LiveData

class TasksRepository(private val dao: TaskDao) {
    val allTasks : LiveData<List<TaskEntity>> = dao.getAll()

    suspend fun add(text: String){
        dao.insert(TaskEntity(text = text))
    }

    suspend fun update(task : TaskEntity){
        dao.update(task)
    }

    suspend fun delete(task: TaskEntity){
        dao.delete(task)
    }
}