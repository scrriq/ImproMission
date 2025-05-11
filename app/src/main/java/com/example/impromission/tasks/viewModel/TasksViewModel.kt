package com.example.impromission.tasks.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.impromission.AppDatabase
import com.example.impromission.tasks.db.TaskEntity
import com.example.impromission.tasks.db.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TasksViewModel(app: Application) : AndroidViewModel(app) {
    private val repo: TasksRepository
    val allTasks: LiveData<List<TaskEntity>>

    init {
        val dao = AppDatabase.getDb(app).taskDao()
        repo = TasksRepository(dao)
        allTasks = repo.allTasks
    }

    fun getTaskById(id: Long): LiveData<TaskEntity> = repo.getTaskById(id)

    fun addTask(task: TaskEntity): LiveData<Long> {
        val result = MutableLiveData<Long>()
        viewModelScope.launch(Dispatchers.IO) {
            val newId = repo.add(task)
            result.postValue(newId)
        }
        return result
    }

    fun updateTask(task: TaskEntity) = viewModelScope.launch {
        repo.update(task)
    }

    fun deleteTask(task: TaskEntity) = viewModelScope.launch {
        repo.delete(task)
    }
}