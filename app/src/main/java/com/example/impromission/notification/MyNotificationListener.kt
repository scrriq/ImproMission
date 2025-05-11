package com.example.impromission.notification

import android.app.Application
import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.impromission.AppDatabase
import com.example.impromission.notification.db.NotificationEntity
import com.example.impromission.tasks.db.TaskEntity
import com.example.impromission.tasks.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyNotificationListener : NotificationListenerService() {
    private lateinit var tasksViewModel: TasksViewModel
    override fun onCreate() {
        super.onCreate()
        val application = this.application as Application
        tasksViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(TasksViewModel::class.java)
        Log.d("NotificationService1", "Сервис создан")
    }
    // Проверка работы сервиса
    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d("NotificationService1", "Сервис запущен и работает")
    }

    private val dao by lazy {
        AppDatabase.getDb(applicationContext).notificationDao()
    }


    // Отслеживание push уведомлений
        override fun onNotificationPosted(sbn: StatusBarNotification) {
        Log.d("hello", "Функция запущена")
        val packageName = sbn.packageName
        val extras = sbn.notification.extras

        val entity = NotificationEntity(
            packageName = sbn.packageName,
            title = extras.getString(Notification.EXTRA_TITLE, "Нет заголовка"),
            text = extras.getString(Notification.EXTRA_TEXT, "Нет текста"),
            bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString() ?: "Нет big Text",
            infoText = extras.getCharSequence(Notification.EXTRA_INFO_TEXT)?.toString() ?: "Нет info Text",
            subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)?.toString() ?: "Нет Sub Text"
        )

        // отслеживание уведомлений
        CoroutineScope(Dispatchers.IO).launch {
            val nothTitle = entity.text
            val wordsArray = nothTitle.split("\\s+".toRegex()).toTypedArray()
            // Сценарий добавление задачи
            if(entity.packageName == "org.telegram.messenger" && wordsArray[0] == "#123"){
                val title = "Сообщение от ${entity.title}"
                val description = "Код операции ${wordsArray[1]}, Заказ: ${wordsArray[2]}"
                val newTask = TaskEntity(title = title, description = description)
                tasksViewModel.addTask(newTask)
            }
            dao.insert(entity)
        }






//        Log.d("NotificationListener", "Extras: ${extras.keySet()}")
//
//        // Основной текст Notification
//        val title = extras.getString(Notification.EXTRA_TITLE, "Нет заголовка")
//        val text = extras.getString(Notification.EXTRA_TEXT, "Нет текста")
//
//        // Развернутый текст notification
//        val bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString() ?:"Нет bigText"
//        val infoText = extras.getCharSequence(Notification.EXTRA_INFO_TEXT)?.toString() ?:"Нет infoText"
//        val subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)?.toString() ?:"Нет subText"
//
//        Log.d("NotificationInfo", "Приложение: $packageName," +
//                " Заголовок: $title, Текст: $text, BigText: $bigText, InfoText: $infoText ,SubText: $subText")
//
//
//        Log.d("NotificationInfo1", "From: $packageName, Title: $title, Text: $text")
    }

    // Отслеживание удаленных push
    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        Log.d("NotificationRemoved", "Removed: ${sbn.packageName}")
    }

    override fun onDestroy() {
        Log.d("NotificationService", "Сервис уничтожен")
        super.onDestroy()
    }

}