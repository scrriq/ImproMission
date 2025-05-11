package com.example.impromission.tasks.db

import androidx.lifecycle.LiveData

class TasksRepository(private val dao: TaskDao) {
    val allTasks : LiveData<List<TaskEntity>> = dao.getAll()

    fun getTaskById(id: Long): LiveData<TaskEntity> = dao.getById(id)

    suspend fun add(task: TaskEntity)= dao.insert(task)

    suspend fun update(task: TaskEntity) = dao.update(task)

    suspend fun delete(task: TaskEntity) = dao.delete(task)

//    suspend fun add(text: String){
//        dao.insert(TaskEntity(text = text))
//    }
//
//    suspend fun update(task : TaskEntity){
//        dao.update(task)
//    }
//
//    suspend fun delete(task: TaskEntity){
//        dao.delete(task)
//    }
}