package com.example.impromission

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NotificationViewModel(private val repo: NotificationRepository) : ViewModel() {

    val notifications: LiveData<List<NotificationEntity>> = repo.allNotification

    fun addNotification(entity: NotificationEntity){
        viewModelScope.launch(Dispatchers.IO){
            repo.save(entity)
        }
    }
}

class NotificationViewModelFactory(private val repo: NotificationRepository) : ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>) : T {
        if(modelClass.isAssignableFrom(NotificationViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return NotificationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

